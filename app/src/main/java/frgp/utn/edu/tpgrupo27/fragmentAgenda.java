package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import frgp.utn.edu.tpgrupo27.entidades.Habito;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;

public class fragmentAgenda extends Fragment {

    private ListView lvTareas;

    private ListView listViewHabitos;
    private DaoTarea daoTarea;

    public fragmentAgenda() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        lvTareas = view.findViewById(R.id.lvTareas);
        daoTarea = new DaoTarea(getContext());

        mostrarTareas();

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listViewHabitos = view.findViewById(R.id.listViewHabitos);

        cargarListaHabitos();




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

        lvTareas.setAdapter(adapter);
    }

    private String formatearFecha(Long timestamp) {

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("dd/MM/yyyy",
                        java.util.Locale.getDefault());

        return sdf.format(new java.util.Date(timestamp));
    }
    private void cargarListaHabitos(){

        negocioHabito negocio = new negocioHabito(requireContext());
        java.util.List<Habito> listaHabitos = negocio.obtenerHabitos();

        java.util.ArrayList<String> listaTexto = new java.util.ArrayList<>();

        for(Habito h : listaHabitos){

            String texto = h.getNombreHabito() + " - "
                    + formatearFecha(h.getFechaInicio()) + " - "
                    + h.getFrecuencia();


            listaTexto.add(texto);
        }

        android.widget.ArrayAdapter<String> adapter =
                new android.widget.ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        listaTexto
                );

        listViewHabitos.setAdapter(adapter);
    }
}