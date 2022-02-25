package com.droid.ashref.smartcontrol;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class All_Rooms extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerViewRoom;
    public static RecycelerViewAdapter recycelerViewAdapter;
    public Recyclerview_Adapter_ROOM recycelerViewAdapterRoom;
    private static ArrayList<DataList> lists;
    private static ArrayList<DataList_Room> listsRoom;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__rooms);
        context = All_Rooms.this;

//        jsonTask_Room jsonTask = new jsonTask_Room();
//        jsonTask.execute();

//        jsonTask_Room_Device jsonTask2 = new jsonTask_Room_Device();
//        jsonTask2.execute("1");

        recyclerViewRoom = findViewById(R.id.Recycler_Room);
        recyclerViewRoom.setHasFixedSize(true);
        //    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewRoom.setLayoutManager(new GridLayoutManager(this, 2));
        listsRoom = new ArrayList<DataList_Room>();
        recycelerViewAdapterRoom = new Recyclerview_Adapter_ROOM(listsRoom, context);
        recyclerViewRoom.setAdapter(recycelerViewAdapterRoom);


//        recyclerView = findViewById(R.id.Recycler_Room_Device);
//        recyclerView.setScrollContainer(false);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        lists = new ArrayList<DataList>();
//        recycelerViewAdapter = new RecycelerViewAdapter(lists, context);
//        //   recyclerView.setAdapter(recycelerViewAdapter);

    }

    public void Add_Room(View view) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_room_dialog, null);

        final EditText editText = dialogView.findViewById(R.id.txt_add);
        Button button1 = dialogView.findViewById(R.id.btn_Add);
        Button button2 = dialogView.findViewById(R.id.btn_cancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                String name = editText.getText().toString();
                JsonTask_Add_Room jsonTask_add_room = new JsonTask_Add_Room();
                jsonTask_add_room.execute(name, SaveSettings.UserID);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

    public void Backword(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    //    public static class jsonTask extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            recycelerViewAdapter.clear();
//        }
//
//        URL url;
//        HttpURLConnection httpURLConnection = null;
//        InputStream inputStream;
//        BufferedReader bufferedReader;
//        StringBuffer stringBuffer = new StringBuffer();
//
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                url = new URL("https://ashrefabd.000webhostapp.com/api/read_all.php?User_ID=" + SaveSettings.UserID);
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.connect();
//                inputStream = httpURLConnection.getInputStream();
//
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line = " ";
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(line);
//
//                }
//
//                String fullfile = stringBuffer.toString();
//
//                /*JSONObject jsonObject = new JSONObject(fullfile);
//                JSONObject jsonObjectchild = jsonObject.getJSONObject("Device");*/
//
//                JSONArray jsonArray = new JSONArray(fullfile);
//
//
//                for (int i = 0; i <= jsonArray.length(); i++) {
//
//
//                    JSONObject child = jsonArray.getJSONObject(i);
//                    String ID = child.getString("ID");
//                    String Name = child.getString("D_Name");
//                    String Status = child.getString("D_status");
//                    String Power = child.getString("D_Power");
//                    String Port = child.getString("D_Port");
//                    String Hours = child.getString("D_Hours");
//                    String Photo = child.getString("D_Photo");
//
//                    lists.add(new DataList(ID, Name, Status, Power, Port, Hours,Photo));
//
//                }
//
//                //   lists.add(new DataList("ashref","ashref","a23","11"));
//
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            RecycelerViewAdapter recycelerViewAdapter = new RecycelerViewAdapter(lists, context);
//            recycelerViewAdapter.notifyDataSetChanged();
//            recyclerView.setAdapter(recycelerViewAdapter);
//
//         //   swipeContainer.setRefreshing(false);
//
//        }
//    }
    // Load Mood


    public static class jsonTask_Room extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listsRoom.clear();
            //   recycelerViewAdapterRoom.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"selectRoom.php?User_ID=" + SaveSettings.UserID);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";
                // listsRoom.add(new DataList_Room("00", "All Devises"));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

                String fullfile = stringBuffer.toString();

                /*JSONObject jsonObject = new JSONObject(fullfile);
                JSONObject jsonObjectchild = jsonObject.getJSONObject("Device");*/

                JSONArray jsonArray = new JSONArray(fullfile);


                for (int i = 0; i <= jsonArray.length(); i++) {


                    JSONObject child = jsonArray.getJSONObject(i);
                    String Room_ID = child.getString("Room_ID");
                    String R_Name = child.getString("R_Name");


                    listsRoom.add(new DataList_Room(Room_ID, R_Name));

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

            Recyclerview_Adapter_ROOM recyclerview__AdapterRoom = new Recyclerview_Adapter_ROOM(listsRoom, context);
            recyclerViewRoom.setAdapter(recyclerview__AdapterRoom);
            //swipeContainer.setRefreshing(false);

        }
    }


//    public static class jsonTask_Room_Device extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            lists.clear();
//        }
//
//        URL url;
//        HttpURLConnection httpURLConnection = null;
//        InputStream inputStream;
//        BufferedReader bufferedReader;
//        StringBuffer stringBuffer = new StringBuffer();
//
//        @Override
//        protected String doInBackground(String... strings) {
//            try {
//                url = new URL("https://ashrefabd.000webhostapp.com/api/select_Room_Device.php?User_ID=" + SaveSettings.UserID + "&Room_ID=" + strings[0]);
//
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.connect();
//                inputStream = httpURLConnection.getInputStream();
//
//                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                String line = " ";
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(line);
//                }
//                String fullfile = stringBuffer.toString();
//                JSONArray jsonArray = new JSONArray(fullfile);
//                for (int i = 0; i <= jsonArray.length(); i++) {
//
//                    JSONObject child = jsonArray.getJSONObject(i);
//                    String ID = child.getString("ID");
//                    String Name = child.getString("D_Name");
//                    String Status = child.getString("D_status");
//                    String Power = child.getString("D_Power");
//                    String Port = child.getString("D_Port");
//                    String Hours = child.getString("D_Hours");
//                    String Photo = child.getString("D_Photo");
//
//                    lists.add(new DataList(ID, Name, Status, Power, Port, Hours, Photo));
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            RecycelerViewAdapter recycelerViewAdapter = new RecycelerViewAdapter(lists, context);
//            recycelerViewAdapter.notifyDataSetChanged();
//            recyclerView.setAdapter(recycelerViewAdapter);
//
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonTask_Room jsonTask = new jsonTask_Room();
        jsonTask.execute();

    }

    public class JsonTask_Add_Room extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";

        @Override
        protected String doInBackground(String... strings) {
            try {


                url = new URL(SaveSettings.Url+"insert_Room.php?Room_Name=" + strings[0] + "&User_ID=" + strings[1]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                String fullfile = stringBuffer.toString();

                JSONObject jObject = new JSONObject(fullfile);
                aJsonString = jObject.getString("message");


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
            All_Rooms.jsonTask_Room jsonTask_room = new All_Rooms.jsonTask_Room();
            jsonTask_room.execute();
            Toast.makeText(context, aJsonString, Toast.LENGTH_LONG).show();
            //finish();
        }
    }


}
