package com.titantec.ferreteriapunzon.Inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.titantec.ferreteriapunzon.R;

public class VerInventario extends AppCompatActivity {

    TextView txvnombre,txvid,txvprecio,txvdescripcion,txvcantidad,txvmarca;
    ImageView imgproducto;
    String nombre;
    long id;
    double precio;
    String descripcion;
    int imagen;
    int cantidad;
    String marca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_inventario);

        txvnombre=findViewById(R.id.TvNombre);
        txvprecio=findViewById(R.id.TvPrecio);
        txvdescripcion=findViewById(R.id.TvDescripcion);
        txvcantidad=findViewById(R.id.TvCantidad);
        txvmarca=findViewById(R.id.TvMarca);
        imgproducto=findViewById(R.id.ImgProducto);
        Bundle parametros= this.getIntent().getExtras();
        nombre = parametros.getString("Nombre");
        id = parametros.getLong("Id");
        precio = parametros.getDouble("Precio");
        descripcion = parametros.getString("Descripcion");
        cantidad = parametros.getInt("Cantidad");
        marca = parametros.getString("Marca");
        txvnombre.setText(nombre);
        txvprecio.setText(precio+"");
        txvdescripcion.setText(descripcion+"");
        txvcantidad.setText(cantidad+"");
        txvmarca.setText(marca+"");
    }
}