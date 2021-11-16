package com.titantec.ferreteriapunzon.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.titantec.ferreteriapunzon.Inventario.VerInventario;
import com.titantec.ferreteriapunzon.Modelos.Producto;
import com.titantec.ferreteriapunzon.R;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends FirestoreRecyclerAdapter <Producto, ProductoAdapter.ViewHolder>{
    private Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    List<Producto> productos = new ArrayList<>();

    public ProductoAdapter(@NonNull FirestoreRecyclerOptions<Producto> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position, @NonNull Producto model) {
        DocumentSnapshot productoDocumento = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = productoDocumento.getId();
        holder.txvDoc.setText(id);
        holder.txvNom.setText(model.getNombre());
        holder.txvApe.setText(String.valueOf(model.getPrecio()));
        Producto p = new Producto(model.getNombre(),model.getId(),model.getPrecio(),model.getDescripcion(),
                model.getCantidad(),model.getMarca());
        productos.add(p);
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsfire,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txvDoc,txvNom,txvApe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvDoc = itemView.findViewById(R.id.txvDoc);
            txvNom= itemView.findViewById(R.id.txvNom);
            txvApe = itemView.findViewById(R.id.txvApe);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent abrir = new Intent(context, VerInventario.class);

                    abrir.putExtra("Nombre",productos.get(getLayoutPosition()).getNombre());
                    abrir.putExtra("Id",productos.get(getLayoutPosition()).getId());
                    abrir.putExtra("Precio",productos.get(getLayoutPosition()).getPrecio());
                    abrir.putExtra("Descripcion",productos.get(getLayoutPosition()).getDescripcion());
                    abrir.putExtra("Cantidad",productos.get(getLayoutPosition()).getCantidad());
                    abrir.putExtra("Marca",productos.get(getLayoutPosition()).getMarca());
                    context.startActivity(abrir);
                }
            });
        }
    }
}
