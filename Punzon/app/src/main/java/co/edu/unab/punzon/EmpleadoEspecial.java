package co.edu.unab.punzon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EmpleadoEspecial extends AppCompatActivity {

    private EditText etname, etname2, etlastname, etlastname2, etid, etusarname, etpassword;
    private Spinner spid, sptemp, spcargo, spespecialidad;
    private Button btnregemespecial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_especial);

        etname = (EditText) findViewById(R.id.txt_name);
        etname2 = (EditText) findViewById(R.id.txt_name2);
        etlastname = (EditText) findViewById(R.id.txt_last_name);
        etlastname2 = (EditText) findViewById(R.id.txt_last_name2);
        etid = (EditText) findViewById(R.id.txt_id);
        etusarname = (EditText) findViewById(R.id.txt_username);
        etpassword = (EditText) findViewById(R.id.txt_password);

        spid = (Spinner) findViewById(R.id.sp_id_type);
        sptemp = (Spinner) findViewById(R.id.sp_empleado_type);
        spcargo = (Spinner) findViewById(R.id.sp_cargo_type);
        spespecialidad = (Spinner) findViewById(R.id.sp_empleado_type);

        btnregemespecial = (Button) findViewById(R.id.btnRegEmEspecial);


    }

    // Método ir al menu principal
    public void Menu(View view) {
        Intent menu = new Intent(this, MainActivity.class);
        startActivity(menu);
    }
}