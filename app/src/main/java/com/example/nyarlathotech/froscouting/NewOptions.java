package com.example.nyarlathotech.froscouting;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static android.os.Environment.getExternalStorageDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewOptions extends Fragment {

    LinkedList<String> AddedList = new LinkedList<>();
    ArrayList<ScoutingOptions> SOList = new ArrayList<>();
    String OptionSelected = "", ModeSelected = "";
    EditText Title, one, two, three;
    private Context mContext;
    int i = 0;

    public NewOptions() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View newOptions = inflater.inflate(R.layout.fragment_new_options, container, false);

        mContext = getContext();

        final Spinner OptionList = newOptions.findViewById(R.id.types);
        final Spinner ModesList = newOptions.findViewById(R.id.modes);

        ArrayAdapter<String> options = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, new ArrayList<String>());
        options.add("Add/Subtract");
        options.add("True/False");
        options.add("Input Text");
        options.add("Multiple Choice");

        ArrayAdapter<String> modes = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, new ArrayList<String>());
        modes.add("Auto");
        modes.add("Teleop");
        modes.add("End");

        OptionList.setAdapter(options);
        ModesList.setAdapter(modes);

        Title = newOptions.findViewById(R.id.TitleOption);
        one = newOptions.findViewById(R.id.OneOption);
        two = newOptions.findViewById(R.id.OptionTwo);
        three = newOptions.findViewById(R.id.OptionThree);
        one.setText("");
        two.setText("");
        three.setText("");

        Button Add = newOptions.findViewById(R.id.add);
        Button Save = newOptions.findViewById(R.id.save);
        Button EditCurrent = newOptions.findViewById(R.id.Edit);
        final GridView Preview = newOptions.findViewById(R.id.preview_options);

        OptionList.setSelection(0);
        ModesList.setSelection(0);

        placeHolder();

        EditCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    final String path = getExternalStorageDirectory().getAbsolutePath() + "/Scouting/Layouts";
                    File pathCreate = new File(path);

                    if(!pathCreate.exists()) {
                        pathCreate.mkdirs();
                    }

                    FileReader teams =  new FileReader(path + "/layout.txt");
                    final BufferedReader test = new BufferedReader(teams);
                    final String testData = test.readLine();
                    final String[] newBois = testData.substring(1,testData.length()-1).split(",");

                    SOList.clear();
                    AddedList.clear();

                    if(newBois.length < 4){
                        for(int i = 0; i < 1; i++){
                            SOList.add(new ScoutingOptions(new String[] {"3", "No Options, Refresh Page"," "," "," ", " "," ",}));
                        }
                    } else {

                        for(int i = 0; i < newBois.length; i++){
                            AddedList.add(newBois[i].trim());
                        }

                        for (int i = 0; i < newBois.length; i+=7) {
                            SOList.add(new ScoutingOptions(new String[]{newBois[i].trim(), newBois[i + 1].trim(), newBois[i + 2].trim(), newBois[i + 3].trim(), newBois[i + 4].trim(), newBois[i + 5].trim(), newBois[i + 6].trim()}));
                        }

                        final ScoutingAdapter newScouting = new ScoutingAdapter(mContext, SOList);
                        Preview.setAdapter(newScouting);

                        int NumRows = ((((newBois.length + 7) / 7)) / 2);

                        ViewGroup.LayoutParams gridHeight =  Preview.getLayoutParams();
                        gridHeight.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ((NumRows - 1) * 220), mContext.getResources().getDisplayMetrics());
                        Preview.setLayoutParams(gridHeight);

                        Toast.makeText(mContext, "Current Layout Imported", Toast.LENGTH_SHORT).show();

                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        OptionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i) == "Add/Subtract"){
                    OptionSelected = "";
                    OptionSelected = "1";
                } else if(adapterView.getItemAtPosition(i) == "True/False"){
                    OptionSelected = "";
                    OptionSelected = "2";
                } else if(adapterView.getItemAtPosition(i) == "Input Text"){
                    OptionSelected = "";
                    OptionSelected = "3";
                } else if(adapterView.getItemAtPosition(i) == "Multiple Choice"){
                    OptionSelected = "";
                    OptionSelected = "4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                OptionSelected = "";
            }
        });

        ModesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i) == "Auto"){
                    ModeSelected = "auto";
                } else if(adapterView.getItemAtPosition(i) == "Teleop"){
                    ModeSelected = "teleops";
                } else if(adapterView.getItemAtPosition(i) == "End"){
                    ModeSelected = "end";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ModeSelected = "";
            }
        });


        //Add button creates a small array that is added to a bigger array, that creates the values for
        //card type, title, choices, and game mode
        //It also resets the height of the GridView to simulate the view of a nested view
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddedList.add(OptionSelected);
                AddedList.add(Title.getText().toString());
                AddedList.add(one.getText().toString());
                AddedList.add(two.getText().toString());
                AddedList.add(three.getText().toString());
                AddedList.add(ModeSelected);
                AddedList.add("#");

                String[] newBoi = AddedList.toArray(new String[AddedList.size()]);

                SOList.clear();

                for(int i = 0; i < newBoi.length; i+=7) {
                    String newerboi[] = Arrays.copyOfRange(newBoi, (i), (i+6));
                    ScoutingOptions evenNewerboi = new ScoutingOptions(newerboi);
                    SOList.add(evenNewerboi);
                }

                int NumRows = ((((newBoi.length+7)/7))/2);

                ScoutingAdapter newScouting = new ScoutingAdapter(mContext, SOList);
                Preview.setAdapter(newScouting);

                ViewGroup.LayoutParams gridHeight = Preview.getLayoutParams();
                gridHeight.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ((NumRows -1) * 220), mContext.getResources().getDisplayMetrics());
                Preview.setLayoutParams(gridHeight);

            }
        });

        //Saves current layout to a local textFile
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting/Layouts";
                File option2 = new File(path);

                if(!option2.exists()) {
                    option2.mkdirs();
                } else {
                    FileOutputStream options = null;
                    try {
                        File test = new File(option2, "layout.txt");
                        options = new FileOutputStream(test);
                        options.write(AddedList.toString().getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (options != null) {
                            try {
                                options.close();
                                Toast.makeText(mContext, "Changes Saved", Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        return newOptions;
    }

    //This method creates a placeholder card so that when the ScoutingAdapter gets data it doesn't reset the first card to a null/zero value, as when the
    //method runs, it does this every time without fault!
    public void placeHolder(){

        //Type 5 is only used as a placeholder card
        AddedList.add("5");
        AddedList.add("");
        AddedList.add("");
        AddedList.add("");
        AddedList.add("");
        AddedList.add("auto");
        AddedList.add("#");

        AddedList.add("5");
        AddedList.add("");
        AddedList.add("");
        AddedList.add("");
        AddedList.add("");
        AddedList.add("auto");
        AddedList.add("#");

    }

}
