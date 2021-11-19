package com.titantec.punzon.Adaptadores;

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
import com.titantec.punzon.Modelos.Empleado;
import com.titantec.punzon.R;

public class EmpleadoAdapter extends FirestoreRecyclerAdapter<Empleado,EmpleadoAdapter.ViewHolder> {
    FirebaseFirestore fireStore= FirebaseFirestore.getInstance();

    public EmpleadoAdapter(@NonNull FirestoreRecyclerOptions<Empleado> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EmpleadoAdapter.ViewHolder holder, int position, @NonNull Empleado model) {
        DocumentSnapshot empleadoDocumento= getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id= empleadoDocumento.getId();

        holder.tvDocumento.setText(id);
        holder.tvNombre1.setText(model.getNombre1());
        holder.tvApellido1.setText(model.getApellido1());
        holder.tvTipoDocumento.setText(model.getTipoDocumento());
        holder.tvTipoEmpleado.setText(model.getTipoEmpleado());
        holder.tvCargo.setText(model.getCargo());
        holder.tvEspecialidad.setText(model.getEspecialidad());
        holder.tvNumero.setText(model.getNumero());
        holder.tvContraseña.setText(model.getContraseña());
        holder.tvEmail.setText(model.getEmail());
    }

    @NonNull
    @Override
    public EmpleadoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_empleado, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDocumento,tvNombre1,tvNombre2, tvApellido1, tvApellido2, tvTipoDocumento, tvTipoEmpleado, tvCargo, tvEspecialidad, tvNumero, tvContraseña, tvEmail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDocumento= itemView.findViewById(R.id.txvDoc);
            tvNombre1= itemView.findViewById(R.id.txvNom1);
            tvApellido1= itemView.findViewById(R.id.txvApe);
            tvTipoDocumento= itemView.findViewById(R.id.txvTipoDoccumento);
            tvTipoEmpleado= itemView.findViewById(R.id.txvTipoEmpleado);
            tvCargo= itemView.findViewById(R.id.txvCargo);
            tvEspecialidad= itemView.findViewById(R.id.txvEspecialidad);
            tvNumero= itemView.findViewById(R.id.txvNumero);
            tvEmail=itemView.findViewById(R.id.txvUsuario);
            tvContraseña= itemView.findViewById(R.id.txvContraseña);

        }
    }
}
