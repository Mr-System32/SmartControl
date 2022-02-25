package com.droid.ashref.smartcontrol;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
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

public class Modified extends Activity {
    String a1, a2, a3, a4, a5, a6,a7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int higth = dm.heightPixels;
        getWindow().setLayout((int) (width * .4), (int) (higth * .4));


        Bundle a = getIntent().getExtras(); // load the notifications
        assert a != null;
        a1 = a.getString("Update");
        a2 = a.getString("ID");
        a3 = a.getString("Name");
        a4 = a.getString("Power");
        a5 = a.getString("Port");
        a6 = a.getString("Status");
        a7 = a.getString("Hours");


    }

    public void BTN_INFORMATION(View view) {

        Intent intent = new Intent(Modified.this, Information.class);
        intent.putExtra("Hours", a7);
        intent.putExtra("Name", a3);
        startActivity(intent);
        finish();
    }

    public void BTN_UPDATE(View view) {


        Intent myintents = new Intent(this, ADD_Device.class);

        myintents.putExtra("Update", a1);
        myintents.putExtra("ID", a2);
        myintents.putExtra("Name", a3);
        myintents.putExtra("Power", a4);
        myintents.putExtra("Port", a5);
        myintents.putExtra("Status", a6);

        this.startActivity(myintents);
        finish();

    }

    public void BTN_DELETE(View view) {
        JsonTask_DELETE jsonTask = new JsonTask_DELETE();
        jsonTask.execute();

    }


    public class JsonTask_DELETE extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";
        ProgressDialog progressDoalog;
        @Override
        protected void onPreExecute() {
             progressDoalog = new ProgressDialog(Modified.this);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("يرجى الانتظار ... ");
            progressDoalog.setTitle("جاري الحذف ");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                progressDoalog.onStart();

                url = new URL(SaveSettings.Url+"delete.php?ID="+a2);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

             String   fullfile = stringBuffer.toString();

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

            Toast.makeText(getApplicationContext(), aJsonString, Toast.LENGTH_LONG).show();
            progressDoalog.dismiss();
            finish();
//            MainActivity mainActivity=new MainActivity();
//            mainActivity.Refresh();


        }
    }
}
