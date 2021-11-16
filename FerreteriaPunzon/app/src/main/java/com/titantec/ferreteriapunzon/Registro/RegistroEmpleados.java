package com.titantec.ferreteriapunzon.Registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.titantec.ferreteriapunzon.MainActivity;
import com.titantec.ferreteriapunzon.R;

import java.util.HashMap;
import java.util.Map;

public class RegistroEmpleados extends AppCompatActivity {

    private EditText etname, etname2, etlastname, etlastname2, etDocumento, etEmail, etpassword,edtNumero;
    private Spinner spid, sptemp, spcargo, spespecialidad;
    private Button btnRegisEmpleado, btnEliminarEmpleado, btnEditarEmpleado, btnConsultarEmpleado, btnSalir;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleados);

        etname = (EditText) findViewById(R.id.txtname);
        etname2 = (EditText) findViewById(R.id.txtname2);
        etlastname = (EditText) findViewById(R.id.txtlastname);
        etlastname2 = (EditText) findViewById(R.id.txtlastname2);
        etDocumento = (EditText) findViewById(R.id.txtDocumento);
        etEmail = (EditText) findViewById(R.id.txtEmail);
        etpassword = (EditText) findViewById(R.id.txtpassword);
        edtNumero= findViewById(R.id.EdtNumero);

        spid = (Spinner) findViewById(R.id.spid_type);
        sptemp = (Spinner) findViewById(R.id.spempleado_type);
        spcargo = (Spinner) findViewById(R.id.spcargo_type);
        spespecialidad = (Spinner) findViewById(R.id.spespempleado_type);

        btnRegisEmpleado = (Button) findViewById(R.id.btnRegisEmpleado);
        btnEditarEmpleado = (Button) findViewById(R.id.btnEditarEmpleado);
        btnSalir = findViewById(R.id.irMenu);

        // Lista para tipo de documento
        String[] tipoDocumento = {"Seleccione...", "Cédula de ciudadanía", "Tarjeta de identidad", "Cedula de extranjería", "Pasaporte"};
        ArrayAdapter<String> tipoDocumentoAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, tipoDocumento);
        spid.setAdapter(tipoDocumentoAdapter);

        // Lista para tipo de empleado
        String[] tipoEmpleado = {"Seleccione...", "Planta", "Prestador de servicios"};
        ArrayAdapter<String> tipoEmpleadoAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, tipoEmpleado);
        sptemp.setAdapter(tipoEmpleadoAdapter);

        // Lista para cargo
        String[] cargo = {"Seleccione...", "Gerente", "Contador", "Vendedor", "Asesor comercial", "Auxuliar de bodega", "Jefe de bodega", "Domiciliario", "Vendedor", "Conductor", "Domiciliario", "Servicios generales", "Servicios especiales"};
        ArrayAdapter<String> cargoAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, cargo);
        spcargo.setAdapter(cargoAdapter);

        // Lista para especialidad
        String[] especialidad = {"Seleccione...", "No aplica", "Maestro de obra", "Plomero", "Eléctrico", "Soldador", "Otro"};
        ArrayAdapter<String> especialidadAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, especialidad);
        spespecialidad.setAdapter(especialidadAdapter);

        btnRegisEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        btnEditarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu();
            }
        });
    }

    private void validarDatos() {
        String nombre1String = etname.getText().toString();
        String apellido1String = etlastname.getText().toString();
        String documentoString = etDocumento.getText().toString();
        String usuarioString = etEmail.getText().toString();
        String contraseñaString = etpassword.getText().toString();
        String numeroTelefonoString = edtNumero.getText().toString();
        if (nombre1String.length() == 0) {
            Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
        }
        if (apellido1String.length() == 0) {
            Toast.makeText(this, "Debe ingresar un apellido", Toast.LENGTH_SHORT).show();
        }
        if (documentoString.length() == 0) {
            Toast.makeText(this, "Debe ingresar un número de documento", Toast.LENGTH_SHORT).show();
        }
        if (usuarioString.length() == 0) {
            Toast.makeText(this, "Debe ingresar un Email de usuario", Toast.LENGTH_SHORT).show();
        }
        if (contraseñaString.length() == 0) {
            Toast.makeText(this, "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
        }else{
            registrar();
        }
    }

    private void registrar() {
        auth.createUserWithEmailAndPassword(etEmail.getText().toString(),etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String id = auth.getUid();
                    Map<String, Object> empleado = new HashMap<>();
                    empleado.put("nombre1", etname.getText().toString()+" "+etname2.getText().toString());
                    empleado.put("apellido1", etlastname.getText().toString()+" "+etlastname2.getText().toString());
                    empleado.put("tipoDocumento", spid.getSelectedItem().toString());
                    empleado.put("tipoEmpleado", sptemp.getSelectedItem().toString());
                    empleado.put("cargo", spcargo.getSelectedItem().toString());
                    empleado.put("especialidad", spespecialidad.getSelectedItem().toString());
                    empleado.put("email", etEmail.getText().toString());
                    empleado.put("contraseña", etpassword.getText().toString());
                    empleado.put("numero", edtNumero.getText().toString());
                    firestore.collection("Empleados").document(id).set(empleado).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(RegistroEmpleados.this,"Registro Exitoso",Toast.LENGTH_SHORT).show();
                                limpiar();
                            } else{
                                Toast.makeText(RegistroEmpleados.this,"Fallo en el registro, " +
                                        "Revisalo y Intentalo otra vez",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegistroEmpleados.this,"Fallo en el registro, " +
                            "Revisalo y Intentalo otra vez",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpiar() {
        etname.setText("");
        etname2.setText("");
        etlastname.setText("");
        etlastname2.setText("");
        etDocumento.setText("");
        etEmail.setText("");
        etpassword.setText("");
        edtNumero.setText("");
    }

    private void editar() {

        Map<String, Object> empleado = new HashMap<>();
        empleado.put("nombre1", etname.getText().toString());
        empleado.put("nombre2", etname2.getText().toString());
        empleado.put("apellido1", etlastname.getText().toString());
        empleado.put("apellido2", etlastname2.getText().toString());
        empleado.put("tipoDocumento", spid.getSelectedItem().toString());
        empleado.put("tipoEmpleado", sptemp.getSelectedItem().toString());
        empleado.put("cargo", spcargo.getSelectedItem().toString());
        empleado.put("especialidad", spespecialidad.getSelectedItem().toString());
        empleado.put("Email", etEmail.getText().toString());
        empleado.put("contraseña", etpassword.getText().toString());
        empleado.put("Numero de telefono", edtNumero.getText().toString());

        firestore.collection("Empleados").document(etDocumento.getText().toString()).update(empleado);

        Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

    }


    // Método ir al menu principal
    public void Menu(){
        Intent menu = new Intent(this, MainActivity.class);
        startActivity(menu);
        finish();
        Toast.makeText(this, "Se ha movido al menu principal", Toast.LENGTH_SHORT).show();

    }
}