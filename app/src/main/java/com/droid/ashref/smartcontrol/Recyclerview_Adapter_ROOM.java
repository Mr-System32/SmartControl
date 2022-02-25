package com.droid.ashref.smartcontrol;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class Recyclerview_Adapter_ROOM extends RecyclerView.Adapter<DataViewHolder_Room> {


    private ArrayList<DataList_Room> arrayList;
    private Context context;

    public Recyclerview_Adapter_ROOM() {
    }

    public Recyclerview_Adapter_ROOM(ArrayList<DataList_Room> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataViewHolder_Room onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_room, viewGroup, false);
        return new DataViewHolder_Room(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder_Room dataViewHolder, int i) {

        final DataList_Room currentDatalist = arrayList.get(i);
        dataViewHolder.Room.setText(currentDatalist.getName());

        dataViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                All_Rooms.jsonTask_Room_Device jsonTask = new All_Rooms.jsonTask_Room_Device();
//                jsonTask.execute(currentDatalist.ID);
            }
        });

        final CardView cardView = dataViewHolder.cardView;
        dataViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, cardView);
                //inflating menu from xml resource
                popup.inflate(R.menu.room_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            //handle menu1 click
                            case R.id.Edit:
                                Intent myintents = new Intent(context, Edit_ROOM.class);
                                myintents.putExtra("ID", currentDatalist.getID());
                                myintents.putExtra("Name", currentDatalist.getName());
                                context.startActivity(myintents);
                                break;
                            case R.id.Delete:
                                JsonTask_Delete_Room jsonTask_delete_room = new JsonTask_Delete_Room();
                                jsonTask_delete_room.execute(currentDatalist.getID());
                                break;
                            case R.id.Rename:
                                //handle menu3 click
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class JsonTask_Delete_Room extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";

        @Override
        protected String doInBackground(String... strings) {
            try {


                url = new URL(SaveSettings.Url+"delete_Room.php?Room_ID=" + strings[0]);
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

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }




}