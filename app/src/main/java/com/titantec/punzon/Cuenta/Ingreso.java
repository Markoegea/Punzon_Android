package com.titantec.punzon.Cuenta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityIngresoBinding;
import com.titantec.punzon.databinding.ActivityMainBinding;

public class Ingreso extends Fragment {
    EditText edEmail, edPassword;
    Button btnIngresar;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    ActivityIngresoBinding activityIngresoBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityIngresoBinding = activityIngresoBinding.inflate(inflater, container, false);
        View root = activityIngresoBinding.getRoot();

        edEmail= activityIngresoBinding.EdEmail;
        edPassword= activityIngresoBinding.EdPassword;
        btnIngresar=activityIngresoBinding.BtnIngresar;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar(edEmail.getText().toString(),edPassword.getText().toString(),v);
            }
        });
        if(auth.getCurrentUser()!=null){
            NavController abrir = Navigation.findNavController(view);
            abrir.navigate(R.id.Mi_cuenta_Ingreso);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityIngresoBinding = null;
    }

    private void ingresar(String email, String password, View v){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    entrar(v);
                } else {
                    Toast.makeText(v.getContext(),"Fallo en el ingreso, " +
                            "Revisalo y Intentalo otra vez",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void entrar(View view){
        if(auth.getCurrentUser()!=null){
            Intent inte = new Intent(view.getContext(), MainActivity.class);
            startActivity(inte);
        }
    }
}
