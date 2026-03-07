package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import frgp.utn.edu.tpgrupo27.entidades.Habito;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentHabitos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentHabitos extends Fragment {

    private TextInputEditText nombreHabito, descripHabito, fechaInicioHabito;

    private Spinner spinnerFrecuencia;   //HAY que ver como funciona
    private MaterialButton botonGuardarHabito;
    private android.widget.ListView listViewHabitos;

    public fragmentHabitos() {
        // Required empty public constructor
    }


    public static fragmentHabitos newInstance(String param1, String param2) {
        fragmentHabitos fragment = new fragmentHabitos();
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

        View view = inflater.inflate(R.layout.habitos, container, false);

        nombreHabito = view.findViewById(R.id.nombreHabito);
        descripHabito = view.findViewById(R.id.descripHabito);
        fechaInicioHabito = view.findViewById(R.id.fechaInicioHabito);
        spinnerFrecuencia = view.findViewById(R.id.spinnerFrecuencia);
        botonGuardarHabito = view.findViewById(R.id.botonGuardarHabito);
        botonGuardarHabito.setOnClickListener(v -> guardarHabito());

        return view;
    }


    private Long convertirFecha(String fecha) {
        try {
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("dd/MM/yyyy");

            java.util.Date date = sdf.parse(fecha);
            return date.getTime(); // ← Long válido
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
    private void guardarHabito(){
        String nombreH = nombreHabito.getText().toString().trim();
        String descripH = descripHabito.getText().toString().trim();
        Long fechaInicioH = convertirFecha(fechaInicioHabito.getText().toString().trim());



        int spinnerFrecuenciaH = spinnerFrecuencia.getSelectedItemPosition() + 1;


        if(nombreH.isEmpty() || descripH.isEmpty()
                || spinnerFrecuenciaH <= 0  ){

            Toast.makeText(requireContext(),
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Habito habito = new Habito();

        habito.setNombreHabito(nombreH);
        habito.setDescripcionHabito(descripH);
        habito.setFechaInicio(fechaInicioH);
        habito.setFrecuencia(spinnerFrecuenciaH);


        negocioHabito negocio = new negocioHabito(requireContext());
        boolean resultado = negocio.crearHabito(habito);

        if(resultado){

            Toast.makeText(requireContext(),
                    "Habito registrado correctamente",
                    Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(requireContext(),
                    "Error al registrar habito",
                    Toast.LENGTH_SHORT).show();
        }
    }


}