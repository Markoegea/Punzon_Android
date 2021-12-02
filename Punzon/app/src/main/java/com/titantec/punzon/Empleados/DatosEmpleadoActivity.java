package com.titantec.punzon.Empleados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.punzon.Modelos.Empleado;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityDatosEmpleadoBinding;


public class DatosEmpleadoActivity extends Fragment {
    RecyclerView rv;
    EmpleadoAdapter adapter;
    FirebaseFirestore firestore= FirebaseFirestore.getInstance();
    ActivityDatosEmpleadoBinding activityDatosEmpleadoBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityDatosEmpleadoBinding = activityDatosEmpleadoBinding.inflate(inflater, container, false);
        View root = activityDatosEmpleadoBinding.getRoot();

        rv = activityDatosEmpleadoBinding.rvEmpleados;
        rv.setLayoutManager(new LinearLayoutManager(activityDatosEmpleadoBinding.rvEmpleados.getContext()));


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Query query = firestore.collection("Empleado");
        FirestoreRecyclerOptions<Empleado> firestoreRO =
                new FirestoreRecyclerOptions.Builder<Empleado>().setQuery(query,Empleado.class).build();
        adapter= new EmpleadoAdapter(firestoreRO,view);
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
        adapter.stopListening();
        activityDatosEmpleadoBinding = null;
    }

    public class EmpleadoAdapter extends FirestoreRecyclerAdapter<Empleado, EmpleadoAdapter.ViewHolder> {
        FirebaseFirestore fireStore= FirebaseFirestore.getInstance();
        private View v;
        public EmpleadoAdapter(@NonNull FirestoreRecyclerOptions<Empleado> options, View view) {
            super(options);
            this.v = view;
        }

        @Override
        protected void onBindViewHolder(@NonNull EmpleadoAdapter.ViewHolder holder, int position, @NonNull Empleado model) {
            DocumentSnapshot empleadoDocumento = getSnapshots().getSnapshot(holder.getAdapterPosition());
            final String id= empleadoDocumento.getId();

            holder.tvDocumento.setText(id);
            holder.tvNombre.setText(model.getNombre());
            holder.tvApellido.setText(model.getApellido());
            holder.tvTipoDocumento.setText(model.getTipoDocumento());
            holder.tvTipoEmpleado.setText(model.getTipoEmpleado());
            holder.tvCargo.setText(model.getCargo());
            holder.tvEspecialidad.setText(model.getEspecialidad());
            holder.tvNumero.setText(model.getNumero());
            holder.tvContraseña.setText(model.getContraseña());
            holder.tvEmail.setText(model.getEmail());
            Glide.with(v.getContext()).load(model.getImagen()).into(holder.iV);
        }

        @NonNull
        @Override
        public EmpleadoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_empleado, parent, false);
            return new EmpleadoAdapter.ViewHolder(view);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvDocumento,tvNombre, tvApellido, tvTipoDocumento, tvTipoEmpleado, tvCargo, tvEspecialidad, tvNumero, tvContraseña, tvEmail;
            ImageView iV;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDocumento= itemView.findViewById(R.id.txvDoc);
                tvNombre= itemView.findViewById(R.id.txvNom1);
                tvApellido= itemView.findViewById(R.id.txvApe);
                tvTipoDocumento= itemView.findViewById(R.id.txvTipoDoccumento);
                tvTipoEmpleado= itemView.findViewById(R.id.txvTipoEmpleado);
                tvCargo= itemView.findViewById(R.id.txvCargo);
                tvEspecialidad= itemView.findViewById(R.id.txvEspecialidad);
                tvNumero= itemView.findViewById(R.id.txvNumero);
                tvEmail=itemView.findViewById(R.id.txvUsuario);
                tvContraseña= itemView.findViewById(R.id.txvContraseña);
                iV = itemView.findViewById(R.id.ImagenEmpleado);
            }
        }
    }

}
