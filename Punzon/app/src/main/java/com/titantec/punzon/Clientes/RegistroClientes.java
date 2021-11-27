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

import java.util.HashMap;
import java.util.Map;


public class RegistroClientes extends Fragment {

    EditText etname, etlastname, etid, etusername, etpassword,edtDireccion;
    Spinner spid;
    String name,lastname,tipoDocumento,numeroId,username,password,direccion;
    Button btnRegCli;
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
        btnRegCli = activityRegistroClientesBinding.btnRegCliente;
        edtDireccion = activityRegistroClientesBinding.EdtDireccion;
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
        String name= etname.getText().toString();
        String lastname = etlastname.getText().toString();
        String tipoDocumento= spid.getSelectedItem().toString();
        String numeroId = etid.getText().toString();
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();
        String direccion = edtDireccion.getText().toString();

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
                        String id = auth.getUid();
                        Map<String, Object> empleado = new HashMap<>();
                        empleado.put("nombre", name);
                        empleado.put("apellido", lastname);
                        empleado.put("tipoDocumento", tipoDocumento);
                        empleado.put("tipoEmpleado", numeroId);
                        empleado.put("cargo", username);
                        empleado.put("especialidad", password);
                        empleado.put("email", direccion);
                        firestore.collection("Empleados").document(id).set(empleado).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if (task2.isSuccessful()) {
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
                    } else {
                        Toast.makeText(v.getContext(), "Fallo en el registro, " +
                                "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void limpiar() {
        etname.setText("");
        etlastname.setText("");
        spid.clearFocus();
        etid.setText("");
        etusername.setText("");
        etpassword.setText("");
    }
}
