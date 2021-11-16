package com.titantec.ferreteriapunzon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.ferreteriapunzon.Adaptadores.ProductoAdapter;
import com.titantec.ferreteriapunzon.Modelos.Producto;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    ProductoAdapter adapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Query query = firestore.collection("Producto");
        FirestoreRecyclerOptions<Producto> firestoreRecyclerOptions
                = new FirestoreRecyclerOptions.Builder<Producto>().setQuery(query, Producto.class).build();

        adapter = new ProductoAdapter(firestoreRecyclerOptions,this);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.stopListening();
    }
}