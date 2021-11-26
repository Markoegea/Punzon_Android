package com.titantec.punzon.Inventario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.titantec.punzon.databinding.ActivityCarroInventarioBinding;


public class CarroInventario extends Fragment {
    ActivityCarroInventarioBinding activityCarroInventarioBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityCarroInventarioBinding = activityCarroInventarioBinding.inflate(inflater, container, false);
        View root = activityCarroInventarioBinding.getRoot();;
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityCarroInventarioBinding = null;
    }
}
