package com.titantec.punzon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.titantec.punzon.Modelos.Empleado;
import com.titantec.punzon.databinding.ActivityMainBinding;
import com.titantec.punzon.databinding.NavHeaderMainBinding;


public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;
    ActivityMainBinding activityMainBinding;
    NavHeaderMainBinding navHeaderMainBinding;
    TextView txtNombre,txtCorreo;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        setSupportActionBar(activityMainBinding.appBarMain.toolbar);
        DrawerLayout drawer = activityMainBinding.drawerLayout;
        NavigationView navigationView = activityMainBinding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.Main_Page,R.id.Registro_Clientes,R.id.Ver_Empleados,R.id.Ver_Inventario,R.id.Registro_Empleados,
                R.id.Mi_cuenta,R.id.Mi_cuenta_Ingreso,R.id.Nada)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headview = navigationView.getHeaderView(0);
        txtNombre = headview.findViewById(R.id.textView2);
        txtCorreo = headview.findViewById(R.id.txtCorreo);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null){
            FirebaseUser user = auth.getCurrentUser();
            firestore.collection("Empleados").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        String nombre = documentSnapshot.getString("nombre");
                        txtNombre.setText("Hola, "+nombre);
                        txtCorreo.setText(user.getEmail());
                    }
                }
            });
        }
        txtNombre.setText("No te conozco, por favor registrate");
        txtCorreo.setText("");
    }
}