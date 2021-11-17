package com.titantec.ferreteriapunzon.Inventario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.titantec.ferreteriapunzon.R;

public class EditarInventario extends AppCompatActivity {

    EditText txvnombre,txvid,txvprecio,txvdescripcion,txvcantidad,txvmarca;
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
        setContentView(R.layout.activity_editar_inventario);

        txvnombre=findViewById(R.id.txvNombre);
        txvid=findViewById(R.id.txvId);
        txvprecio=findViewById(R.id.txvPrecio);
        txvdescripcion=findViewById(R.id.txvDescripcion);
        txvcantidad=findViewById(R.id.txvCantidad);
        txvmarca=findViewById(R.id.txvMarca);
        imgproducto=findViewById(R.id.ImgProducto);
        Bundle parametros= this.getIntent().getExtras();
        nombre = parametros.getString("Nombre");
        id = parametros.getLong("Id");
        precio = parametros.getDouble("Precio");
        descripcion = parametros.getString("Descripcion");
        cantidad = parametros.getInt("Cantidad");
        marca = parametros.getString("Marca");
        txvnombre.setText(nombre);
        txvid.setText(id+"");
        txvprecio.setText(precio+"");
        txvdescripcion.setText(descripcion+"");
        txvcantidad.setText(cantidad+"");
        txvmarca.setText(marca+"");
    }
}