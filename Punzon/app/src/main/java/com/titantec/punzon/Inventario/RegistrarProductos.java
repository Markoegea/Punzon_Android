package com.titantec.punzon.Inventario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.databinding.ActivityRegistrarProductoBinding;

import java.util.HashMap;
import java.util.Map;

public class RegistrarProductos extends Fragment {
    ActivityRegistrarProductoBinding activityRegistrarProductoBinding;
    EditText txvnombre,txvid,txvprecio,txvdescripcion,txvcantidad,txvmarca;
    Button btnregistrar;
    ImageView imgproducto;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

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
        btnregistrar = activityRegistrarProductoBinding.btnRegistrar;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(v);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistrarProductoBinding = null;
    }

    private void registrar(View v) {
        Map<String,Object> producto = new HashMap<>();
        producto.put("nombre", txvnombre.getText().toString());
        producto.put("precio", txvprecio.getText().toString());
        producto.put("descripcion",txvdescripcion.getText().toString());
        producto.put("cantidad", txvcantidad.getText().toString());
        producto.put("marca", txvmarca.getText().toString());
        producto.put("id", txvid.getText().toString());
        firestore.collection("Productos").document(txvid.getText().toString()).set(producto).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task2) {
                if (task2.isSuccessful()) {
                    Toast.makeText(v.getContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    limpiar();
                } else {
                    Toast.makeText(v.getContext(), "Fallo en el registro, " +
                            "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                }
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
