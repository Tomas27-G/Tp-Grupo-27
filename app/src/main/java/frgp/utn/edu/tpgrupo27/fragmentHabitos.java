package frgp.utn.edu.tpgrupo27;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentHabitos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentHabitos extends Fragment {



    public fragmentHabitos() {
        // Required empty public constructor
    }


    public static fragmentHabitos newInstance(String param1, String param2) {
        fragmentHabitos fragment = new fragmentHabitos();
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
        return inflater.inflate(R.layout.habitos, container, false);
    }
}