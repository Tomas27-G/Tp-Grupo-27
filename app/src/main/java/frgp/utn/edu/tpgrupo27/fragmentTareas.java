package frgp.utn.edu.tpgrupo27;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import frgp.utn.edu.tpgrupo27.database.DAO.DaoTarea;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;
import frgp.utn.edu.tpgrupo27.negocio.negocioTarea;
import utils.session;

public class fragmentTareas extends Fragment {

        private TextInputEditText etNombreTarea;
        private TextInputEditText etDescripcionTarea;
        private TextInputEditText etFechaInicio;
        private TextInputEditText etFechaFinal;
        private Spinner spinnerPrioridad;
        private MaterialButton btnGuardar;

        public fragmentTareas() {

        }

        @Override
        public void onResume() {
            super.onResume();

            TextView txtBienvenida =
                    requireActivity().findViewById(R.id.txtBienvenida);

            if (txtBienvenida != null) {
                txtBienvenida.setVisibility(View.GONE);
            }
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



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.tareas, container, false);

            etNombreTarea = view.findViewById(R.id.etNombreTarea);
            etDescripcionTarea = view.findViewById(R.id.etDescripcionTarea);
            etFechaInicio = view.findViewById(R.id.etFechaInicio);
            etFechaFinal = view.findViewById(R.id.etFechaFinal);
            spinnerPrioridad = view.findViewById(R.id.spinnerPrioridad);
            etFechaInicio.setOnClickListener(v -> mostrarDatePicker(etFechaInicio));
            etFechaFinal.setOnClickListener(v -> mostrarDatePicker(etFechaFinal));
            cargarSpinnerPrioridad();
            btnGuardar = view.findViewById(R.id.btnGuardarTarea);

            btnGuardar.setOnClickListener(v -> guardarTarea());

            return view;
        }
        private void cargarSpinnerPrioridad() {
            String[] prioridades = {"Baja", "Media", "Alta"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    prioridades
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPrioridad.setAdapter(adapter);
        }

        private boolean contieneNumeros(String texto){
            return texto.matches(".*\\d.*");
        }

        private void guardarTarea() {

            String nombre = etNombreTarea.getText().toString();
            String descripcion = etDescripcionTarea.getText().toString();
            String fechaInicioStr = etFechaInicio.getText().toString();
            String fechaFinalStr = etFechaFinal.getText().toString();



            if(contieneNumeros(nombre) || contieneNumeros(descripcion)){
                Toast.makeText(getContext(),
                        "El nombre y la descripción no pueden tener números",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if(nombre.isEmpty() || descripcion.isEmpty() || fechaInicioStr.isEmpty() || fechaFinalStr.isEmpty()){
                Toast.makeText(getContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int prioridad = spinnerPrioridad.getSelectedItemPosition() + 1;

            long fechaInicio = convertirFecha(fechaInicioStr);
            long fechaFinal = convertirFecha(fechaFinalStr);

            if(fechaInicio == 0 || fechaFinal == 0){
                Toast.makeText(getContext(), "Formato de fecha incorrecto (dd/MM/yyyy)", Toast.LENGTH_SHORT).show();
                return;
            }

            if(fechaFinal < fechaInicio){
                Toast.makeText(getContext(),
                        "La fecha final no puede ser menor que la fecha de inicio",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Tarea tarea = new Tarea();
            tarea.setNombreTarea(nombre);
            tarea.setDescripcionTarea(descripcion);
            tarea.setFechaInicio(fechaInicio);
            tarea.setFechaFinal(fechaFinal);
            tarea.setPrioridad(prioridad);

            session sesion = new session(requireContext());
            int idUs = sesion.getIdUsuario();

            negocioTarea nTarea = new negocioTarea(requireContext(), idUs);
            boolean resultado = nTarea.crearTarea(tarea);

            if (resultado) {

                Toast.makeText(getContext(), "Tarea guardada correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(getContext(), "Error al guardar la tarea", Toast.LENGTH_SHORT).show();
            }
        }
        private long convertirFecha(String fecha) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date date = formato.parse(fecha);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }

        private void limpiarCampos() {
            etNombreTarea.setText("");
            etDescripcionTarea.setText("");
            etFechaInicio.setText("");
            etFechaFinal.setText("");
            spinnerPrioridad.setSelection(0);
        }
    }





