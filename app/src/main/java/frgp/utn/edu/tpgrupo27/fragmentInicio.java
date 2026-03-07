package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import utils.session;


public class fragmentInicio extends Fragment {



    public fragmentInicio() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();

        TextView txtBienvenida =
                requireActivity().findViewById(R.id.txtBienvenida);

        if (txtBienvenida != null) {
            txtBienvenida.setVisibility(View.VISIBLE);
        }
    }

    public static fragmentInicio newInstance(String param1, String param2) {
        fragmentInicio fragment = new fragmentInicio();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        session session = new session(getContext());

        Button btnCerrar = view.findViewById(R.id.btnCerrar);

        btnCerrar.setOnClickListener(v -> {
            session.cerrarSesion();


            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new fragmentLogin())
                    .commit();
        });
    }

}