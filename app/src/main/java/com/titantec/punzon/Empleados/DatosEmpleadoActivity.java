package com.titantec.punzon.Empleados;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.orhanobut.dialogplus.DialogPlus;
import com.titantec.punzon.Modelos.Empleado;
import com.titantec.punzon.R;
import com.titantec.punzon.databinding.ActivityDatosEmpleadoBinding;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class DatosEmpleadoActivity extends Fragment {
    RecyclerView rv;
    EmpleadoAdapter adapter;
    FloatingActionButton btnFlotanteAdd;
    FirebaseFirestore firestore= FirebaseFirestore.getInstance();
    ActivityDatosEmpleadoBinding activityDatosEmpleadoBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityDatosEmpleadoBinding = activityDatosEmpleadoBinding.inflate(inflater, container, false);
        View root = activityDatosEmpleadoBinding.getRoot();

        rv = activityDatosEmpleadoBinding.rvEmpleados;
        rv.setLayoutManager(new LinearLayoutManager(activityDatosEmpleadoBinding.rvEmpleados.getContext()));

        btnFlotanteAdd = activityDatosEmpleadoBinding.btnFlotanteAdd;
        btnFlotanteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegistrarEmpleados();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.verEmpleados, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = firestore.collection("Empleados");
        FirestoreRecyclerOptions<Empleado> options =
                new FirestoreRecyclerOptions.Builder<Empleado>().setQuery(query,Empleado.class).build();
        adapter= new EmpleadoAdapter(options,view);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.stopListening();
        activityDatosEmpleadoBinding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.buscar, menu);
        MenuItem item = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txBuscar(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txBuscar(query);
                return false;
            }
        });


    }

    private void txBuscar(String str){
        FirestoreRecyclerOptions<Empleado> firestoreRO = new FirestoreRecyclerOptions.Builder<Empleado>()
                .setQuery(firestore.collection("Empleados").orderBy("documento").startAt(str).endAt(str+"~"), Empleado.class).build();

        adapter = new EmpleadoAdapter(firestoreRO);
        adapter.startListening();
        rv.setAdapter(adapter);
    }

    public class EmpleadoAdapter extends FirestoreRecyclerAdapter<Empleado, EmpleadoAdapter.ViewHolder> {
        FirebaseFirestore fireStore= FirebaseFirestore.getInstance();
        private View v;
        public EmpleadoAdapter(@NonNull FirestoreRecyclerOptions<Empleado> options, View view) {
            super(options);
            this.v = view;
        }

        public EmpleadoAdapter(FirestoreRecyclerOptions<Empleado> firestoreRO) {
            super(firestoreRO);
        }


        @Override
        protected void onBindViewHolder(@NonNull EmpleadoAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Empleado model) {
            DocumentSnapshot empleadoDocumento = getSnapshots().getSnapshot(holder.getAdapterPosition());
            final String id= empleadoDocumento.getId();

            holder.tvDocumento.setText(model.getDocumento());
            holder.tvNombre.setText(model.getNombre());
            holder.tvApellido.setText(model.getApellido());
            holder.tvTipoDocumento.setText(model.getTipoDocumento());
            holder.tvTipoEmpleado.setText(model.getTipoEmpleado());
            holder.tvCargo.setText(model.getCargo());
            holder.tvEspecialidad.setText(model.getEspecialidad());
            holder.tvNumero.setText(model.getNumero());
            holder.tvEmail.setText(model.getEmail());

            //Glide.with(DatosEmpleadoActivity.this).load(model.getUrlImagen()).into(imagenEmpleado);
            Glide.with(holder.imagenEmpleado.getContext()).load(model.getUrlImagen()).into(holder.imagenEmpleado);

            holder.btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DialogPlus dialogPlus = DialogPlus.newDialog(holder.imagenEmpleado.getContext())
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.actualizar_popup_empleados))
                            .setExpanded(true, 1500).create();



                    View view = dialogPlus.getHolderView();

                    EditText nombre = view.findViewById(R.id.txtNom1);
                    EditText apellido = view.findViewById(R.id.txtApe);
                    EditText tipoDoc = view.findViewById(R.id.txtTipoDoccumento);
                    EditText documento = view.findViewById(R.id.txtDoc);
                    EditText numero = view.findViewById(R.id.txtNumero);
                    EditText cargo = view.findViewById(R.id.txtCargo);
                    EditText especialidad = view.findViewById(R.id.txtEspecialidad);
                    EditText tipoEmpleado = view.findViewById(R.id.txtTipoEmpleado);
                    EditText correo = view.findViewById(R.id.txtUsuario);
                    Button btnActualizar = view.findViewById(R.id.btnActualizar);
                    CircleImageView imagenEmpleado = view.findViewById(R.id.imageEmpleado);

                    nombre.setText(model.getNombre());
                    apellido.setText(model.getApellido());
                    tipoDoc.setText(model.getTipoDocumento());
                    documento.setText(model.getDocumento());
                    numero.setText(model.getNumero());
                    cargo.setText(model.getCargo());
                    especialidad.setText(model.getEspecialidad());
                    tipoEmpleado.setText(model.getTipoEmpleado());
                    correo.setText(model.getEmail());

                    Glide.with(imagenEmpleado.getContext()).load(model.getUrlImagen()).into(imagenEmpleado);

                    dialogPlus.show();

                    btnActualizar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String, Object> empleado = new HashMap<>();
                            empleado.put("nombre",nombre.getText().toString());
                            empleado.put("apellido", apellido.getText().toString());
                            empleado.put("tipoDocumento", tipoDoc.getText().toString());
                            empleado.put("tipoEmpleado", tipoEmpleado.getText().toString());
                            empleado.put("cargo", cargo.getText().toString());
                            empleado.put("especialidad", especialidad.getText().toString());
                            empleado.put("email", correo.getText().toString());
                            //empleado.put("contraseña", etpassword.getText().toString());
                            empleado.put("numero", numero.getText().toString());
                            empleado.put("documento", documento.getText().toString());

                            //FirebaseStorage.getInstance().getReference().child("Empleados").child(empleado.get(position).getKey().updateChildren(empleado))
                            firestore.collection("Empleados").document(documento.getText().toString()).update(empleado)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            makeText(getContext(), "Datos actualizados correctamente", LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            makeText(getContext(), "Error al actualizar los datos", LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });


                        }
                    });
                }
            });


            //Glide.with(v.getContext()).load(model.getImagen()).into(holder.iV);
            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.tvDocumento.getContext());
                    builder.setTitle("Eliminar los Datos");
                    builder.setMessage("Está seguro qué desea eliminar los datos?");

                    builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fireStore.collection("Empleados").document(id).delete();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            makeText(getContext(), "Cancelado.", LENGTH_SHORT).show();
                        }
                    });
                    builder.show();
                }
            });
        }

        @NonNull
        @Override
        public EmpleadoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_empleado, parent, false);
            return new EmpleadoAdapter.ViewHolder(view);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements SearchView.OnQueryTextListener{
            TextView tvDocumento,tvNombre, tvApellido, tvTipoDocumento, tvTipoEmpleado, tvCargo, tvEspecialidad, tvNumero, tvEmail;
            Button btnEliminar, btnEditar;
            CircleImageView imagenEmpleado;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvDocumento= itemView.findViewById(R.id.txvDoc);
                tvNombre= itemView.findViewById(R.id.txvNom1);
                tvApellido= itemView.findViewById(R.id.txvApe);
                tvTipoDocumento= itemView.findViewById(R.id.txvTipoDoccumento);
                tvTipoEmpleado= itemView.findViewById(R.id.txvTipoEmpleado);
                tvCargo= itemView.findViewById(R.id.txvCargo);
                tvEspecialidad= itemView.findViewById(R.id.txvEspecialidad);
                tvNumero= itemView.findViewById(R.id.txvNumero);
                tvEmail=itemView.findViewById(R.id.txvUsuario);
                //tvContraseña= itemView.findViewById(R.id.txvContraseña);
                imagenEmpleado = itemView.findViewById(R.id.imageEmpleado);
                btnEliminar = itemView.findViewById(R.id.btnEliminar);
                btnEditar = itemView.findViewById(R.id.btnRegresar);
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }
    }



}
