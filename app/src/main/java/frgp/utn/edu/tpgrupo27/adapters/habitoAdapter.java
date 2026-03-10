package frgp.utn.edu.tpgrupo27.adapters;

import android.app.AlertDialog;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

            EditText etFechaInicio = new EditText(context);
            etFechaInicio.setText(formato.format(new Date(habito.getFechaInicio())));
            etFechaInicio.setHint("Fecha inicio (dd/MM/yyyy)");
            layout.addView(etFechaInicio);

            EditText etFrecuencia = new EditText(context);
            etFrecuencia.setInputType(InputType.TYPE_CLASS_NUMBER);
            etFrecuencia.setText(String.valueOf(habito.getFrecuencia()));
            etFrecuencia.setHint("Frecuencia (1:Diaria, 2:Semanal, 3:Mensual)");
            layout.addView(etFrecuencia);

            new AlertDialog.Builder(context)
                    .setTitle("Modificar hábito")
                    .setView(layout)
                    .setPositiveButton("Aceptar", (dialog, which) -> {

                        String nombreOriginal = habito.getNombreHabito();

                        String nuevoNombre = etNombre.getText().toString().trim();
                        String nuevaDescripcion = etDescripcion.getText().toString().trim();
                        String nuevaFechaInicioStr = etFechaInicio.getText().toString().trim();
                        String nuevaFrecuenciaStr = etFrecuencia.getText().toString().trim();

                        long nuevaFechaInicio;
                        int nuevaFrecuencia;

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

        TextView txtFechaInicio;

        CheckBox checkRealizado;

        ImageButton btnEliminar;

        ImageButton btnModificar;

    }
}