package com.droid.ashref.smartcontrol;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecycelerViewAdapter extends RecyclerView.Adapter<DataViewHolder> {

    private ArrayList<DataList> arrayList;
    private Context context;


    public RecycelerViewAdapter(ArrayList<DataList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DataViewHolder(LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder dataViewHolder, int i) {
        final DataList currentDatalist = arrayList.get(i);

        dataViewHolder.Name.setText(currentDatalist.getName());
        dataViewHolder.Power.setText(currentDatalist.getPower() + " Watt");
        dataViewHolder.Port.setText(currentDatalist.getPort());
        Picasso.get()
                .load(currentDatalist.getImage())
                .placeholder(R.drawable.addphoto)
                .error(R.drawable.addphoto)
                .into(dataViewHolder.imageView);
        if (currentDatalist.getStatus().equals("1"))
            dataViewHolder.Status.setImageResource(R.drawable.power);
        else if (currentDatalist.getStatus().equals("0"))
            dataViewHolder.Status.setImageResource(R.drawable.power_off);

        dataViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintents = new Intent(context, Modified.class);
                myintents.putExtra("Update", "Update");
                myintents.putExtra("ID", currentDatalist.getID());
                myintents.putExtra("Name", currentDatalist.getName());
                myintents.putExtra("Power", currentDatalist.getPower());
                myintents.putExtra("Port", currentDatalist.getPort());
                myintents.putExtra("Status", currentDatalist.getStatus());
                myintents.putExtra("Hours", currentDatalist.getHours());
                context.startActivity(myintents);
            }
        });
        dataViewHolder.Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentDatalist.getStatus().equals("0")) {
                    JsonTask_Update_Status jsonTask = new JsonTask_Update_Status();
                    jsonTask.execute(currentDatalist.getID(), "1");
                    dataViewHolder.Status.setImageResource(R.drawable.power);
                }
                if (currentDatalist.getStatus().equals("1")) {
                    JsonTask_Update_Status jsonTask = new JsonTask_Update_Status();
                    jsonTask.execute(currentDatalist.getID(), "0");
                    dataViewHolder.Status.setImageResource(R.drawable.power_off);
                }

            }
        });


//        dataViewHolder.lottieAnimationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(dataViewHolder.lottieAnimationView.isAnimating()){
//                    dataViewHolder.lottieAnimationView.pauseAnimation();
//                }else {
//                    dataViewHolder.lottieAnimationView.playAnimation();
//                }
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class JsonTask_Update_Status extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";

        @Override
        protected String doInBackground(String... strings) {
            try {


                url = new URL(SaveSettings.Url+"update_Status.php?ID=" + strings[0] + "&D_status=" + strings[1]);
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

            Toast.makeText(context, aJsonString, Toast.LENGTH_LONG).show();
            //finish();
        }
    }


    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }


}




