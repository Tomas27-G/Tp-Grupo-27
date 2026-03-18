package frgp.utn.edu.tpgrupo27.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import frgp.utn.edu.tpgrupo27.R;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;
import frgp.utn.edu.tpgrupo27.negocio.negocioTarea;
import utils.session;

public class tareaAdapter extends ArrayAdapter<Tarea> {

    private Context context;
    private List<Tarea> lista;

    public tareaAdapter(Context context, List<Tarea> lista){
        super(context,0,lista);
        this.context = context;
        this.lista = lista;
    }

    private void mostrarDatePicker(TextInputEditText editText, long fechaInicial){

        Calendar calendar = Calendar.getInstance();

        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
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
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_tarea,parent,false);
        }

        final  Tarea tarea = lista.get(position);

        TextView nombre = convertView.findViewById(R.id.txtNombreTarea);
        TextView fechas = convertView.findViewById(R.id.txtFechas);
        CheckBox check = convertView.findViewById(R.id.checkTarea);
        ImageButton btnEliminar = convertView.findViewById(R.id.btnEliminarTarea);
        ImageButton btnModificar = convertView.findViewById(R.id.btnModificarTarea);

        // Mostrar datos
        nombre.setText(tarea.getNombreTarea());

        SimpleDateFormat formato =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String inicio = formato.format(new Date(tarea.getFechaInicio()));
        String fin = formato.format(new Date(tarea.getFechaFinal()));

        fechas.setText("Inicio: " + inicio + " | Fin: " + fin);

        // CHECKBOX ESTADO
        check.setOnCheckedChangeListener(null);
        check.setChecked(tarea.getCheckeado());

        if(tarea.getCheckeado()){
            nombre.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            nombre.setPaintFlags(nombre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            nombre.setTextColor(context.getResources().getColor(android.R.color.black));
            nombre.setPaintFlags(nombre.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        check.setOnCheckedChangeListener((buttonView, isChecked) -> {

            tarea.setCheckeado(isChecked);
            session sesion = new session(context);
            int idUsuario = sesion.getIdUsuario();

            negocioTarea negocio = new negocioTarea(context, idUsuario);
            boolean resultado = negocio.actualizarCheckeado(tarea.getIdTarea(), isChecked);

            if(resultado){

                if(isChecked){
                    nombre.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
                    nombre.setPaintFlags(nombre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    Toast.makeText(context,"Tarea completada",Toast.LENGTH_SHORT).show();
                }else{
                    nombre.setTextColor(context.getResources().getColor(android.R.color.black));
                    nombre.setPaintFlags(nombre.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }

            }else{
                Toast.makeText(context,"Error al actualizar estado",Toast.LENGTH_SHORT).show();
            }

        });

        // BOTÓN ELIMINAR
        btnEliminar.setOnClickListener(v -> {

            new android.app.AlertDialog.Builder(context)
                    .setTitle("Eliminar tarea")
                    .setMessage("¿Desea eliminar esta tarea?")
                    .setPositiveButton("Aceptar", (dialog, which) -> {

                        session sesion = new session(context);
                        int idUsuario = sesion.getIdUsuario();

                        negocioTarea negocio = new negocioTarea(context, idUsuario);
                        boolean resultado = negocio.eliminarTarea(tarea);

                        if(resultado){
                            lista.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error al eliminar tarea", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        // BOTÓN MODIFICAR
        btnModificar.setOnClickListener(v -> {

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            int padding = (int) (16 * context.getResources().getDisplayMetrics().density);
            layout.setPadding(padding,padding,padding,padding);

            EditText etNombre = new EditText(context);
            etNombre.setText(tarea.getNombreTarea());
            etNombre.setHint("Nombre");
            layout.addView(etNombre);

            TextInputEditText etInicio = new TextInputEditText(context);
            etInicio.setText(formato.format(new Date(tarea.getFechaInicio())));
            etInicio.setHint("Fecha inicio (dd/MM/yyyy)");
            layout.addView(etInicio);

            TextInputEditText etFin = new TextInputEditText(context);
            etFin.setText(formato.format(new Date(tarea.getFechaFinal())));
            etFin.setHint("Fecha final (dd/MM/yyyy)");
            layout.addView(etFin);

            etInicio.setFocusable(false);
            etFin.setFocusable(false);

            etInicio.setOnClickListener(v1 ->
                    mostrarDatePicker(etInicio, tarea.getFechaInicio())
            );

            etFin.setOnClickListener(v1 ->
                    mostrarDatePicker(etFin, tarea.getFechaFinal())
            );

            new android.app.AlertDialog.Builder(context)
                    .setTitle("Modificar tarea")
                    .setView(layout)
                    .setPositiveButton("Aceptar", (dialog, which) -> {

                        String nuevoNombre = etNombre.getText().toString().trim();
                        String nuevaInicio = etInicio.getText().toString().trim();
                        String nuevaFin = etFin.getText().toString().trim();

                        if(nuevoNombre.isEmpty()){
                            Toast.makeText(context, "El nombre que modifique no tiene que estar vacio", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        long fechaInicioLong, fechaFinLong;

                        try {
                            fechaInicioLong = formato.parse(nuevaInicio).getTime();
                            fechaFinLong = formato.parse(nuevaFin).getTime();
                            if(fechaFinLong < fechaInicioLong){
                                Toast.makeText(getContext(),
                                        "La fecha final no puede ser menor que la fecha de inicio",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e){
                            Toast.makeText(context, "Formato de fecha incorrecto", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        tarea.setNombreTarea(nuevoNombre);
                        tarea.setFechaInicio(fechaInicioLong);
                        tarea.setFechaFinal(fechaFinLong);

                        session sesion = new session(context);
                        int idUsuario = sesion.getIdUsuario();

                        negocioTarea negocio = new negocioTarea(context, idUsuario);
                        boolean resultado = negocio.modificarTarea(tarea);

                        if(resultado){
                            notifyDataSetChanged();
                            Toast.makeText(context, "Tarea modificada", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Error al modificar tarea", Toast.LENGTH_SHORT).show();
                        }

                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        return convertView;
    }
}