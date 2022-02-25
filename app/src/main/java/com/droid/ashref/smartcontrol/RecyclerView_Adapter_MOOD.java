package com.droid.ashref.smartcontrol;

import android.content.Context;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.droid.ashref.smartcontrol.DataList_MOOD;
import com.droid.ashref.smartcontrol.DataViewHolder_MOOD;
import com.droid.ashref.smartcontrol.Edit_Mood;
import com.droid.ashref.smartcontrol.MainActivity;
import com.droid.ashref.smartcontrol.R;
import com.droid.ashref.smartcontrol.SaveSettings;

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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerView_Adapter_MOOD extends RecyclerView.Adapter<DataViewHolder_MOOD> {


    private ArrayList<DataList_MOOD> arrayList;
    private static Context context;

    public RecyclerView_Adapter_MOOD() {
    }

    public RecyclerView_Adapter_MOOD(ArrayList<DataList_MOOD> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataViewHolder_MOOD onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_mood, viewGroup, false);
        return new DataViewHolder_MOOD(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder_MOOD dataViewHolder, int i) {

        final DataList_MOOD currentDatalist = arrayList.get(i);
        dataViewHolder.Mood.setText(currentDatalist.getName());

        dataViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getRootView().getContext())
                        .setMessage("Are you sure ?")
                     //   .setView(R.layout.add_room_dialog)
                        .setTitle("Mood Set ..")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                jsonTask_Set_Mood jsonTask_set_mood = new jsonTask_Set_Mood();
                                jsonTask_set_mood.execute(currentDatalist.getID());


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode

        String ModeName= pref.getString("ModeName", null);

        if (Objects.equals(ModeName, currentDatalist.Name))
        {
            dataViewHolder.Mood.setTextColor(context.getResources().getColor(R.color.green));

        }


        final CardView cardView = dataViewHolder.cardView;
        dataViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, cardView);
                //inflating menu from xml resource
                popup.inflate(R.menu.mood_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Add:
                               Add_Mood();
                                break;
                            case R.id.Edit:
                                Intent myintents = new Intent(context, Edit_Mood.class);
                                myintents.putExtra("ID", currentDatalist.getID());
                                myintents.putExtra("Name", currentDatalist.getName());
                                context.startActivity(myintents);
                                break;
                            case R.id.Delete:
                                JsonTask_Delete_Mood jsonTask_delete_mood = new JsonTask_Delete_Mood();
                                jsonTask_delete_mood.execute(currentDatalist.getID());
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


    public class JsonTask_Delete_Mood extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";

        @Override
        protected String doInBackground(String... strings) {
            try {


                url = new URL(SaveSettings.Url+"delete_Mood.php?Mood_ID=" + strings[0]);
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
            MainActivity.jsonTask_Mood task_mood = new MainActivity.jsonTask_Mood();
            task_mood.execute();
            Toast.makeText(context, aJsonString, Toast.LENGTH_LONG).show();
            //finish();
        }
    }

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }


    class jsonTask_Set_Mood extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private URL url;
        private HttpURLConnection httpURLConnection = null;
        private InputStream inputStream;
        private BufferedReader bufferedReader;
        private StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"setMood.php?User_ID=" + SaveSettings.UserID + "&Mood_ID=" + strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity.jsonTask jsonTask = new MainActivity.jsonTask();
            jsonTask.execute();

            MainActivity.jsonTask_Mood_Info jsonTaskMoodInfo = new MainActivity.jsonTask_Mood_Info();
            jsonTaskMoodInfo.execute();

            MainActivity.jsonTask_Mood jsonTask_Mood = new MainActivity.jsonTask_Mood();
            jsonTask_Mood.execute();

        }
    }

    public void Add_Mood() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
      //  LayoutInflater inflater = this.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        //LayoutInflater inflater = dialogBuilder.getLayoutInflater();
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
                JsonTask_Add_Mood jsonTask_add_mood = new JsonTask_Add_Mood();
                jsonTask_add_mood.execute(editText.getText().toString(), SaveSettings.UserID);
//                All_Rooms.JsonTask_Add_Room jsonTask_add_room = new All_Rooms.JsonTask_Add_Room();
//                jsonTask_add_room.execute(editText.getText().toString(), SaveSettings.UserID);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();




    }

    public static class JsonTask_Add_Mood extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";

        @Override
        protected String doInBackground(String... strings) {
            try {


                url = new URL(SaveSettings.Url+"insert_Mood.php?Mood_Name=" + strings[0] + "&User_ID=" + strings[1]);
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
            MainActivity.jsonTask_Mood jsonTask_mood = new MainActivity.jsonTask_Mood();
            jsonTask_mood.execute();
            Toast.makeText(context, aJsonString, Toast.LENGTH_LONG).show();
            //finish();
        }
    }
}



