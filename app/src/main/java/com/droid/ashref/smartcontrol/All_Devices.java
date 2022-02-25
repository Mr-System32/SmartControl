package com.droid.ashref.smartcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

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

public class All_Devices extends AppCompatActivity {

    private static RecyclerView recyclerView;
    public static RecycelerViewAdapter recycelerViewAdapter;
    private static ArrayList<DataList> lists;

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__devices);
        context = All_Devices.this;

        recyclerView = findViewById(R.id.Recycler_Devises);
        recyclerView.setScrollContainer(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        lists = new ArrayList<DataList>();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
         recyclerView.setLayoutManager(gridLayoutManager);
    }

    public void Backword(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }


        public static class jsonTask_Devices extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                lists.clear();
            }

            URL url;
            HttpURLConnection httpURLConnection = null;
            InputStream inputStream;
            BufferedReader bufferedReader;
            StringBuffer stringBuffer = new StringBuffer();

            @Override
            protected String doInBackground(String... strings) {
                try {
                    url = new URL(SaveSettings.Url+"read_all.php?User_ID=" + SaveSettings.UserID );

                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    inputStream = httpURLConnection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = " ";

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    String fullfile = stringBuffer.toString();
                    JSONArray jsonArray = new JSONArray(fullfile);
                    for (int i = 0; i <= jsonArray.length(); i++) {

                        JSONObject child = jsonArray.getJSONObject(i);
                        String ID = child.getString("ID");
                        String Name = child.getString("D_Name");
                        String Status = child.getString("D_status");
                        String Power = child.getString("D_Power");
                        String Port = child.getString("D_Port");
                        String Hours = child.getString("D_Hours");
                        String Photo = child.getString("D_Photo");

                        lists.add(new DataList(ID, Name, Status, Power, Port, Hours, Photo));
                    }
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

                RecycelerViewAdapter recycelerViewAdapter = new RecycelerViewAdapter(lists, context);
                recyclerView.setAdapter(recycelerViewAdapter);
                recycelerViewAdapter.notifyDataSetChanged();

            }
        }


    public void Add_Devices(View view) {

        Intent intent=new Intent(this,ADD_Device.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        jsonTask_Devices jsonTaskDevices=new jsonTask_Devices();
        jsonTaskDevices.execute();
    }
}
