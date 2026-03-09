package frgp.utn.edu.tpgrupo27.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import frgp.utn.edu.tpgrupo27.R;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;

public class tareaAdapter extends ArrayAdapter<Tarea> {

    private Context context;
    private List<Tarea> listaTareas;

    public tareaAdapter(Context context, List<Tarea> listaTareas) {
        super(context, 0, listaTareas);
        this.context = context;
        this.listaTareas = listaTareas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_tarea, parent, false);
        }

        Tarea tarea = listaTareas.get(position);

        TextView txtNombre = convertView.findViewById(R.id.txtNombreTarea);
        CheckBox check = convertView.findViewById(R.id.checkTarea);

        txtNombre.setText(tarea.getNombreTarea());

        return convertView;
    }
}