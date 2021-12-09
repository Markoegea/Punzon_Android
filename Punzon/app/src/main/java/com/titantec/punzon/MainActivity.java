package com.titantec.punzon;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.juanlabrador.badgecounter.BadgeCounter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.titantec.punzon.Modelos.Productos;
import com.titantec.punzon.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AppBarConfiguration appBarConfiguration;
    ActivityMainBinding activityMainBinding;
    TextView txtNombre, txtCorreo;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Menu menu;
    private ArrayList<Productos> productosShoppingCarList = new ArrayList<>();

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
                R.id.Main_Page, R.id.Registro_Clientes, R.id.Ver_Empleados, R.id.Ver_Inventario, R.id.Registro_Empleados,
                R.id.Mi_cuenta, R.id.Mi_cuenta_Ingreso, R.id.Carrito, R.id.Registro_Productos, R.id.Nada)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.carrito, menu);
        actualizarNotificacion(new ArrayList<>());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shop:
                final Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("product_key", productosShoppingCarList);

                final NavController abrir = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                abrir.navigate(R.id.Venta_Carritos, bundle);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        activityMainBinding.appBarMain.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_shop:
                        NavController abrir = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                        abrir.popBackStack();
                        abrir.navigate(R.id.Carrito);
                    default:
                        return false;
                }
            }
        });
        if (auth.getCurrentUser() != null) {
            FirebaseUser user = auth.getCurrentUser();
            firestore.collection("Empleado").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String nombre = documentSnapshot.getString("nombre");
                        txtNombre.setText("Hola, " + nombre);
                        txtCorreo.setText(user.getEmail());
                    }
                }
            });
        }
        txtNombre.setText("No te conozco, por favor registrate");
        txtCorreo.setText("");
    }

    public void actualizarNotificacion(final ArrayList<Productos> listaProductosCarrito) {
        this.productosShoppingCarList = listaProductosCarrito;

        BadgeCounter.update(this,
                menu.findItem(R.id.action_shop),
                R.drawable.shopping_car,
                BadgeCounter.BadgeColor.BLACK,
                "+" + listaProductosCarrito.size());
    }

    public void showMenuItem(final boolean isvisible){
        menu.findItem(R.id.action_shop).setVisible(isvisible);
    }
}