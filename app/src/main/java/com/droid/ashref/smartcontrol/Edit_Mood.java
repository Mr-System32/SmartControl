package com.droid.ashref.smartcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Edit_Mood extends AppCompatActivity {
    static RecyclerView recyclerView_IN;
    static RecyclerView recyclerView_OUT;
    static Recyclerview_IN_Adapter_MOOD recyclerview_in_adapterMOOD;
    static Recyclerview_OUT_Adapter_MOOD recyclerview_out_adapterMOOD;
    private static ArrayList<DataLiset_OUT_IN> lists_IN;
    private static ArrayList<DataLiset_OUT_IN> lists_OUT;
    public static String MoodID, Mood_Name;
    private static Context context;
    TextView ModeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__mood);

        Bundle a = getIntent().getExtras(); // load the notifications
        assert a != null;
        MoodID = a.getString("ID");
        Mood_Name = a.getString("Name");
        context = Edit_Mood.this;
        ModeName = findViewById(R.id.TXT_Mood_Name);
        ModeName.setText(Mood_Name);
        recyclerView_IN = findViewById(R.id.Recycler_Mood_IN);
        recyclerView_IN.setHasFixedSize(true);
        recyclerView_IN.setLayoutManager(new LinearLayoutManager(this));
        lists_IN = new ArrayList<DataLiset_OUT_IN>();
        recyclerview_in_adapterMOOD = new Recyclerview_IN_Adapter_MOOD(lists_IN, getApplicationContext());
        recyclerView_IN.setAdapter(recyclerview_in_adapterMOOD);


        recyclerView_OUT = findViewById(R.id.Recycler_Mood_Out);
        recyclerView_OUT.setHasFixedSize(true);
        recyclerView_OUT.setLayoutManager(new LinearLayoutManager(this));
        lists_OUT = new ArrayList<DataLiset_OUT_IN>();
        recyclerview_out_adapterMOOD = new Recyclerview_OUT_Adapter_MOOD(lists_OUT, getApplicationContext());
        recyclerView_OUT.setAdapter(recyclerview_out_adapterMOOD);


    }

    public void Back(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public static class jsonTask_OUT_Mood extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lists_OUT.clear();
            recyclerview_out_adapterMOOD.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"select_Out_Mood_Device.php?User_ID=" + SaveSettings.UserID + "&Mood_ID=" + MoodID);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

                String fullfile = stringBuffer.toString();

                /*JSONObject jsonObject = new JSONObject(fullfile);
                JSONObject jsonObjectchild = jsonObject.getJSONObject("Device");*/

                JSONArray jsonArray = new JSONArray(fullfile);


                for (int i = 0; i <= jsonArray.length(); i++) {


                    JSONObject child = jsonArray.getJSONObject(i);
                    String ID = child.getString("ID");
                    String D_Name = child.getString("D_Name");


                    lists_OUT.add(new DataLiset_OUT_IN(ID, D_Name));

                }

                //   lists.add(new DataList("ashref","ashref","a23","11"));


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Recyclerview_OUT_Adapter_MOOD recyclerview_out_adapterMOOD = new Recyclerview_OUT_Adapter_MOOD(lists_OUT, context);
            recyclerview_out_adapterMOOD.notifyDataSetChanged();
            recyclerView_OUT.setAdapter(recyclerview_out_adapterMOOD);

        }
    }


    public static class jsonTask_IN_Mood extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lists_IN.clear();
            recyclerview_in_adapterMOOD.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"select_Mood_Device.php?User_ID=" + SaveSettings.UserID + "&Mood_ID=" + MoodID);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

                String fullfile = stringBuffer.toString();

                /*JSONObject jsonObject = new JSONObject(fullfile);
                JSONObject jsonObjectchild = jsonObject.getJSONObject("Device");*/

                JSONArray jsonArray = new JSONArray(fullfile);


                for (int i = 0; i <= jsonArray.length(); i++) {


                    JSONObject child = jsonArray.getJSONObject(i);
                    String ID = child.getString("ID");
                    String D_Name = child.getString("D_Name");


                    lists_IN.add(new DataLiset_OUT_IN(ID, D_Name));

                }

                //   lists.add(new DataList("ashref","ashref","a23","11"));


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Recyclerview_IN_Adapter_MOOD recyclerview_in_adapterMOOD = new Recyclerview_IN_Adapter_MOOD(lists_IN, context);
            recyclerview_in_adapterMOOD.notifyDataSetChanged();
            recyclerView_IN.setAdapter(recyclerview_in_adapterMOOD);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Operations.isConnectingToInternet(getApplicationContext())) {
            // Internet.setVisibility(View.GONE);
            jsonTask_OUT_Mood jsonTask_out_mood = new jsonTask_OUT_Mood();
            jsonTask_out_mood.execute();

            jsonTask_IN_Mood jsonTask_in_mood = new jsonTask_IN_Mood();
            jsonTask_in_mood.execute();

        } else {
//            swipeContainer.setRefreshing(false);
//            Internet.setVisibility(View.VISIBLE);

        }

    }

    public void Refresh() {
        jsonTask_OUT_Mood jsonTask_out_mood = new jsonTask_OUT_Mood();
        jsonTask_out_mood.execute();

        jsonTask_IN_Mood jsonTask_in_mood = new jsonTask_IN_Mood();
        jsonTask_in_mood.execute();
    }
}

