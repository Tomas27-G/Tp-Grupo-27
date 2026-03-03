package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class fragmentEstadistica extends Fragment {



    public fragmentEstadistica() {
        // Required empty public constructor
    }

    public static fragmentEstadistica newInstance(String param1, String param2) {
        fragmentEstadistica fragment = new fragmentEstadistica();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadistica, container, false);
    }
}