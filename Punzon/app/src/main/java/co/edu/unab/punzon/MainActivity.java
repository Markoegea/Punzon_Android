package co.edu.unab.punzon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Método ir al Registro Empleados principal
    public void Empleados(View view) {
        Intent irregempleados = new Intent(this, RegistroEmpleados.class);
        startActivity(irregempleados);
    }

    // Método ir al Registro Empleado Especial principal
    public void EmpleadoEspecial(View view) {
        Intent empleadoespecial = new Intent(this, EmpleadoEspecial.class);
        startActivity(empleadoespecial);
    }

    //Método ir a la tienda
    public void VerTienda(View view) {
        Intent producto = new Intent(this, ProductosActivity.class);
        startActivity(producto);
    }

    // Método ir al Registro Clientes
    public void Clientes(View view) {
        Intent clientes = new Intent(this, RegistroClientes.class);
        startActivity(clientes);
    }
}
