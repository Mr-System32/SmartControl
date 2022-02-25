package com.droid.ashref.smartcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class Recyclerview_IN_Adapter_MOOD extends RecyclerView.Adapter<DataViewHolder_IN_MOOD> {


    private ArrayList<DataLiset_OUT_IN> arrayList;
    private Context context;

    public Recyclerview_IN_Adapter_MOOD() {
    }

    public Recyclerview_IN_Adapter_MOOD(ArrayList<DataLiset_OUT_IN> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataViewHolder_IN_MOOD onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_mood_in_device, viewGroup, false);
        return new DataViewHolder_IN_MOOD(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder_IN_MOOD dataViewHolder, int i) {

        final DataLiset_OUT_IN currentDatalist = arrayList.get(i);
        dataViewHolder.IN_Mood.setText(currentDatalist.getName());
        dataViewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getRootView().getContext())
                        .setMessage("Are you sure ?")
                        .setTitle("Delete Device ..")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                JsonTask_delete jsonTask_delete=new JsonTask_delete();
                                jsonTask_delete.execute(currentDatalist.getID(),Edit_Mood.MoodID);

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

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }

    public static class JsonTask_delete extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //   Recyclerview_OUT_Adapter_MOOD.clear();
        }

        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL(SaveSettings.Url+"delete_Mood_Device.php?Device_ID="+ strings[0]+"&User_ID=" + SaveSettings.UserID+"&Mood_ID="+strings[1]);
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
            Edit_Mood.jsonTask_OUT_Mood jsonTask = new Edit_Mood.jsonTask_OUT_Mood() ;
            jsonTask.execute();

            Edit_Mood.jsonTask_IN_Mood jsonTask2 = new Edit_Mood.jsonTask_IN_Mood() ;
            jsonTask2.execute();


        }
    }
}