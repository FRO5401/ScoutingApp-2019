package com.example.nyarlathotech.froscouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import static android.os.Environment.getExternalStorageDirectory;

//This class is pretty simple, takes user inputand creates a text file that can be read
public class TeamsList extends Fragment {

    public TeamsList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Teams = inflater.inflate(R.layout.fragment_teamslist, container, false);

        final TextInputEditText teamsText = Teams.findViewById(R.id.edittext);
        Button submitButton = Teams.findViewById(R.id.SUBMIT);

        /*
            This onClickListener allows for the user to...
                *Submit team data
                *Create a text file that they can copy afterwards
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Path creation and new File
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting/Teams";
                File teamPath = new File(path);

                if(!teamPath.exists()){
                    teamPath.mkdirs();
                }
                else{

                    FileOutputStream teams = null;

                    //Needed for error catching during instantiation and data creation
                    try {
                        File test = new File(teamPath, "teams.txt");
                        teams = new FileOutputStream(test);

                        //The replaceAll removes spaces the Array can be read properly
                        teams.write(teamsText.getText().toString().replaceAll("\\s","").getBytes());
                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    finally{
                        if (teams != null){
                            try{
                                teams.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        //Returns View
        return Teams;
    }

}
