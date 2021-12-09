package com.titantec.punzon.Inventario;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.Modelos.Productos;
import com.titantec.punzon.R;

import java.util.ArrayList;

public class ShoppingCarFragment extends Fragment {

    private ArrayList<Productos> listaProductos;
    private TextView txtTotalProductos;
    private TextView txtTotalPrecio;
    private MainActivity mainActivity;

    public ShoppingCarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaProductos = getArguments().getParcelableArrayList("product_key");

        mainActivity = (MainActivity) getActivity();
        mainActivity.showMenuItem(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container
            , Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.shopping_car, container, false);

        final RecyclerView rvProductos = view.findViewById(R.id.rv_shopping_car);
        rvProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProductos.setAdapter(new ProductoAdapter(listaProductos, getContext()));

        txtTotalProductos = view.findViewById(R.id.txt_total_productos);
        txtTotalPrecio = view.findViewById(R.id.txt_precio_total);

        updateInfo(listaProductos);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mainActivity.showMenuItem(true);
        mainActivity.actualizarNotificacion(listaProductos);
    }

    private void updateInfo(final ArrayList<Productos> listaProductos) {
        txtTotalProductos.setText("Cantidad total de productos: " + listaProductos.size());
        txtTotalPrecio.setText("Precio total: $" + getPrecioTotal(listaProductos));
    }

    private double getPrecioTotal(final ArrayList<Productos> listaProductos) {
        double totalPrecio = 0;

        for (Productos mProducto : listaProductos) {
            totalPrecio += Double.parseDouble(mProducto.getPrecio());
        }

        return totalPrecio;
    }

    public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

        private final ArrayList<Productos> listaProductos;
        private final Context mContext;

        public ProductoAdapter(final ArrayList<Productos> options, final Context mContext) {
            listaProductos = options;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
            final Context context = parent.getContext();
            final LayoutInflater inflater = LayoutInflater.from(context);
            final View contactView = inflater.inflate(R.layout.item_producto, parent, false);

            return new ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Productos mProducto = listaProductos.get(position);

            Glide.with(mContext).load(mProducto.getImagen()).into(holder.imgProduct);
            holder.txvProducto.setText(mProducto.getNombre());
            holder.txvCosto.setText(mProducto.getPrecio());
            holder.btnEliminar.setText("Eliminar");
        }

        @Override
        public int getItemCount() {
            return listaProductos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txvProducto, txvCosto;
            Button btnEliminar;
            ImageView imgProduct;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txvProducto = itemView.findViewById(R.id.txvProducto);
                txvCosto = itemView.findViewById(R.id.txvApe);
                btnEliminar = itemView.findViewById(R.id.btnAgregar);
                imgProduct = itemView.findViewById(R.id.imgProduct);

                btnEliminar.setOnClickListener(view -> {
                    listaProductos.remove(getBindingAdapterPosition());
                    notifyDataSetChanged();
                    updateInfo(listaProductos);
                });
            }
        }
    }
}