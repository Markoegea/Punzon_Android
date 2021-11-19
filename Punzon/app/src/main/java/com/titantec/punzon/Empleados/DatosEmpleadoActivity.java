package com.titantec.punzon.Empleados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.punzon.Adaptadores.EmpleadoAdapter;
import com.titantec.punzon.Modelos.Empleado;
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

        Query query = firestore.collection("Empleados");
        FirestoreRecyclerOptions<Empleado> firestoreRO =
                new FirestoreRecyclerOptions.Builder<Empleado>().setQuery(query,Empleado.class).build();
        adapter= new EmpleadoAdapter(firestoreRO);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);

        return root;
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

}
