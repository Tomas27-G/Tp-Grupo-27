package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import utils.session;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieDataSet;

import com.github.mikephil.charting.utils.ColorTemplate;

public class fragmentInicio extends Fragment {

    private BarChart barChartGeneral;
    private PieChart pieChartTareas;
    private PieChart pieChartHabitos;

    public fragmentInicio() {}

    @Override
    public void onResume() {
        super.onResume();

        TextView txtBienvenida =
                requireActivity().findViewById(R.id.txtBienvenida);

        if (txtBienvenida != null) {
            txtBienvenida.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        session session = new session(getContext());

        Button btnCerrar = view.findViewById(R.id.btnCerrar);

        btnCerrar.setOnClickListener(v -> {
            session.cerrarSesion();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new fragmentLogin())
                    .commit();
        });

        // REFERENCIAS DE GRAFICOS

        barChartGeneral = view.findViewById(R.id.barChartGeneral);
        pieChartTareas = view.findViewById(R.id.pieChartTareas);
        pieChartHabitos = view.findViewById(R.id.pieChartHabitos);

        cargarGraficoGeneral();
        cargarGraficoTareas();
        cargarGraficoHabitos();
    }

    // =========================
    // GRAFICO GENERAL (BARRAS)
    // =========================

    private void cargarGraficoGeneral(){

        ArrayList<BarEntry> entries = new ArrayList<>();

        // DATOS DE EJEMPLO

        float habitosHechos = 7;
        float habitosNoHechos = 3;
        float tareasHechas = 5;
        float tareasNoHechas = 4;

        entries.add(new BarEntry(0, habitosHechos));
        entries.add(new BarEntry(1, habitosNoHechos));
        entries.add(new BarEntry(2, tareasHechas));
        entries.add(new BarEntry(3, tareasNoHechas));

        BarDataSet dataSet = new BarDataSet(entries, "Resumen General");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData data = new BarData(dataSet);

        barChartGeneral.setData(data);
        barChartGeneral.setFitBars(true);
        barChartGeneral.animateY(1000);
        barChartGeneral.invalidate();
    }

    // =========================
    // PIE TAREAS
    // =========================

    private void cargarGraficoTareas(){

        ArrayList<PieEntry> entries = new ArrayList<>();

        float tareasHechas = 5;
        float tareasNoHechas = 4;

        entries.add(new PieEntry(tareasHechas, "Hechas"));
        entries.add(new PieEntry(tareasNoHechas, "Pendientes"));

        PieDataSet dataSet = new PieDataSet(entries, "Tareas");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        pieChartTareas.setData(data);
        pieChartTareas.setCenterText("Tareas");
        pieChartTareas.animateY(1000);
        pieChartTareas.invalidate();
    }

    // =========================
    // PIE HABITOS
    // =========================

    private void cargarGraficoHabitos(){

        ArrayList<PieEntry> entries = new ArrayList<>();

        float habitosHechos = 7;
        float habitosNoHechos = 3;

        entries.add(new PieEntry(habitosHechos, "Hechos"));
        entries.add(new PieEntry(habitosNoHechos, "No hechos"));

        PieDataSet dataSet = new PieDataSet(entries, "Hábitos");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);

        pieChartHabitos.setData(data);
        pieChartHabitos.setCenterText("Hábitos");
        pieChartHabitos.animateY(1000);
        pieChartHabitos.invalidate();
    }
}