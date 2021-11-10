package co.edu.unab.punzon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import co.edu.unab.punzon.adaptadores.ProductosAdapter;
import co.edu.unab.punzon.data.model.Producto;
import co.edu.unab.punzon.inventario.ModificarInventario;

public class ProductosActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        RecyclerView productos  = findViewById(R.id.rcvProductos);
        ProductosAdapter adapter = new ProductosAdapter (this,  Producto.buldierProductos());
        productos.setAdapter(adapter);
        productos.setLayoutManager(new LinearLayoutManager(this));
    }

}