package frgp.utn.edu.tpgrupo27;
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

        // SPINNER FILTRO

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
        // 🔥 ACÁ VA EL LISTENER
        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        mostrarTareas();
                        mostrarHabitos();
                        break;

                    case 1:
                        mostrarTareas(); //Todas las tareas
                        break;

                    case 2:
                        mostrarTareasFiltradas(false);// Tareas hechas
                        break;

                    case 3:
                        mostrarHabitosFiltrados(true);// Tareas pendientes
                        break;

                    case 4:
                        mostrarHabitos(); //Todos los habitos
                        break;

                    case 5:
                         // mostrar habitos hechos
                        break;

                    case 6:
                        // mostrar habitos no hechos
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // ⚠️ OPCIONAL: esto ya no hace falta si usás el spinner
        // mostrarTareas();
        // mostrarHabitos();

        return view;


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
