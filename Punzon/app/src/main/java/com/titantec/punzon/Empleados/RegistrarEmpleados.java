package com.titantec.punzon.Empleados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityRegistroEmpleadosBinding;

import java.util.HashMap;
import java.util.Map;

public class RegistrarEmpleados extends Fragment {
    private EditText etname, etlastname, etDocumento, etEmail, etpassword,edtNumero;
    private Spinner spid, sptemp, spcargo, spespecialidad;
    private Button btnRegisEmpleado;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

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

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistroEmpleadosBinding = null;
    }

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
        }else{
            registrar(v);
        }
    }
    private void registrar(View v) {
        auth.createUserWithEmailAndPassword(etEmail.getText().toString(),etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String id = auth.getUid();
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
                    empleado.put("numero_de_documento",etDocumento.getText().toString());
                    firestore.collection("Empleados").document(id).set(empleado).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(v.getContext(),"Registro Exitoso",Toast.LENGTH_SHORT).show();
                                limpiar();
                            } else{
                                Toast.makeText(v.getContext(),"Fallo en el registro, " +
                                        "Revisalo y Intentalo otra vez",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(v.getContext(),"Fallo en el registro, " +
                            "Revisalo y Intentalo otra vez",Toast.LENGTH_SHORT).show();
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
