package frgp.utn.edu.tpgrupo27;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import frgp.utn.edu.tpgrupo27.adapters.habitoAdapter;
import frgp.utn.edu.tpgrupo27.adapters.tareaAdapter;
import frgp.utn.edu.tpgrupo27.entidades.Habito;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;
import frgp.utn.edu.tpgrupo27.negocio.negocioTarea;

public class fragmentAgenda extends Fragment {

    private ListView lvTareas;
    private ListView lvHabitos;

    public fragmentAgenda() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        lvTareas = view.findViewById(R.id.lvTareas);
        lvHabitos = view.findViewById(R.id.listViewHabitos);

        mostrarTareas();
        mostrarHabitos();

        return view;
    }

    private void mostrarTareas() {

        negocioTarea negocio = new negocioTarea(requireContext());

        List<Tarea> listaTareas = negocio.listarTareas();

        if(listaTareas == null){
            listaTareas = new ArrayList<>();
        }

        tareaAdapter adapter = new tareaAdapter(requireContext(), listaTareas);

        lvTareas.setAdapter(adapter);
    }
    private void mostrarHabitos() {

        negocioHabito negocio = new negocioHabito(requireContext());

        List<Habito> listaHabitos = negocio.obtenerHabitos();

        if (listaHabitos == null) {
            listaHabitos = new ArrayList<>();
        }

        habitoAdapter adapter = new habitoAdapter(requireContext(), listaHabitos);

        lvHabitos.setAdapter(adapter);
    }
}
