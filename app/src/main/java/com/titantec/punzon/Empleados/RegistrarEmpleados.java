package com.titantec.punzon.Empleados;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.titantec.punzon.Inventario.RegistrarProductos;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.Modelos.Empleado;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityRegistroEmpleadosBinding;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrarEmpleados extends Fragment {

    private EditText etname, etlastname, etDocumento, etEmail, etpassword, edtNumero;
    private Spinner spid, sptemp, spcargo, spespecialidad;
    private Button btnGaleria, btnCamara, btnRegresar, btnRegisEmpleado, btnSubir;
    private ImageView imgEmpleado;
    private StorageReference ref;
    private static final int GALLERY_INTENT = 1;
    private static final int CODE_CAMERA = 21;
    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private static final int REQUEST_PERMISSION_WRITE_STORAGE = 200;

    private Uri uriImagen;

    ImageView iV;
    Uri uri;
    ProgressDialog progressDialog;
    FloatingActionButton btnFlotanteBack;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //StorageReference ref= storage.getReference();

    ActivityRegistroEmpleadosBinding activityRegistroEmpleadosBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityRegistroEmpleadosBinding = activityRegistroEmpleadosBinding.inflate(inflater, container, false);
        View root = activityRegistroEmpleadosBinding.getRoot();

        ref = FirebaseStorage.getInstance().getReference();

        etname = activityRegistroEmpleadosBinding.txtname;
        etlastname = activityRegistroEmpleadosBinding.txtlastname;
        etDocumento = activityRegistroEmpleadosBinding.txtDocumento;
        etEmail = activityRegistroEmpleadosBinding.txtEmail;
        etpassword = activityRegistroEmpleadosBinding.txtpassword;
        edtNumero= activityRegistroEmpleadosBinding.EdtNumero;

        spid = activityRegistroEmpleadosBinding.spidType;
        sptemp = activityRegistroEmpleadosBinding.spempleadoType;
        spcargo = activityRegistroEmpleadosBinding.spcargoType;
        spespecialidad = activityRegistroEmpleadosBinding.spespempleadoType;

        btnRegisEmpleado = activityRegistroEmpleadosBinding.btnRegisEmpleado;
        //btnSubir = activityRegistroEmpleadosBinding.BtnImagen;
        //iV = activityRegistroEmpleadosBinding.imageView3;

        btnGaleria = activityRegistroEmpleadosBinding.btnGaleria;
        btnCamara = activityRegistroEmpleadosBinding.btnCamara;
        //btnRegresar = activityRegistroEmpleadosBinding.btnRegresar;
        btnRegisEmpleado = activityRegistroEmpleadosBinding.btnRegisEmpleado;

        imgEmpleado = activityRegistroEmpleadosBinding.ivImagen;
        imgEmpleado.setImageResource(R.drawable.logo);

        btnFlotanteBack = activityRegistroEmpleadosBinding.btnFlotanteRegresar;
        btnFlotanteBack.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                Fragment ventana = new DatosEmpleadoActivity();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.regEmpleados, ventana);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(view.getContext());

        // Lista para tipo de documento
        String[] tipoDocumento = {"     ?", "Cédula de ciudadanía", "Tarjeta de identidad", "Cedula de extranjería", "Pasaporte"};
        ArrayAdapter<String> tipoDocumentoAdapter = new ArrayAdapter<>(view.getContext(),R.layout.spinner_item, tipoDocumento);
        spid.setAdapter(tipoDocumentoAdapter);

        // Lista para tipo de empleado
        String[] tipoEmpleado = {"     ?", "Planta", "Prestador de servicios"};
        ArrayAdapter<String> tipoEmpleadoAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, tipoEmpleado);
        sptemp.setAdapter(tipoEmpleadoAdapter);

        // Lista para cargo
        String[] cargo = {"     ?", "Gerente", "Contador", "Vendedor", "Asesor comercial", "Auxuliar de bodega", "Jefe de bodega", "Domiciliario", "Vendedor", "Conductor", "Domiciliario", "Servicios generales", "Servicios especiales"};
        ArrayAdapter<String> cargoAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, cargo);
        spcargo.setAdapter(cargoAdapter);

        // Lista para especialidad
        String[] especialidad = {"     ?", "No aplica", "Maestro de obra", "Plomero", "Eléctrico", "Soldador", "Otro"};
        ArrayAdapter<String> especialidadAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, especialidad);
        spespecialidad.setAdapter(especialidadAdapter);

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarPermisosAlmacenamiento();
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarPermisoCamara();
            }
        });

        btnRegisEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos(v);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_INTENT:
                if (data != null) {
                    uriImagen = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriImagen);
                        imgEmpleado.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CODE_CAMERA:
                Bitmap bitmap = BitmapFactory.decodeFile(getActivity().getExternalFilesDir(null)+"/test.jpg");
                imgEmpleado.setImageBitmap(bitmap);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistroEmpleadosBinding = null;
    }


    private void validarDatos(View v) {

        String nombreString = etname.getText().toString();
        String apellidoString = etlastname.getText().toString();
        String documentoString = etDocumento.getText().toString();
        String usuarioString = etEmail.getText().toString();
        String contraseñaString = etpassword.getText().toString();
        String numeroTelefonoString = edtNumero.getText().toString();

        if (nombreString.length() == 0) {
            makeText(v.getContext(), "Debe ingresar un nombre", LENGTH_SHORT).show();
        }
        if (apellidoString.length() == 0) {
            makeText(v.getContext(), "Debe ingresar un apellido", LENGTH_SHORT).show();
        }
        if (documentoString.length() == 0) {
            makeText(v.getContext(), "Debe ingresar un número de documento", LENGTH_SHORT).show();
        }
        if (numeroTelefonoString.length() == 0) {
            makeText(v.getContext(), "Debe ingresar un número de teléfono", LENGTH_SHORT).show();
        }
        if (usuarioString.length() == 0) {
            makeText(v.getContext(), "Debe ingresar un Email de usuario", LENGTH_SHORT).show();
        }
        if (uriImagen == null){
            Toast.makeText(v.getContext(), "Debe subir una imagen", Toast.LENGTH_SHORT).show();
        }
        if (contraseñaString.length() == 0) {
            makeText(v.getContext(), "Debe ingresar una contraseña", LENGTH_SHORT).show();
        }else if (v.getId()==btnRegisEmpleado.getId()){
            enviarImagenFireStore();
            crearEmpleado();
        }
    }

    private void crearEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setNombre(etname.getText().toString());
        empleado.setApellido(etlastname.getText().toString());
        empleado.setTipoDocumento(spid.getSelectedItem().toString());
        empleado.setTipoEmpleado(sptemp.getSelectedItem().toString());
        empleado.setCargo(spcargo.getSelectedItem().toString());
        empleado.setEspecialidad(spespecialidad.getSelectedItem().toString());
        empleado.setEmail(etEmail.getText().toString());
        empleado.setContraseña(etpassword.getText().toString());
        empleado.setNumero(edtNumero.getText().toString());
        empleado.setDocumento(etDocumento.getText().toString());

        firestore.collection("Empleados").document(etDocumento.getText().toString()).set(empleado);
        makeText(getContext(), "Empleado registrado correctamente", LENGTH_SHORT).show();
        //limpiar();
    }

    private void limpiar() {
        etname.setText("");
        etlastname.setText("");
        etDocumento.setText("");
        etEmail.setText("");
        etpassword.setText("");
        edtNumero.setText("");
        spcargo.setAdapter(spcargo.getAdapter());
        spespecialidad.setAdapter(spespecialidad.getAdapter());
        spid.setAdapter(spid.getAdapter());
        sptemp.setAdapter(sptemp.getAdapter());
        imgEmpleado.setImageResource(R.drawable.logo);
    }

    private void verificarPermisoCamara(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                tomarFoto();
            }else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
            }
        }else{
            tomarFoto();
        }
    }

    private void tomarFoto(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        File imagen = new File(getActivity().getExternalFilesDir(null), "test.jpg");
        uriImagen = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName()+".provider", imagen);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImagen);
        startActivityForResult(intent, CODE_CAMERA);

    }

    public void verificarPermisosAlmacenamiento(){
        if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                elegirImagenGaleria();
            }else{
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_WRITE_STORAGE);
            }
        }else{
            elegirImagenGaleria();
        }
    }

    private void elegirImagenGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    private void enviarImagenFireStore(){
        StorageReference filePath = ref.child("fotosEmpleados");
        StorageReference fotoRef = filePath.child(etDocumento.getText().toString() + "_" + uriImagen.getLastPathSegment());
        fotoRef.putFile(uriImagen).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri downloadUrl = uriTask.getResult();

                Map<String,Object>empleado = new HashMap<>();
                empleado.put("documento", etDocumento.getText().toString());
                empleado.put("urlImagen",downloadUrl.toString());

                firestore.collection("Empleados").document(etDocumento.getText().toString()).update(empleado);
                limpiar();
            }
        });
    }
}
