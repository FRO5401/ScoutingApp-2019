package com.example.nyarlathotech.froscouting;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.Inflater;

import static android.os.Environment.getExternalStorageDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreGame extends Fragment {

    Spinner team_spinner;
    EditText match, preName;
    Button AllowScout;
    String Position = "", Team = "";

    public PreGame() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pregameview = inflater.inflate(R.layout.fragment_pre_game, container, false);

        team_spinner = pregameview.findViewById(R.id.team_spinner);

        try {
            String path = getExternalStorageDirectory().getAbsolutePath() + "/Scouting/Teams";
            File pathCreate = new File(path);

            if (!pathCreate.exists()) {
                pathCreate.mkdirs();
            } else {

                    FileReader teams = new FileReader(path + "/teams.txt");
                    BufferedReader test = new BufferedReader(teams);
                    String spinnerTeams = test.readLine();

                    if(spinnerTeams.length() > 2) {
                        ArrayAdapter<String> teamsSpinner = new ArrayAdapter<>(getContext(), R.layout.spinner, spinnerTeams.split(","));
                        team_spinner.setAdapter(teamsSpinner);
                    }

                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences MatchGetter = getContext().getSharedPreferences("MatchNum", Context.MODE_PRIVATE);

        match = pregameview.findViewById(R.id.MatchText);
        preName = pregameview.findViewById(R.id.NameText);

        if(MatchGetter.getString("match", "") == null || MatchGetter.getString("match", "").equals("")) {
            match.setText("1");
        }else{
            match.setText(MatchGetter.getString("match", ""));
        }

        final RadioGroup position = pregameview.findViewById(R.id.PreGroup);

        position.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(position.getCheckedRadioButtonId() == R.id.Left){
                    Position = "1";
                    Toast.makeText(getContext(), Position, Toast.LENGTH_LONG).show();
                } else if (position.getCheckedRadioButtonId() == R.id.Center){
                    Position = "2";
                    Toast.makeText(getContext(), Position, Toast.LENGTH_LONG).show();
                } else if (position.getCheckedRadioButtonId() == R.id.Right){
                    Position = "3";
                    Toast.makeText(getContext(), Position, Toast.LENGTH_LONG).show();
                } else {
                    Position = "Else?";
                }
            }
        });

        team_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Team = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        AllowScout = pregameview.findViewById(R.id.AllowScout);

        AllowScout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Confirming...");
                alertDialog.setMessage("Is all of this information correct? You cannot go back...");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                onAllow(preName.getText().toString(), match.getText().toString(), Position);
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                alertDialog.show();

            }
        });

        return pregameview;

    }

    private void onAllow(String Name, String Match, String Post){
        if( (Name == "" || Name == null || Name == " " || Name.length() < 3) || (Match == "" || Match == null || Match == " ") || (Post == "Else?" || Post == null) || (Team == null || Team == "")){
            Snackbar.make(getView(), "Please make sure all fields are filled in!!", 3000).show();
        } else if ( (Name != "") && (Match != "") && (Post != "" || Post != null)){
            Bundle info = new Bundle();
            info.putString("Name", Name);
            info.putString("Team", Team);
            info.putString("Match", Match);
            info.putString("Position", Post);

            Scouting Scout = new Scouting();
            Scout.setArguments(info);

            SharedPreferences MatchGetter = getContext().getSharedPreferences("MatchNum", Context.MODE_PRIVATE);
            SharedPreferences.Editor matchEditor = MatchGetter.edit();
            matchEditor.putString("match",match.getText().toString());
            matchEditor.apply();

            androidx.fragment.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.FragMain, Scout).commit();


        }
    }

}
