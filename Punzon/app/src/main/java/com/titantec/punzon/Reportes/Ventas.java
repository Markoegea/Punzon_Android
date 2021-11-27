package com.titantec.punzon.Reportes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.punzon.Modelos.Productos;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.MainPageBinding;

import java.util.ArrayList;
import java.util.List;

public class Ventas extends Fragment {
    RecyclerView rv;
    MainPageBinding mainPageBinding;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ProductoAdapter productoAdapter;
    List<Productos> productosList = new ArrayList<>();
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
        Query query = firestore.collection("Productos");
        FirestoreRecyclerOptions<Productos> firestoreRO =
                new FirestoreRecyclerOptions.Builder<Productos>().setQuery(query, Productos.class).build();
        productoAdapter = new ProductoAdapter(firestoreRO);
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

    public class ProductoAdapter extends FirestoreRecyclerAdapter<Productos, ProductoAdapter.ViewHolder>{

        public ProductoAdapter (@NonNull FirestoreRecyclerOptions<Productos> options){
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Productos model) {
            DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
            final String id= documentSnapshot.getId();
            holder.txvDoc.setText(id);
            holder.txvNom.setText(model.getNombre());
            holder.txvApe.setText(model.getPrecio());
            Productos p = new Productos(model.getNombre(),model.getId(), model.getPrecio(),
                    model.getDescripcion(),model.getCantidad(),model.getMarca());
            productosList.add(p);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsfire, parent, false);
            return new ViewHolder(view);
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txvDoc,txvNom,txvApe;
            public ViewHolder(@NonNull View itemView){
                super(itemView);
                txvDoc = itemView.findViewById(R.id.txvDoc);
                txvNom= itemView.findViewById(R.id.txvNom);
                txvApe = itemView.findViewById(R.id.txvApe);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavController abrir = Navigation.findNavController(v);

                        Bundle bundle = new Bundle();

                        /*Aqui va el modelo de datos*/

                        abrir.navigate(R.id.Ver_Inventario);
                    }
                });
            }
        }
    }
}
