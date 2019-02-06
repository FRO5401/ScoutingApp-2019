package com.example.nyarlathotech.froscouting;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//This class is a barrier for people to make edits to, though the password is hardcoded soooo only programmers should know it
public class UnlockCreate extends Fragment {


    public UnlockCreate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Unlock = inflater.inflate(R.layout.fragment_unlock_create, container, false);

        Button Teams = Unlock.findViewById(R.id.Teams);
        Button Layout = Unlock.findViewById(R.id.Layout);
        Button Cancel = Unlock.findViewById(R.id.cancel);
        final EditText password = Unlock.findViewById(R.id.password);

        //Allows the user to edit the teams used by the team for data collection
        Teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals("team5401password")){
                    TeamsList team = new TeamsList();
                    androidx.fragment.app.FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.FragMain, team).commit();
                    Toast.makeText(getContext(), "TEAMS LIST", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Password Incorrect", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Allows users to edit the apps layout for the team to use
        Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals("team5401password")){
                    NewOptions newOption = new NewOptions();
                    androidx.fragment.app.FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.FragMain, newOption).commit();
                    Toast.makeText(getContext(), "LAYOUT CREATOR", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Allows user to cancel password input/creation of cards/teams
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment home = new HomeFragment();
                androidx.fragment.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.FragMain, home).commit();
                Toast.makeText(getContext(), "CANCELLED", Toast.LENGTH_SHORT).show();
            }
        });


        return Unlock;


    }

}
