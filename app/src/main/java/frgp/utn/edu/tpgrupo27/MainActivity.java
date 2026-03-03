package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;
import android.view.View;

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
        session session = new session(this);
        BottomNavigationView bottomNavigation =
                findViewById(R.id.buttom_navigation);

        Fragment fragmentInicial;

        if(!session.estaLogueado()){
            fragmentInicial = new fragmentLogin();
            bottomNavigation.setVisibility(View.GONE);
        } else {
            fragmentInicial = new fragmentInicio();
            bottomNavigation.setVisibility(View.VISIBLE);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentInicial)
                .commit();

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
            if (item.getItemId() == R.id.stats) {
                fragment = new fragmentEstadistica();
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


    }
}