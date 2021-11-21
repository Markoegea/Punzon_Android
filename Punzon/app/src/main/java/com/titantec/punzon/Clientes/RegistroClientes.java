package com.titantec.punzon.Clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityRegistroClientesBinding;


public class RegistroClientes extends Fragment {

    EditText etname, etlastname, etid, etusarname, etpassword;
    Spinner spid;
    Button btnRegCli;
    ActivityRegistroClientesBinding activityRegistroClientesBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityRegistroClientesBinding = activityRegistroClientesBinding.inflate(inflater, container, false);
        View root = activityRegistroClientesBinding.getRoot();

        etname =  activityRegistroClientesBinding.nomCliente;
        etlastname =  activityRegistroClientesBinding.apeCliente;
        etid =  activityRegistroClientesBinding.idCliente;
        etusarname =  activityRegistroClientesBinding.userCliente;
        etpassword = activityRegistroClientesBinding.passwordCliente;
        spid = activityRegistroClientesBinding.spidCliente;
        btnRegCli = activityRegistroClientesBinding.btnRegCliente;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistroClientesBinding = null;
    }
}
