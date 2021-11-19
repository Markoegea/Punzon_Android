package com.titantec.punzon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.punzon.Modelos.Producto;
import com.titantec.punzon.databinding.MainPageBinding;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends Fragment {
    RecyclerView rv;
    MainPageBinding mainPageBinding;
    ProductoAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainPageBinding = MainPageBinding.inflate(inflater, container, false);
        View root = mainPageBinding.getRoot();

        rv = mainPageBinding.recyclerView;
        rv.setLayoutManager(new LinearLayoutManager(mainPageBinding.recyclerView.getContext()));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query = firestore.collection("Producto");
        FirestoreRecyclerOptions<Producto> firestoreRecyclerOptions
                = new FirestoreRecyclerOptions.Builder<Producto>().setQuery(query, Producto.class).build();

        adapter = new ProductoAdapter(firestoreRecyclerOptions,mainPageBinding.recyclerView.getContext());
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainPageBinding = null;
    }

    public class ProductoAdapter extends FirestoreRecyclerAdapter<Producto, ProductoAdapter.ViewHolder> {
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
            return new ProductoAdapter.ViewHolder(view);
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
                        NavController abrir = Navigation.findNavController(v);

                        Bundle bundle = new Bundle();
                        bundle.putString("Nombre",productos.get(getLayoutPosition()).getNombre());
                        bundle.putString("Id",String.valueOf(productos.get(getLayoutPosition()).getId()));
                        bundle.putString("Precio",String.valueOf(productos.get(getLayoutPosition()).getPrecio()));
                        bundle.putString("Descripcion",productos.get(getLayoutPosition()).getDescripcion());
                        bundle.putString("Cantidad",String.valueOf(productos.get(getLayoutPosition()).getCantidad()));
                        bundle.putString("Marca",productos.get(getLayoutPosition()).getMarca());
                        getParentFragmentManager().setFragmentResult("param1",bundle);

                        abrir.navigate(R.id.Ver_Inventario);
                    }
                });
            }
        }
    }

}
