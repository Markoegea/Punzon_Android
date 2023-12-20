package com.titantec.punzon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.titantec.punzon.databinding.ActivityNadaBinding;

public class Nada extends Fragment {

    ActivityNadaBinding activityNadaBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityNadaBinding = activityNadaBinding.inflate(inflater, container, false);
        View root = activityNadaBinding.getRoot();
        Button button = activityNadaBinding.Vista;
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activityNadaBinding = null;
    }
}
