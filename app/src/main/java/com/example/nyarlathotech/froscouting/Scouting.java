package com.example.nyarlathotech.froscouting;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import static android.os.Environment.getExternalStorageDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Scouting extends Fragment {

    //Array that all Scouting Cards are added to
    private ArrayList<ScoutingOptions> scoutingList = new ArrayList<>();
    private Context mContext;

    public Scouting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Scouting = inflater.inflate(R.layout.fragment_scouting, container, false);

        mContext = getContext();

        try {

            final CustomGrid scoutingOptions = Scouting.findViewById(R.id.scouting_options);

            final String path = getExternalStorageDirectory().getAbsolutePath() + "/Scouting/Layouts";
            File pathCreate = new File(path);

            if(!pathCreate.exists()) {
                pathCreate.mkdirs();
            }

            FileReader teams =  new FileReader(path + "/layout.txt");
            final BufferedReader reader = new BufferedReader(teams);
            final String data = reader.readLine();
            final String[] newDataFromFile = data.substring(1,data.length()-1).split(",");

            if(newDataFromFile.length < 4){
                for(int i = 0; i < 1; i++){
                    //Adds null card if there's not anything in the text file
                    scoutingList.add(new ScoutingOptions(new String[] {"3", "No Options"," "," "," ", " "," ",}));
                }
            } else {

                //Goes through text file array and sorts accordingly
                for(int i = 0; i < newDataFromFile.length; i+=7) {
                    scoutingList.add(new ScoutingOptions(new String[] {newDataFromFile[i].trim(), newDataFromFile[i+1].trim(), newDataFromFile[i+2].trim(), newDataFromFile[i+3].trim(), newDataFromFile[i+4].trim(), newDataFromFile[i+5].trim(), newDataFromFile[i+6].trim()}));
                }

                //Sets scouting adapter to the gridview and expand it
                final ScoutingAdapter newScouting = new ScoutingAdapter(mContext, scoutingList);
                scoutingOptions.setAdapter(newScouting);
                scoutingOptions.setExpanded(true);

                final Button SUBMIT = Scouting.findViewById(R.id.SUBMIT);

                SUBMIT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        alertDialog.setTitle("Confirming...");
                        alertDialog.setMessage("Are all forms filled? Please double check");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ArrayList<String> PostData = new ArrayList<>();
                                        ArrayList<String> PostDataUnaltered = new ArrayList<>();

                                        PostData.clear();
                                        PostDataUnaltered.clear();

                                        String SCOUT = getArguments().getString("Name");
                                        String TEAM = getArguments().getString("Team");
                                        String POSITION = getArguments().getString("Position");
                                        String MATCH = getArguments().getString("Match");

                                        PostData.add(SCOUT);
                                        PostData.add(TEAM);
                                        PostData.add(POSITION);
                                        PostData.add(MATCH);
                                        PostDataUnaltered.add(SCOUT);
                                        PostDataUnaltered.add(TEAM);
                                        PostDataUnaltered.add(POSITION);
                                        PostDataUnaltered.add(MATCH);

                                        for (int i = 2; i < newScouting.getCount(); i++) {
                                            PostData.add(newScouting.getScoutingData(i));
                                            PostDataUnaltered.add(newScouting.getScoutingData(i));
                                        }

                                        if (PostData.size() != 34) {
                                            int avoidNull = 34 - PostData.size();
                                            for (int i = 0; i < avoidNull + 1; i++) {
                                                PostData.add(" ");
                                            }
                                        }

                                        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                                        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

                                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                                            String[] Data = PostData.toArray(new String[PostData.size()]);
                                            String[] Unaltered = PostDataUnaltered.toArray(new String[PostDataUnaltered.size()]);
                                            sendData(Data);
                                            sendLocal(Unaltered);
                                        } else if (activeNetworkInfo == null) {
                                            Toast.makeText(mContext, "Internet not Available, only saving locally...", Toast.LENGTH_LONG).show();
                                            String[] Data = PostDataUnaltered.toArray(new String[PostDataUnaltered.size()]);
                                            sendLocal(Data);
                                        }

                                        SharedPreferences MatchGetter = getContext().getSharedPreferences("MatchNum", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor matchEditor = MatchGetter.edit();
                                        matchEditor.putString("match", String.valueOf(Integer.valueOf(MATCH) + 1));
                                        matchEditor.apply();

                                        PreGame pre = new PreGame();
                                        androidx.fragment.app.FragmentManager fragmentManager = getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.FragMain, pre).commit();
                                        fragmentManager.beginTransaction().replace(R.id.FragMain, pre).commit();

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
            }

        }
        catch(NullPointerException e){
            System.out.print("ScoutingView:" + e);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return Scouting;
    }


    //This method sends data to sheets if the internet is available
    public void sendData(String[] params) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/u/2/d/e/1FAIpQLSf-u1WwhqdFuGCQPPq4rwhpqpmAxS52zmilf81fyMeQItLEPQ/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ScoutingPost userService = retrofit.create(ScoutingPost.class);

        Call<SerializedData> userCall = userService.savePost(params[0], params[1], params[2], params[3],
                                                             params[4],params[5],params[6],params[7],
                                                             params[8],params[9],params[10],params[11],
                                                             params[12],params[13],params[14],params[15],
                                                             params[16],params[17],params[18],params[19],
                                                             params[20],params[21],params[22],params[23],
                                                             params[24],params[25],params[26],params[27],
                                                             params[28],params[29],params[30],params[30],
                                                             params[32],params[33]);


        userCall.enqueue(new Callback<SerializedData>() {
            @Override
            public void onResponse(Call<SerializedData> call, Response<SerializedData> response) {
                try {
                    Toast.makeText(mContext, "Submitted to Cloud!!", Toast.LENGTH_LONG).show();
                    Log.d("Response: ",response.body().toString());

                }
                catch (NullPointerException e) {
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            //This onFailure says the same due to Sheets needing a TimeStamp that is clearly not provided
            @Override
            public void onFailure(Call<SerializedData> call, Throwable t) {
                Toast.makeText(mContext, "Submitted to Cloud!!", Toast.LENGTH_LONG).show();
                Log.d("Response: ",t.toString());
            }
        });
    }

    //This method saves data locally to the tablet in said directory even if there is internet available
    public void sendLocal(String[] params){

        String pathLocal = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Scouting/Data";
        File local = new File(pathLocal);

        if(!local.exists()) {
            local.mkdirs();
        }
        else {

            FileOutputStream localData = null;

            try {

                String pathNum = getExternalStorageDirectory().getAbsolutePath() + "/Scouting/ID";
                File pathNUM = new File(pathNum);

                if(!pathNUM.exists()) {
                    pathNUM.mkdirs();
                }

                FileReader teams =  new FileReader(pathNum + "/ID.txt");
                BufferedReader numberData = new BufferedReader(teams);
                String number = numberData.readLine();

                File data = new File(local, "data" + number + ".csv");
                localData = new FileOutputStream(data, true);

                if(data.exists()){
                    for(int i = 0; i < params.length; i++){
                        localData.write((params[i]+",").getBytes());
                    }
                    localData.write("\n".getBytes());
                }
                else if (data.exists()) {
                    FileOutputStream localData2 = new FileOutputStream(data, true);
                    for(int i = 0; i < params.length; i++){
                        localData2.write((params[i]+",").getBytes());
                    }
                    localData2.write("\n".getBytes());
                }

            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (localData != null) {
                    try {
                        localData.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
