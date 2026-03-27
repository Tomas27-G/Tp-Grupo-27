package frgp.utn.edu.tpgrupo27;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import frgp.utn.edu.tpgrupo27.adapters.habitoAdapter;
import frgp.utn.edu.tpgrupo27.adapters.tareaAdapter;
import frgp.utn.edu.tpgrupo27.entidades.Habito;
import frgp.utn.edu.tpgrupo27.entidades.Tarea;
import frgp.utn.edu.tpgrupo27.negocio.negocioHabito;
import frgp.utn.edu.tpgrupo27.negocio.negocioTarea;
import utils.session;

public class fragmentAgenda extends Fragment {
    private TextView txtTareas;
    private TextView txtHabitos;
    private ListView lvTareas;
    private ListView lvHabitos;
    private Spinner spFiltro;

    public fragmentAgenda() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        lvTareas = view.findViewById(R.id.lvTareas);
        lvHabitos = view.findViewById(R.id.listViewHabitos);

        txtHabitos= view.findViewById(R.id.txtHabitos);
        txtTareas= view.findViewById(R.id.txtTareas);


        mostrarTareas();
        mostrarHabitos();


        spFiltro = view.findViewById(R.id.spFiltro);

        String[] opciones = {
                "Todo",
                "Tareas (todas)",
                "Tareas hechas",
                "Tareas pendientes",
                "Hábitos (todos)",
                "Hábitos hechos",
                "Hábitos no hechos"
        };

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                opciones
        );

        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFiltro.setAdapter(adapterSpinner);

        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        lvTareas.setVisibility(View.VISIBLE);
                        lvHabitos.setVisibility(View.VISIBLE);
                        mostrarTareas();
                        mostrarHabitos();
                        break;

                    case 1:
                        lvTareas.setVisibility(View.VISIBLE);
                        lvHabitos.setVisibility(View.GONE);
                        mostrarTareas(); //Todas las tareas
                        break;

                    case 2:
                        lvTareas.setVisibility(View.VISIBLE);
                        lvHabitos.setVisibility(View.GONE);
                        mostrarTareasFiltradas(true);// Tareas hechas
                        break;

                    case 3:
                        lvTareas.setVisibility(View.VISIBLE);
                        lvHabitos.setVisibility(View.GONE);
                        mostrarTareasFiltradas(false);// Tareas pendientes
                        break;

                    case 4:
                        lvTareas.setVisibility(View.GONE);
                        lvHabitos.setVisibility(View.VISIBLE);
                        mostrarHabitos(); //Todos los habitos
                        break;

                    case 5:
                        lvHabitos.setVisibility(View.VISIBLE);
                        lvTareas.setVisibility(View.GONE);
                        mostrarHabitosFiltrados(true); // mostrar habitos hechos
                        break;

                    case 6:
                        lvHabitos.setVisibility(View.VISIBLE);
                        lvTareas.setVisibility(View.GONE);
                        mostrarHabitosFiltrados(false); // mostrar habitos no hechos
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        return view;


    }

    public void mostrarTareasFiltradas(boolean hechas){

      session session = new session(requireContext());

      int idUsuario = session.getIdUsuario();

      negocioTarea negocio = new negocioTarea(requireContext(), idUsuario);

        List<Tarea> todas = negocio.listarTareas();
        List<Tarea> filtradas = new ArrayList<>();

        for (Tarea t : todas) {
            if (t.getCheckeado() == hechas) {
                filtradas.add(t);
            }
        }

        tareaAdapter adapter = new tareaAdapter(requireContext(), filtradas);
        lvTareas.setAdapter(adapter);
    }

    private void mostrarHabitosFiltrados(boolean hechos) {

        session sesion = new session(requireContext());
        int idUsuario = sesion.getIdUsuario();

        negocioHabito negocio = new negocioHabito(requireContext(), idUsuario);

        List<Habito> todos = negocio.obtenerHabitos();
        List<Habito> filtrados = new ArrayList<>();

        for (Habito h : todos) {
            if (h.getCheckeado() == hechos) {
                filtrados.add(h);
            }
        }

        habitoAdapter adapter = new habitoAdapter(requireContext(), filtrados);
        lvHabitos.setAdapter(adapter);
    }


    private void mostrarTareas() {

        session sesion = new session(requireContext());
        int idUsuario = sesion.getIdUsuario();

        negocioTarea negocio = new negocioTarea(requireContext(), idUsuario);

        List<Tarea> listaTareas = negocio.listarTareas();

        if(listaTareas == null){
            listaTareas = new ArrayList<>();
        }


        if (negocio.contarTareasPendientes() + negocio.contarTareasHechas() == 0){
            txtTareas.setVisibility(View.VISIBLE);
            txtTareas.setText("No tenés tareas todavía");
        }

        tareaAdapter adapter = new tareaAdapter(requireContext(), listaTareas);

        lvTareas.setAdapter(adapter);
    }
    private void mostrarHabitos() {

        session sesion = new session(requireContext());
        int idUsuario = sesion.getIdUsuario();

        negocioHabito negocio = new negocioHabito(requireContext(), idUsuario);

        List<Habito> listaHabitos = negocio.obtenerHabitos();

        if (listaHabitos == null) {
            listaHabitos = new ArrayList<>();
        }
        if (negocio.contarHabitosNoHechos() + negocio.contarHabitosHechos() == 0){
            txtHabitos.setVisibility(View.VISIBLE);
            txtHabitos.setText("No tenés hábitos todavía");
        }

        habitoAdapter adapter = new habitoAdapter(requireContext(), listaHabitos);

        lvHabitos.setAdapter(adapter);
    }
}
