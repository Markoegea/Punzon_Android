package com.titantec.punzon.Empleados;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.titantec.punzon.Inventario.RegistrarProductos;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityRegistroEmpleadosBinding;

import java.util.HashMap;
import java.util.Map;

public class RegistrarEmpleados extends Fragment {
    EditText etname, etlastname, etDocumento, etEmail, etpassword,edtNumero;
    Spinner spid, sptemp, spcargo, spespecialidad;
    Button btnRegisEmpleado, btnSubir;
    ImageView iV;
    Uri uri;
    ProgressDialog progressDialog;
    String id;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref= storage.getReference();

    ActivityRegistroEmpleadosBinding activityRegistroEmpleadosBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityRegistroEmpleadosBinding = activityRegistroEmpleadosBinding.inflate(inflater, container, false);
        View root = activityRegistroEmpleadosBinding.getRoot();

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
        btnSubir = activityRegistroEmpleadosBinding.BtnImagen;
        iV = activityRegistroEmpleadosBinding.imageView3;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(view.getContext());

        // Lista para tipo de documento
        String[] tipoDocumento = {"Seleccione...", "Cédula de ciudadanía", "Tarjeta de identidad", "Cedula de extranjería", "Pasaporte"};
        ArrayAdapter<String> tipoDocumentoAdapter = new ArrayAdapter<>(view.getContext(),R.layout.spinner_item, tipoDocumento);
        spid.setAdapter(tipoDocumentoAdapter);

        // Lista para tipo de empleado
        String[] tipoEmpleado = {"Seleccione...", "Planta", "Prestador de servicios"};
        ArrayAdapter<String> tipoEmpleadoAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, tipoEmpleado);
        sptemp.setAdapter(tipoEmpleadoAdapter);

        // Lista para cargo
        String[] cargo = {"Seleccione...", "Gerente", "Contador", "Vendedor", "Asesor comercial", "Auxuliar de bodega", "Jefe de bodega", "Domiciliario", "Vendedor", "Conductor", "Domiciliario", "Servicios generales", "Servicios especiales"};
        ArrayAdapter<String> cargoAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, cargo);
        spcargo.setAdapter(cargoAdapter);

        // Lista para especialidad
        String[] especialidad = {"Seleccione...", "No aplica", "Maestro de obra", "Plomero", "Eléctrico", "Soldador", "Otro"};
        ArrayAdapter<String> especialidadAdapter = new ArrayAdapter<>(view.getContext(), R.layout.spinner_item, especialidad);
        spespecialidad.setAdapter(especialidadAdapter);

        btnRegisEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos(v);
            }
        });

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerImagen();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistroEmpleadosBinding = null;
    }

    private void ponerImagen() {
        Intent imagen = new Intent(Intent.ACTION_PICK);
        imagen.setType("image/*");
        ponerImagenIV.launch(imagen);
    }

    ActivityResultLauncher<Intent> ponerImagenIV = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent intent = result.getData();
                    if(intent != null){
                        Glide.with(RegistrarEmpleados.this).load(intent.getData()).into(iV);
                        uri = intent.getData();
                    }
                }
            });

    private void validarDatos(View v) {
        String nombre1String = etname.getText().toString();
        String apellido1String = etlastname.getText().toString();
        String documentoString = etDocumento.getText().toString();
        String usuarioString = etEmail.getText().toString();
        String contraseñaString = etpassword.getText().toString();
        String numeroTelefonoString = edtNumero.getText().toString();
        if (nombre1String.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
        }
        if (apellido1String.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un apellido", Toast.LENGTH_SHORT).show();
        }
        if (documentoString.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un número de documento", Toast.LENGTH_SHORT).show();
        }
        if (usuarioString.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un Email de usuario", Toast.LENGTH_SHORT).show();
        }
        if (contraseñaString.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
        }
        if (uri == null) {
            Toast.makeText(v.getContext(), "Debe subir una imagen", Toast.LENGTH_SHORT).show();
        }else{
            registrar(v);
        }
    }
    private void registrar(View v) {
        if(auth.getCurrentUser()!=null) {
            Toast.makeText(v.getContext(), "Ya estas registrado", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Subiendo...");
            progressDialog.setMessage("Subiendo producto a la base de datos");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StorageReference filePath= ref.child("fotosEmpleados").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> storage = taskSnapshot.getStorage().getDownloadUrl();
                    storage.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        id = auth.getUid();
                                        subir(v,url);
                                    } else {
                                        Toast.makeText(v.getContext(), "Fallo en el registro, " +
                                                "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private void subir(View v,String url){
        Map<String, Object> empleado = new HashMap<>();
        empleado.put("nombre", etname.getText().toString());
        empleado.put("apellido", etlastname.getText().toString());
        empleado.put("tipoDocumento", spid.getSelectedItem().toString());
        empleado.put("tipoEmpleado", sptemp.getSelectedItem().toString());
        empleado.put("cargo", spcargo.getSelectedItem().toString());
        empleado.put("especialidad", spespecialidad.getSelectedItem().toString());
        empleado.put("email", etEmail.getText().toString());
        empleado.put("contraseña", etpassword.getText().toString());
        empleado.put("numero", edtNumero.getText().toString());
        empleado.put("documento", etDocumento.getText().toString());
        empleado.put("imagen", url );
        firestore.collection("Empleado").document(id).set(empleado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()) {
                    Toast.makeText(v.getContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    limpiar();
                    Intent inte = new Intent(v.getContext(), MainActivity.class);
                    startActivity(inte);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(v.getContext(), "Fallo en el registro, " +
                            "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    private void limpiar() {
        etname.setText("");
        etlastname.setText("");
        etDocumento.setText("");
        etEmail.setText("");
        etpassword.setText("");
        edtNumero.setText("");
    }
}
