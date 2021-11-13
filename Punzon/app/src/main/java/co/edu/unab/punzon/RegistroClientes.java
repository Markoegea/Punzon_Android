package co.edu.unab.punzon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroClientes extends AppCompatActivity {

    private EditText etname, etname2, etlastname, etlastname2, etid, etusarname, etpassword;
    private Spinner spid;
    private Button btnRegCli;


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

        spid = (Spinner) findViewById(R.id.spid_cliente);

        btnRegCli = (Button) findViewById(R.id.btnRegCliente);
    }

    public void Menu(View view) {
        Intent menu = new Intent(this, MainActivity.class);
        startActivity(menu);
    }
}
