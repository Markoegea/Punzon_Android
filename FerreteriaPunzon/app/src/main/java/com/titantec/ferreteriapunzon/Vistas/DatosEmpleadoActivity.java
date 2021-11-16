package com.titantec.ferreteriapunzon.Vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.ferreteriapunzon.Adaptadores.EmpleadoAdapter;
import com.titantec.ferreteriapunzon.Modelos.Empleado;
import com.titantec.ferreteriapunzon.R;

public class DatosEmpleadoActivity extends AppCompatActivity {
    RecyclerView rv;
    EmpleadoAdapter adapter;
    FirebaseFirestore firestore= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_empleado);


        rv= findViewById(R.id.rvEmpleados);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Query query = firestore.collection("Empleados");
        FirestoreRecyclerOptions<Empleado> firestoreRO = new FirestoreRecyclerOptions.Builder<Empleado>().setQuery(query,Empleado.class).build();
        adapter= new EmpleadoAdapter(firestoreRO);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}