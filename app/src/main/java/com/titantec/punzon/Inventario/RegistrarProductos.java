package com.titantec.punzon.Inventario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.databinding.ActivityRegistrarProductoBinding;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrarProductos extends Fragment {
    ActivityRegistrarProductoBinding activityRegistrarProductoBinding;
    EditText txvnombre,txvid,txvprecio,txvdescripcion,txvcantidad,txvmarca;
    Button btnregistrar,btnSubir;
    ImageView imgproducto;
    ProgressDialog progressDialog;
    Uri uri;
    String url;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference ref= storage.getReference();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityRegistrarProductoBinding = activityRegistrarProductoBinding.inflate(inflater, container, false);
        View root = activityRegistrarProductoBinding.getRoot();

        txvnombre = activityRegistrarProductoBinding.txvNombre;
        txvprecio= activityRegistrarProductoBinding.txvPrecio;
        txvdescripcion = activityRegistrarProductoBinding.txvDescripcion;
        txvcantidad= activityRegistrarProductoBinding.txvCantidad;
        txvmarca = activityRegistrarProductoBinding.txvMarca;
        txvid = activityRegistrarProductoBinding.txvId;
        imgproducto = activityRegistrarProductoBinding.imgProducto;
        btnregistrar = activityRegistrarProductoBinding.btnRegistrar;
        btnSubir = activityRegistrarProductoBinding.BtnSubir;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(view.getContext());

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(v);
            }
        });
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerImagen();
            }
        });
    }

    private void ponerImagen() {
        Intent imagen = new Intent(Intent.ACTION_PICK);
        imagen.setType("image/*");
        //startActivityForResult(imagen,GALLERY_INTENT);
        ponerImagenIV.launch(imagen);
    }

    ActivityResultLauncher<Intent> ponerImagenIV = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // There are no request codes
                    Intent intent = result.getData();
                    if(intent != null){
                        Glide.with(RegistrarProductos.this).load(intent.getData()).into(imgproducto);
                        uri = intent.getData();
                    }
                }
            });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistrarProductoBinding = null;
    }

    private void registrar(View v) {
        progressDialog.setTitle("Subiendo...");
        progressDialog.setMessage("Subiendo producto a la base de datos");
        progressDialog.setCancelable(false);
        progressDialog.show();


        StorageReference filePath= ref.child("fotosProductos").child(uri.getLastPathSegment());
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> storage = taskSnapshot.getStorage().getDownloadUrl();
                storage.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url = uri.toString();
                        Map<String,Object> producto = new HashMap<>();
                        producto.put("nombre", txvnombre.getText().toString());
                        producto.put("precio", txvprecio.getText().toString());
                        producto.put("descripcion",txvdescripcion.getText().toString());
                        producto.put("cantidad", txvcantidad.getText().toString());
                        producto.put("marca", txvmarca.getText().toString());
                        producto.put("id", txvid.getText().toString());
                        producto.put("imagen", url);
                        firestore.collection("Productos").document(txvid.getText().toString()).set(producto).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if (task2.isSuccessful()) {
                                    Toast.makeText(v.getContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                    limpiar();
                                    progressDialog.dismiss();
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

    private void limpiar() {
        txvnombre.setText("");
        txvprecio.setText("");
        txvdescripcion.setText("");
        txvcantidad.setText("");
        txvmarca.setText("");
        txvid.setText("");
    }
}
