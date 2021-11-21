package com.titantec.punzon.Cuenta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.titantec.punzon.MainActivity;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityInformacionCuentaBinding;
import com.titantec.punzon.databinding.ActivityMainBinding;

public class InformacionCuenta extends Fragment {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    ActivityInformacionCuentaBinding activityInformacionCuentaBinding;
    ActivityMainBinding activityMainBinding;

    TextView tvDocumento,tvNombre1, tvApellido1, tvTipoDocumento, tvTipoEmpleado, tvCargo, tvEspecialidad, tvNumero, tvContraseña, tvEmail;
    Button btnSalir;
    public InformacionCuenta() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityInformacionCuentaBinding = activityInformacionCuentaBinding.inflate(inflater, container, false);
        View root = activityInformacionCuentaBinding.getRoot();
        tvDocumento= activityInformacionCuentaBinding.txvDoc;
        tvNombre1= activityInformacionCuentaBinding.txvNom1;
        tvApellido1= activityInformacionCuentaBinding.txvApe;
        tvTipoDocumento= activityInformacionCuentaBinding.txvTipoDoccumento;
        tvTipoEmpleado= activityInformacionCuentaBinding.txvTipoEmpleado;
        tvCargo= activityInformacionCuentaBinding.txvCargo;
        tvEspecialidad= activityInformacionCuentaBinding.txvEspecialidad;
        tvNumero= activityInformacionCuentaBinding.txvNumero;
        tvEmail= activityInformacionCuentaBinding.txvUsuario;
        tvContraseña= activityInformacionCuentaBinding.txvPassword;
        btnSalir=activityInformacionCuentaBinding.BtnSalir;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(auth.getCurrentUser()!=null){
            FirebaseUser user = auth.getCurrentUser();
            firestore.collection("Empleados").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        tvDocumento.setText(documentSnapshot.getString("numero_de_documento"));
                        tvNombre1.setText(documentSnapshot.getString("nombre"));
                        tvApellido1.setText(documentSnapshot.getString("apellido"));
                        tvTipoDocumento.setText(documentSnapshot.getString("tipoDocumento"));
                        tvTipoEmpleado.setText(documentSnapshot.getString("tipoEmpleado"));
                        tvCargo.setText(documentSnapshot.getString("cargo"));
                        tvEspecialidad.setText(documentSnapshot.getString("especialidad"));
                        tvNumero.setText(documentSnapshot.getString("numero"));
                        tvContraseña.setText(documentSnapshot.getString("contraseña"));
                        tvEmail.setText(documentSnapshot.getString("email"));
                    }
                }
            });
        }
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent inte = new Intent(v.getContext(), MainActivity.class);
                startActivity(inte);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityInformacionCuentaBinding = null;
    }
}
