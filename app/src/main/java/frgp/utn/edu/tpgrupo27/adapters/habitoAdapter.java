package frgp.utn.edu.tpgrupo27.adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Paint;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import utils.session;

import frgp.utn.edu.tpgrupo27.R;
import frgp.utn.edu.tpgrupo27.entidades.Habito;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;

public class habitoAdapter extends BaseAdapter {

    private Context context;
    private List<Habito> listaHabitos;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    public habitoAdapter(Context context, List<Habito> listaHabitos) {
        this.context = context;
        this.listaHabitos = listaHabitos;
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
    public int getCount() {
        return listaHabitos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaHabitos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private String frecuenciaTexto(int frecuencia) {
        switch (frecuencia) {
            case 1: return "Diaria";
            case 2: return "Semanal";
            case 3: return "Mensual";
            default: return "Desconocida";
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_habito, parent, false);

            holder = new ViewHolder();

            holder.txtNombre = convertView.findViewById(R.id.tvNombreHabito);
            holder.txtDescripcion = convertView.findViewById(R.id.tvDescripcionHabito);
            holder.txtFechaInicio = convertView.findViewById(R.id.tvFechaInicioHabito);
            holder.checkRealizado = convertView.findViewById(R.id.checkRealizado);
            holder.btnEliminar = convertView.findViewById(R.id.btnEliminar);
            holder.btnModificar = convertView.findViewById(R.id.btnModificar);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Habito habito = listaHabitos.get(position);

        holder.txtNombre.setText(habito.getNombreHabito());
        holder.txtDescripcion.setText(habito.getDescripcionHabito());
        holder.txtFechaInicio.setText(
                "Inicio: " + formato.format(new Date(habito.getFechaInicio()))
                        + " | Frecuencia: " + frecuenciaTexto(habito.getFrecuencia())
        );

        // =========================
// CHECKBOX
// =========================

// Evita problemas cuando ListView recicla vistas
        holder.checkRealizado.setOnCheckedChangeListener(null);

// Estado desde la base de datos
        holder.checkRealizado.setChecked(habito.getCheckeado());

        if(habito.getCheckeado()){

            holder.checkRealizado.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

            holder.txtNombre.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

            holder.txtNombre.setPaintFlags(
                    holder.txtNombre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
            );

        }else{

            holder.checkRealizado.setTextColor(context.getResources().getColor(android.R.color.black));

            holder.txtNombre.setTextColor(context.getResources().getColor(android.R.color.black));

            holder.txtNombre.setPaintFlags(
                    holder.txtNombre.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)
            );
        }

        holder.checkRealizado.setOnCheckedChangeListener((buttonView, isChecked) -> {

            habito.setCheckeado(isChecked);

            session sesion = new session(context);
            int idUsuario = sesion.getIdUsuario();

            negocioHabito negocio = new negocioHabito(context, idUsuario);

            boolean actualizado = negocio.actualizarCheckeado(
                    habito.getIdHabito(),
                    isChecked
            );

            if(actualizado){

                if(isChecked){

                    holder.checkRealizado.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

                    holder.txtNombre.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));

                    holder.txtNombre.setPaintFlags(
                            holder.txtNombre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
                    );

                    Toast.makeText(context, "Hábito completado", Toast.LENGTH_SHORT).show();

                }else{

                    holder.checkRealizado.setTextColor(context.getResources().getColor(android.R.color.black));

                    holder.txtNombre.setTextColor(context.getResources().getColor(android.R.color.black));

                    holder.txtNombre.setPaintFlags(
                            holder.txtNombre.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG)
                    );

                }

            }else{

                Toast.makeText(context, "Error al actualizar estado", Toast.LENGTH_SHORT).show();

            }

        });

        // =========================
        // ELIMINAR HABITO
        // =========================

        holder.btnEliminar.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Eliminar hábito")
                    .setMessage("¿Desea eliminar este hábito?")
                    .setPositiveButton("Aceptar", (dialog, which) -> {

                        session sesion = new session(context);
                        int idUsuario = sesion.getIdUsuario();

                        negocioHabito negocio = new negocioHabito(context, idUsuario);

                        if (negocio.borrarHabito(habito)) {

                            listaHabitos.remove(position);

                            notifyDataSetChanged();

                            Toast.makeText(context, "Hábito eliminado", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();

                        }

                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

        });

        // =========================
        // MODIFICAR HABITO
        // =========================

        holder.btnModificar.setOnClickListener(v -> {

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            int padding = (int) (16 * context.getResources().getDisplayMetrics().density);

            layout.setPadding(padding, padding, padding, padding);

            EditText etNombre = new EditText(context);
            etNombre.setText(habito.getNombreHabito());
            etNombre.setHint("Nombre");
            layout.addView(etNombre);

            EditText etDescripcion = new EditText(context);
            etDescripcion.setText(habito.getDescripcionHabito());
            etDescripcion.setHint("Descripción");
            layout.addView(etDescripcion);

            TextInputEditText etFechaInicio = new TextInputEditText(context);
            etFechaInicio.setText(formato.format(new Date(habito.getFechaInicio())));
            etFechaInicio.setHint("Fecha inicio (dd/MM/yyyy)");
            layout.addView(etFechaInicio);

            EditText etFrecuencia = new EditText(context);
            etFrecuencia.setInputType(InputType.TYPE_CLASS_NUMBER);
            etFrecuencia.setText(String.valueOf(habito.getFrecuencia()));
            etFrecuencia.setHint("Frecuencia (1,2,3,4,5,6,7)");
            layout.addView(etFrecuencia);

            etFechaInicio.setOnClickListener(v1 ->
                    mostrarDatePicker(etFechaInicio, habito.getFechaInicio())
            );;

            new AlertDialog.Builder(context)
                    .setTitle("Modificar hábito")
                    .setView(layout)
                    .setPositiveButton("Aceptar", (dialog, which) -> {

                        String nombreOriginal = habito.getNombreHabito();

                        String nuevoNombre = etNombre.getText().toString().trim();
                        String nuevaDescripcion = etDescripcion.getText().toString().trim();
                        String nuevaFechaInicioStr = etFechaInicio.getText().toString().trim();
                        String nuevaFrecuenciaStr = etFrecuencia.getText().toString().trim();

                        if(nuevoNombre.isEmpty()||nuevaDescripcion.isEmpty() ){
                            Toast.makeText(context, "El nombre o la descripcion que modifique no tiene que estar vacio", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int nuevaFrecuencia;

                        try {
                            nuevaFrecuencia = Integer.parseInt(nuevaFrecuenciaStr);
                        } catch (NumberFormatException e) {
                            Toast.makeText(context, "La frecuencia debe ser un número válido", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (nuevaFrecuencia < 1 || nuevaFrecuencia > 7) {
                            Toast.makeText(context, "La frecuencia debe ser del 1 al 7", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(contieneNumeros(nuevoNombre) || contieneNumeros(nuevaDescripcion)){
                            Toast.makeText(context,
                                    "El nombre y la descripción no pueden tener números",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }


                        long nuevaFechaInicio;



                        try {

                            nuevaFechaInicio = formato.parse(nuevaFechaInicioStr).getTime();

                            nuevaFrecuencia = Integer.parseInt(nuevaFrecuenciaStr);

                        } catch (Exception e) {

                            Toast.makeText(context, "Datos inválidos", Toast.LENGTH_SHORT).show();

                            return;

                        }

                        Habito habitoModificado = new Habito();

                        habitoModificado.setNombreHabito(nuevoNombre);
                        habitoModificado.setDescripcionHabito(nuevaDescripcion);
                        habitoModificado.setFechaInicio(nuevaFechaInicio);
                        habitoModificado.setFrecuencia(nuevaFrecuencia);

                        session sesion = new session(context);
                        int idUsuario = sesion.getIdUsuario();

                        negocioHabito negocio = new negocioHabito(context, idUsuario);

                        boolean modificado = negocio.modificarHabito(nombreOriginal, habitoModificado);

                        if(modificado){

                            habito.setNombreHabito(nuevoNombre);
                            habito.setDescripcionHabito(nuevaDescripcion);
                            habito.setFechaInicio(nuevaFechaInicio);
                            habito.setFrecuencia(nuevaFrecuencia);

                            notifyDataSetChanged();

                            Toast.makeText(context, "Hábito modificado", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(context, "Error al modificar hábito", Toast.LENGTH_SHORT).show();

                        }

                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

        });

        return convertView;
    }

    private static class ViewHolder {

        TextView txtNombre;

        TextView txtDescripcion;
        TextView txtFechaInicio;

        CheckBox checkRealizado;

        ImageButton btnEliminar;

        ImageButton btnModificar;

    }

    private boolean contieneNumeros(String texto){
        return texto.matches(".*\\d.*");
    }
}