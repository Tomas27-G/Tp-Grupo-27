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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class fragmentInicio extends Fragment {

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

    public static fragmentInicio newInstance(String param1, String param2) {
        fragmentInicio fragment = new fragmentInicio();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

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

        // =========================
        // PIE CHART DE PRUEBA
        // =========================

        PieChart pieChart = view.findViewById(R.id.pieChart);

        ArrayList<PieEntry> entries = new ArrayList<>();

        // Datos de prueba
        entries.add(new PieEntry(70f, "Completados"));
        entries.add(new PieEntry(30f, "Pendientes"));

        PieDataSet dataSet = new PieDataSet(entries, "Hábitos");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.setCenterText("Estadísticas");
        pieChart.animateY(1000);
        pieChart.invalidate();
    }
}