package frgp.utn.edu.tpgrupo27;

import android.app.DatePickerDialog;
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

import java.util.Calendar;
import java.util.Locale;

import frgp.utn.edu.tpgrupo27.entidades.Habito;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;

import utils.session;

public class fragmentHabitos extends Fragment {

    private TextInputEditText nombreHabito, descripHabito, fechaInicioHabito;

    private Spinner spinnerFrecuencia;   //HAY que ver como funciona
    private MaterialButton botonGuardarHabito;
    private android.widget.ListView listViewHabitos;

    public fragmentHabitos() {
        // Required empty public constructor
    }

    private void mostrarDatePicker(TextInputEditText editText){

        Calendar calendar = Calendar.getInstance();

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {

                    month = month + 1;

                    String fecha = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d", dayOfMonth, month, year);

                    editText.setText(fecha);
                },
                anio, mes, dia
        );

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
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

        fechaInicioHabito = view.findViewById(R.id.fechaInicioHabito);

        fechaInicioHabito.setOnClickListener(v -> mostrarDatePicker(fechaInicioHabito));

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
                || spinnerFrecuenciaH <= 0){

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

        session sesion = new session(requireContext());
        int idUs = sesion.getIdUsuario();

        negocioHabito negocio = new negocioHabito(requireContext(), idUs);
        boolean resultado = negocio.crearHabito(habito);

        if(resultado){

            NotificacionHelper.programarNotificacion(
                    requireContext(),
                    fechaInicioH,
                    habito.getIdHabito()
            );

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