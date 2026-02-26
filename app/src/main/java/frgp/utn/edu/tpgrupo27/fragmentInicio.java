package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class fragmentInicio extends Fragment {



    public fragmentInicio() {
        // Required empty public constructor
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
}