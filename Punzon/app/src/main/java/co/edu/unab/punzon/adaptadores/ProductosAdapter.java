package co.edu.unab.punzon.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.unab.punzon.R;
import co.edu.unab.punzon.data.model.Producto;
import co.edu.unab.punzon.inventario.ModificarInventario;

public class ProductosAdapter extends RecyclerView.Adapter <ProductosAdapter.ViewHolder> {
private Context mContext;
private List<Producto> productos;
private int intPosition;

    public ProductosAdapter(Context mContext, List<Producto> productos) {
        this.mContext = mContext;
        this.productos = productos;
    }


    @NonNull
    @Override
    public ProductosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ViewHolder(inflater.inflate(R.layout.item_producto, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ProductosAdapter.ViewHolder holder, int position) {
        holder.imprimir(position);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvNombre, txvPrecio;
        private ImageView imv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvNombre = itemView.findViewById(R.id.txtNombre);
            txvPrecio = itemView.findViewById(R.id.txtPrecio);
            imv = itemView.findViewById(R.id.imvProducto);
            itemView.setOnClickListener(this::onClick);
        }


        public void imprimir(int position) {
            imv.setImageResource(productos.get(position).getImagen());
            txvNombre.setText(productos.get(position).getNombre());
            txvPrecio.setText("" + productos.get(position).getPrecio());
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(mContext, "ha seleccionado: "+productos.get(getLayoutPosition()).getNombre(), Toast.LENGTH_LONG).show();
            Intent producto = new Intent(mContext, ModificarInventario.class);

            producto.putExtra("Nombre",productos.get(getLayoutPosition()).getNombre());
            producto.putExtra("Id",productos.get(getLayoutPosition()).getId());
            producto.putExtra("Precio",productos.get(getLayoutPosition()).getPrecio());
            producto.putExtra("Descripcion",productos.get(getLayoutPosition()).getDescripcion());
            producto.putExtra("Imagen",productos.get(getLayoutPosition()).getImagen());
            producto.putExtra("Cantidad",productos.get(getLayoutPosition()).getCantidad());
            producto.putExtra("Marca",productos.get(getLayoutPosition()).getMarca());

            mContext.startActivity(producto);
        }
    }
}
