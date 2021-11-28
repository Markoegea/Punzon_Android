package com.titantec.punzon.Clientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityRegistroClientesBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegistroClientes extends Fragment {

    EditText etname, etlastname, etid, etusername, etpassword,edtDireccion,edtTelefono;
    Spinner spid;
    String name,lastname,tipoDocumento,numeroId,username,password,direccion,telefono, id;
    Button btnRegCli;
    List<String> carrito = new ArrayList<>();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ActivityRegistroClientesBinding activityRegistroClientesBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityRegistroClientesBinding = activityRegistroClientesBinding.inflate(inflater, container, false);
        View root = activityRegistroClientesBinding.getRoot();

        etname =  activityRegistroClientesBinding.nomCliente;
        etlastname =  activityRegistroClientesBinding.apeCliente;
        etid =  activityRegistroClientesBinding.idCliente;
        etusername =  activityRegistroClientesBinding.userCliente;
        etpassword = activityRegistroClientesBinding.passwordCliente;
        spid = activityRegistroClientesBinding.spidCliente;
        edtDireccion = activityRegistroClientesBinding.EdtDireccion;
        edtTelefono = activityRegistroClientesBinding.EdtTelefono;

        btnRegCli = activityRegistroClientesBinding.BtnRegCliente;
        btnRegCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos(v);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistroClientesBinding = null;
    }

    private void validarDatos(View v) {
        name= etname.getText().toString();
        lastname = etlastname.getText().toString();
        tipoDocumento= spid.getSelectedItem().toString();
        numeroId = etid.getText().toString();
        username = etusername.getText().toString();
        password = etpassword.getText().toString();
        direccion = edtDireccion.getText().toString();
        telefono = edtTelefono.getText().toString();

        if (name.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
        }
        if (lastname.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un apellido", Toast.LENGTH_SHORT).show();
        }
        if (numeroId.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un número de documento", Toast.LENGTH_SHORT).show();
        }
        if (username.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar un Email de usuario", Toast.LENGTH_SHORT).show();
        }
        if (password.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
        }
        if (direccion.length() == 0) {
            Toast.makeText(v.getContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
        }else{
            registrar(v);
        }
    }

    private void registrar(View v) {
        if(auth.getCurrentUser()!=null) {
            Toast.makeText(v.getContext(), "Ya estas registrado", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(etusername.getText().toString(), etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        id = auth.getUid();
                        Map<String, Object> cliente = new HashMap<>();
                        cliente.put("nombre", name);
                        cliente.put("apellido", lastname);
                        cliente.put("tipoDocumento", tipoDocumento);
                        cliente.put("documento", numeroId);
                        cliente.put("email", username);
                        cliente.put("contraseña", password);
                        cliente.put("direccion", direccion);
                        cliente.put("numero",telefono);

                        firestore.collection("Clientes").document(id).set(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if (task2.isSuccessful()) {
                                    carrito(v);
                                } else {
                                    Toast.makeText(v.getContext(), "Fallo en el registro, " +
                                            "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(v.getContext(), "Fallo en el registro, " +
                                "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void carrito(View v){
        Map<String, Object> cliente = new HashMap<>();
        cliente.put("carrito", carrito);
        firestore.collection("Clientes").document(id).update(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(v.getContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    limpiar();
                    Intent inte = new Intent(v.getContext(), MainActivity.class);
                    startActivity(inte);
                } else {
                    Toast.makeText(v.getContext(), "Fallo en el registro, " +
                            "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpiar() {
        etname.setText("");
        etlastname.setText("");
        etid.setText("");
        etusername.setText("");
        etpassword.setText("");
        edtDireccion.setText("");
        edtTelefono.setText("");
        auth.signOut();
    }
}
