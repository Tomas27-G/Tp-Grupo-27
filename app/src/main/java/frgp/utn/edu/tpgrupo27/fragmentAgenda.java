package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import frgp.utn.edu.tpgrupo27.adapters.tareaAdapter;
import frgp.utn.edu.tpgrupo27.entidades.Habito;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;
import frgp.utn.edu.tpgrupo27.negocio.negocioTarea;

public class fragmentAgenda extends Fragment {

    private ListView lvTareas;
    private ListView listViewHabitos;

    public fragmentAgenda() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        lvTareas = view.findViewById(R.id.lvTareas);
        listViewHabitos = view.findViewById(R.id.listViewHabitos);

        mostrarTareas();
        cargarListaHabitos();

        return view;
    }

    private void mostrarTareas() {

        negocioTarea negocio = new negocioTarea(requireContext());

        List<Tarea> listaTareas = negocio.listarTareas();

        tareaAdapter adapter = new tareaAdapter(
                requireContext(),
                listaTareas
        );

        lvTareas.setAdapter(adapter);
    }

    private String formatearFecha(Long timestamp) {

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat(
                        "dd/MM/yyyy",
                        java.util.Locale.getDefault()
                );

        return sdf.format(new java.util.Date(timestamp));
    }

    private void cargarListaHabitos(){

        negocioHabito negocio = new negocioHabito(requireContext());

        List<Habito> listaHabitos = negocio.obtenerHabitos();

        java.util.ArrayList<String> listaTexto = new java.util.ArrayList<>();

        for(Habito h : listaHabitos){

            String texto =
                    h.getNombreHabito() + " - "
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