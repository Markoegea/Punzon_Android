package com.titantec.punzon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.punzon.Modelos.Productos;
import com.titantec.punzon.databinding.MainPageBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPage extends Fragment {
    RecyclerView rv;
    static MainPageBinding mainPageBinding;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ProductoAdapter productoAdapter;
    ArrayList<Productos> productosList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if(mainPageBinding == null) {
            mainPageBinding = MainPageBinding.inflate(inflater, container, false);
        }

        View root = mainPageBinding.getRoot();

        rv = mainPageBinding.recyclerView;
        rv.setLayoutManager(new LinearLayoutManager(mainPageBinding.recyclerView.getContext()));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query = firestore.collection("Productos");
        FirestoreRecyclerOptions<Productos> firestoreRO =
                new FirestoreRecyclerOptions.Builder<Productos>().setQuery(query, Productos.class).build();
        productoAdapter = new ProductoAdapter(firestoreRO, view);
        productoAdapter.notifyDataSetChanged();
        rv.setAdapter(productoAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        productoAdapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        productoAdapter.stopListening();
        mainPageBinding = null;
    }

    public class ProductoAdapter extends FirestoreRecyclerAdapter<Productos, ProductoAdapter.ViewHolder> {
        private ArrayList<Productos> listaCarro;
        private View view;

        public ProductoAdapter(@NonNull FirestoreRecyclerOptions<Productos> options, View v) {
            super(options);
            this.view = v;
            listaCarro = new ArrayList<>();

        }

        @Override
        protected void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position, @NonNull Productos model) {
            DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
            final String id = documentSnapshot.getId();

            holder.txvProducto.setText(model.getNombre());
            holder.txvCosto.setText(model.getPrecio());


            Glide.with(view.getContext()).load(model.getImagen()).into(holder.imgProduct);

            Productos p = new Productos(model.getNombre(), model.getId(), model.getPrecio(),
                    model.getDescripcion(), model.getImagen(), model.getCantidad(), model.getMarca());
            productosList.add(p);
        }

        @NonNull
        @Override
        public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
            return new ProductoAdapter.ViewHolder(view);
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txvProducto, txvCosto;
            Button btnAgregar;
            ImageView imgProduct;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txvProducto = itemView.findViewById(R.id.txvProducto);
                txvCosto = itemView.findViewById(R.id.txvApe);
                btnAgregar = itemView.findViewById(R.id.btnAgregar);
                imgProduct = itemView.findViewById(R.id.imgProduct);


                btnAgregar.setOnClickListener(agregarProductoClickListener);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavController abrir = Navigation.findNavController(v);

                        Bundle bundle = new Bundle();
                        bundle.putString("Nombre", productosList.get(getLayoutPosition()).getNombre());
                        bundle.putString("Id", productosList.get(getLayoutPosition()).getId());
                        bundle.putString("Precio", productosList.get(getLayoutPosition()).getPrecio());
                        bundle.putString("Descripcion", productosList.get(getLayoutPosition()).getDescripcion());
                        bundle.putString("Cantidad", productosList.get(getLayoutPosition()).getCantidad());
                        bundle.putString("Marca", productosList.get(getLayoutPosition()).getMarca());
                        bundle.putString("Imagen", productosList.get(getLayoutPosition()).getImagen());
                        getParentFragmentManager().setFragmentResult("param1", bundle);

                        abrir.navigate(R.id.Ver_Inventario);
                    }
                });
            }

            private View.OnClickListener agregarProductoClickListener = view -> {
                listaCarro.add(productosList.get(getBindingAdapterPosition()));
                MainActivity actividadCatalogo = (MainActivity) MainPage.this.getActivity();
                actividadCatalogo.actualizarNotificacion(listaCarro);
                compraBaseDeDatos(productosList.get(getBindingAdapterPosition()));
            };

            private void compraBaseDeDatos(Productos producto) {
                /*Map<String, Object> productoMap = new HashMap<>();

                productoMap.put("id", producto.getId());

                firestore.collection("Ventas").document("").set(producto).addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                        limpiar();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(v.getContext(), "Fallo en el registro, " +
                                "Revisalo y Intentalo otra vez", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });*/
            }
        }
    }
}
