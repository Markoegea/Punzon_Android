package com.titantec.punzon.Reportes;

import android.annotation.SuppressLint;
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
import com.titantec.punzon.Modelos.Empleado;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.MainPageBinding;

import java.util.ArrayList;
import java.util.List;

public class Empleados extends Fragment {
    RecyclerView rv;
    MainPageBinding mainPageBinding;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    EmpleadoAdapter EmpleadoAdapter;
    List<Empleado> EmpleadoList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainPageBinding = MainPageBinding.inflate(inflater, container, false);
        View root = mainPageBinding.getRoot();

        rv = mainPageBinding.recyclerView;
        rv.setLayoutManager(new LinearLayoutManager(mainPageBinding.recyclerView.getContext()));

        return root;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query = firestore.collection("Empleado");
        FirestoreRecyclerOptions<Empleado> firestoreRO =
                new FirestoreRecyclerOptions.Builder<Empleado>().setQuery(query, Empleado.class).build();
        EmpleadoAdapter = new EmpleadoAdapter(firestoreRO);
        EmpleadoAdapter.notifyDataSetChanged();
        rv.setAdapter(EmpleadoAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EmpleadoAdapter.startListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EmpleadoAdapter.stopListening();
        mainPageBinding = null;
    }

    public class EmpleadoAdapter extends FirestoreRecyclerAdapter<Empleado, EmpleadoAdapter.ViewHolder>{

        public EmpleadoAdapter (@NonNull FirestoreRecyclerOptions<Empleado> options){
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Empleado model) {
            DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
            final String id= documentSnapshot.getId();
            holder.txvDoc.setText(id);
            holder.txvNom.setText(model.getNombre());
            holder.txvApe.setText(model.getApellido());
            Empleado p = new Empleado(model.getNombre(), model.getApellido(), model.getTipoDocumento(),
                    model.getDocumento(), model.getTipoEmpleado(), model.getCargo(), model.getEspecialidad(), model.getNumero(), model.getEmail(), model.getContraseña(), model.getImagen());
            EmpleadoList.add(p);
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
                        bundle.putString("Apellido",EmpleadoList.get(getLayoutPosition()).getApellido());
                        bundle.putString("Cargo",EmpleadoList.get(getLayoutPosition()).getCargo());
                        bundle.putString("Contrasena",EmpleadoList.get(getLayoutPosition()).getContraseña());
                        bundle.putString("Email",EmpleadoList.get(getLayoutPosition()).getEmail());
                        bundle.putString("Especialidad",EmpleadoList.get(getLayoutPosition()).getEspecialidad());
                        bundle.putString("Nombre",EmpleadoList.get(getLayoutPosition()).getNombre());
                        bundle.putString("Numero",EmpleadoList.get(getLayoutPosition()).getNumero());
                        bundle.putString("Numero de documento",EmpleadoList.get(getLayoutPosition()).getDocumento());
                        bundle.putString("Tipo de documento",EmpleadoList.get(getLayoutPosition()).getTipoDocumento());
                        bundle.putString("Tipo de empleado",EmpleadoList.get(getLayoutPosition()).getTipoEmpleado());
                        getParentFragmentManager().setFragmentResult("param1",bundle);

                        abrir.navigate(R.id.Ver_Empleados);
                    }
                });
            }
        }
    }
}
