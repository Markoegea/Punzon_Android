package com.titantec.punzon.Clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.titantec.punzon.databinding.ActivityRegistroClientesBinding;


public class RegistroClientes extends Fragment {

    ActivityRegistroClientesBinding activityRegistroClientesBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        activityRegistroClientesBinding = activityRegistroClientesBinding.inflate(inflater, container, false);
        View root = activityRegistroClientesBinding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityRegistroClientesBinding = null;
    }
}
