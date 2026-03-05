package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import frgp.utn.edu.tpgrupo27.database.DAO.DaoTarea;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;

public class fragmentAgenda extends Fragment {

    private ListView lvAgenda;
    private DaoTarea daoTarea;

    public fragmentAgenda() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        lvAgenda = view.findViewById(R.id.lvAgenda);
        daoTarea = new DaoTarea(getContext());

        mostrarTareas();

        return view;
    }

    private void mostrarTareas() {
        List<Tarea> lista = daoTarea.listarTareas();
        List<String> listaStrings = new ArrayList<>();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (Tarea t : lista) {
            String prioridad = "";
            switch (t.getPrioridad()) {
                case 1: prioridad = "Baja"; break;
                case 2: prioridad = "Media"; break;
                case 3: prioridad = "Alta"; break;
            }
            String item = t.getNombreTarea() + "\n"
                    + t.getDescripcionTarea() + "\n"
                    + "Inicio: " + formato.format(new Date(t.getFechaInicio()))
                    + " | Fin: " + formato.format(new Date(t.getFechaFinal())) + "\n"
                    + "Prioridad: " + prioridad;
            listaStrings.add(item);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                listaStrings
        );

        lvAgenda.setAdapter(adapter);
    }
}