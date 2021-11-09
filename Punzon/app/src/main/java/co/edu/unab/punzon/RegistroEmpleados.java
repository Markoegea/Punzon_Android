package co.edu.unab.punzon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegistroEmpleados extends AppCompatActivity {

    private EditText etname, etname2, etlastname, etlastname2, etid, etusarname, etpassword;
    private Spinner spid, sptemp, spcargo;
    private Button btnregisempleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_empleados);

        etname = (EditText) findViewById(R.id.txtname);
        etname2 = (EditText) findViewById(R.id.txtname2);
        etlastname = (EditText) findViewById(R.id.txtlast_name);
        etlastname2 = (EditText) findViewById(R.id.txtlast_name2);
        etid = (EditText) findViewById(R.id.txtid);
        etusarname = (EditText) findViewById(R.id.txtusername);
        etpassword = (EditText) findViewById(R.id.txtpassword);

        spid = (Spinner) findViewById(R.id.spid_type);
        sptemp = (Spinner) findViewById(R.id.spempleado_type);
        spcargo = (Spinner) findViewById(R.id.spcargo_type);


        btnregisempleado = (Button) findViewById(R.id.btnRegisEmpleado);
    }

    // Método ir al menu principal
    public void Menu(View view){
        Intent menu = new Intent(this,MainActivity.class);
        startActivity(menu);



    }
}