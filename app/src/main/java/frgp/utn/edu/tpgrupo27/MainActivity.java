package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import utils.session;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigation = findViewById(R.id.buttom_navigation);
        // Cargar fragmento inicial por defecto si es la primera vez
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new fragmentInicio())
                    .commit();
        }
        bottomNavigation.setOnItemSelectedListener(item -> {

            Fragment fragment = null;
            if (item.getItemId() == R.id.inicio) {
                fragment = new fragmentInicio();
            }
            if (item.getItemId() == R.id.tareas) {
                fragment = new fragmentTareas();
            }
            if (item.getItemId() == R.id.habitos) {
                fragment = new fragmentHabitos();
            }
            if (item.getItemId() == R.id.agenda) {
                fragment = new fragmentAgenda();
            }

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }

            return false;
        });

        session session = new session(this);

        if(!session.estaLogueado()){
            // Usuario no logueado → mostrar login
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new fragmentLogin())
                    .commit();
        } else {
            // Usuario logueado → mostrar fragmento de inicio
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new fragmentInicio())
                        .commit();
            }
        }
    }
}