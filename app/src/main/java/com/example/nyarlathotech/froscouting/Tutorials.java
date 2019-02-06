package com.example.nyarlathotech.froscouting;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


//Haven't added any yet lol but this class would just need
        //A BUNCH OF VIDEO PLAYERS INSTANTIATED PROBABLY
public class Tutorials extends Fragment {


    public Tutorials() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorials, container, false);
    }

}
