package com.titantec.punzon.Inventario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.FragmentVerInventarioBinding;

public class VerInventario extends Fragment {

    FragmentVerInventarioBinding fragmentVerInventarioBinding;
    TextView txvnombre,txvid,txvprecio,txvdescripcion,txvcantidad,txvmarca;
    ImageView imgproducto;
    String nombre;
    long id;
    double precio;
    String descripcion;
    int imagen;
    int cantidad;
    String marca;

    public VerInventario() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(fragmentVerInventarioBinding == null) {
            fragmentVerInventarioBinding = fragmentVerInventarioBinding.inflate(inflater, container, false);
        }

        View root = fragmentVerInventarioBinding.getRoot();
        txvnombre=fragmentVerInventarioBinding.TvNombre;
        txvprecio=fragmentVerInventarioBinding.TvPrecio;
        txvdescripcion=fragmentVerInventarioBinding.TvDescripcion;
        txvcantidad=fragmentVerInventarioBinding.TvCantidad;
        txvmarca=fragmentVerInventarioBinding.TvMarca;
        imgproducto=fragmentVerInventarioBinding.ImgProducto;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("param1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                txvnombre.setText(result.getString("Nombre"));
                txvprecio.setText(result.getString("Precio"));
                txvdescripcion.setText(result.getString("Descripcion"));
                txvcantidad.setText(result.getString("Cantidad"));
                txvmarca.setText(result.getString("Marca"));
                Glide.with(view.getContext()).load(result.getString("Imagen")).into(imgproducto);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentVerInventarioBinding = null;
    }
}