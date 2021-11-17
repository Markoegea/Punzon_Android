package com.titantec.ferreteriapunzon.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.titantec.ferreteriapunzon.MainActivity;
import com.titantec.ferreteriapunzon.R;

public class RegistroClientes extends AppCompatActivity {

     EditText etname, etname2, etlastname, etlastname2, etid, etusarname, etpassword;
     Spinner spid;
     Button btnRegCli;
    ImageButton iBtnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_clientes);
        etname = (EditText) findViewById(R.id.nomCliente);
        etname2 = (EditText) findViewById(R.id.nomCliente2);
        etlastname = (EditText) findViewById(R.id.apeCliente);
        etlastname2 = (EditText) findViewById(R.id.apeCliente2);
        etid = (EditText) findViewById(R.id.idCliente);
        etusarname = (EditText) findViewById(R.id.userCliente);
        etpassword = (EditText) findViewById(R.id.passwordCliente);
        iBtnRegresar = findViewById(R.id.IbtnRegresar);
        iBtnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(RegistroClientes.this, MainActivity.class);
                startActivity(menu);
                finish();
            }
        });

        spid = (Spinner) findViewById(R.id.spid_cliente);

        btnRegCli = (Button) findViewById(R.id.btnRegCliente);
    }
}