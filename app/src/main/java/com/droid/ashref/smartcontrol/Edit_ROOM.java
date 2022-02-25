package com.droid.ashref.smartcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class Edit_ROOM extends AppCompatActivity {

    static RecyclerView recyclerView_IN;
    static RecyclerView recyclerView_OUT;
    static Recyclerview_IN_Adapter_ROOM recyclerview_in_adapter_room;
    static Recyclerview_OUT_Adapter_ROOM recyclerview_out_adapter_room;
    private static ArrayList<DataLiset_OUT_IN> lists_IN;
    private static ArrayList<DataLiset_OUT_IN> lists_OUT;
    public static String RoomID, Room_Name;
    private static Context context;
    private TextView RoomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__room);

        Bundle a = getIntent().getExtras(); // load the notifications
        assert a != null;
        RoomID = a.getString("ID");
        Room_Name = a.getString("Name");
        context = Edit_ROOM.this;
        RoomName=findViewById(R.id.TXT_Room_Name);
        RoomName.setText(Room_Name);
        recyclerView_IN = findViewById(R.id.Recycler_Room_IN);
        recyclerView_IN.setHasFixedSize(true);
        recyclerView_IN.setLayoutManager(new LinearLayoutManager(this));
        lists_IN = new ArrayList<DataLiset_OUT_IN>();
        recyclerview_in_adapter_room = new Recyclerview_IN_Adapter_ROOM(lists_IN, getApplicationContext());
        recyclerView_IN.setAdapter(recyclerview_in_adapter_room);


        recyclerView_OUT = findViewById(R.id.Recycler_Room_OUT);
        recyclerView_OUT.setHasFixedSize(true);
        recyclerView_OUT.setLayoutManager(new LinearLayoutManager(this));
        lists_OUT = new ArrayList<DataLiset_OUT_IN>();
        recyclerview_out_adapter_room = new Recyclerview_OUT_Adapter_ROOM(lists_OUT, getApplicationContext());
        recyclerView_OUT.setAdapter(recyclerview_out_adapter_room);


    }

    public static class jsonTask_OUT_Room extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lists_OUT.clear();
            recyclerview_out_adapter_room.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"select_Out_Room_Device.php?User_ID=" + SaveSettings.UserID + "&Room_ID=" + RoomID);
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

            Recyclerview_OUT_Adapter_ROOM recyclerview_out_adapter_room = new Recyclerview_OUT_Adapter_ROOM(lists_OUT, context);
            recyclerview_out_adapter_room.notifyDataSetChanged();
            recyclerView_OUT.setAdapter(recyclerview_out_adapter_room);

        }
    }


    public static class jsonTask_IN_Room extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            lists_IN.clear();
            recyclerview_in_adapter_room.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"select_Room_Device.php?User_ID=" + SaveSettings.UserID + "&Room_ID=" + RoomID);
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

            Recyclerview_IN_Adapter_ROOM recyclerview_in_adapter_room = new Recyclerview_IN_Adapter_ROOM(lists_IN, context);
            recyclerview_in_adapter_room.notifyDataSetChanged();
            recyclerView_IN.setAdapter(recyclerview_in_adapter_room);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Operations.isConnectingToInternet(getApplicationContext())) {
            // Internet.setVisibility(View.GONE);
            Edit_ROOM.jsonTask_OUT_Room jsonTask_out_room = new Edit_ROOM.jsonTask_OUT_Room();
            jsonTask_out_room.execute();

            Edit_ROOM.jsonTask_IN_Room jsonTask_in_room = new Edit_ROOM.jsonTask_IN_Room();
            jsonTask_in_room.execute();

        } else {
//            swipeContainer.setRefreshing(false);
//            Internet.setVisibility(View.VISIBLE);

        }

    }

//    public void Refresh() {
//        Edit_Mood.jsonTask_OUT_Mood jsonTask_out_mood = new Edit_Mood.jsonTask_OUT_Mood();
//        jsonTask_out_mood.execute();
//
//        Edit_Mood.jsonTask_IN_Mood jsonTask_in_mood = new Edit_Mood.jsonTask_IN_Mood();
//        jsonTask_in_mood.execute();
//    }
}
