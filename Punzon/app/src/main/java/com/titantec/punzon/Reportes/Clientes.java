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
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.MainPageBinding;

import java.util.ArrayList;
import java.util.List;

public class Clientes extends Fragment {
    RecyclerView rv;
    MainPageBinding mainPageBinding;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ClienteAdapter ClienteAdapter;
    List<com.titantec.punzon.Modelos.Clientes> clientesList = new ArrayList<>();
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
        Query query = firestore.collection("Clientes");
        FirestoreRecyclerOptions<com.titantec.punzon.Modelos.Clientes> firestoreRO =
                new FirestoreRecyclerOptions.Builder<com.titantec.punzon.Modelos.Clientes>().setQuery(query, com.titantec.punzon.Modelos.Clientes.class).build();
        ClienteAdapter = new ClienteAdapter(firestoreRO);
        ClienteAdapter.notifyDataSetChanged();
        rv.setAdapter(ClienteAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        ClienteAdapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ClienteAdapter.stopListening();
        mainPageBinding = null;
    }

    public class ClienteAdapter extends FirestoreRecyclerAdapter<com.titantec.punzon.Modelos.Clientes, ClienteAdapter.ViewHolder>{

        public ClienteAdapter (@NonNull FirestoreRecyclerOptions<com.titantec.punzon.Modelos.Clientes> options){
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull com.titantec.punzon.Modelos.Clientes model) {
            DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
            final String id= documentSnapshot.getId();
            holder.txvDoc.setText(id);
            holder.txvNom.setText(model.getNombre());
            holder.txvApe.setText(model.getApellido());
            com.titantec.punzon.Modelos.Clientes c = new com.titantec.punzon.Modelos.Clientes(model.getNombre(), model.getApellido(), model.getTipoDocumento(),
                    model.getDocumento(), model.getNumero(), model.getEmail(), model.getContraseña(), model.getDireccion(), model.getImagen(), model.getCarrito());
            clientesList.add(c);
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

                        bundle.putString("Apellido",clientesList.get(getLayoutPosition()).getApellido());
                        bundle.putString("Contrasena",clientesList.get(getLayoutPosition()).getContraseña());
                        bundle.putString("Email",clientesList.get(getLayoutPosition()).getEmail());
                        bundle.putString("Nombre",clientesList.get(getLayoutPosition()).getNombre());
                        bundle.putString("Numero",clientesList.get(getLayoutPosition()).getNumero());
                        bundle.putString("Numero de documento",clientesList.get(getLayoutPosition()).getDocumento());
                        bundle.putString("Direccion",clientesList.get(getLayoutPosition()).getDocumento());
                        bundle.putString("Imagen",clientesList.get(getLayoutPosition()).getDocumento());
                        bundle.putString("Tipo de documento",clientesList.get(getLayoutPosition()).getTipoDocumento());
                        getParentFragmentManager().setFragmentResult("param1",bundle);

                        abrir.navigate(R.id.Ver_Inventario);
                    }
                });
            }
        }
    }
}
