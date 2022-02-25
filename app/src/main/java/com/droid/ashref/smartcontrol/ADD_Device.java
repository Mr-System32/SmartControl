package com.droid.ashref.smartcontrol;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class ADD_Device extends AppCompatActivity {

    EditText Name, Power, Port;
    TextView textView;
    Switch Status;
    ImageView ImgDevice,IMG_Back;
    Button button;
    String status = "";
    String message = "";
    String fullfile;
    String a1, a2, a3, a4, a5, a6;
    private Bitmap bitmap;
    private final int IMG_REQUEST = 1;
    public static final String UPLOAD_URL = SaveSettings.Url+"insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__device);
        Name = findViewById(R.id.TXT_name);
        Power = findViewById(R.id.TXT_power);
        Port = findViewById(R.id.TXT_port);
        IMG_Back = findViewById(R.id.IMG_Back);
        Status = findViewById(R.id.Status);
        ImgDevice = findViewById(R.id.D_IMG);
        button = findViewById(R.id.BTN_ADD);

        IMG_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ImgDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooesPhoto();
            }
        });

        Bundle a = getIntent().getExtras(); // load the notifications
        if (a != null) {
            a1 = a.getString("Update");
            a2 = a.getString("ID");
            a3 = a.getString("Name");
            a4 = a.getString("Power");
            a5 = a.getString("Port");
            a6 = a.getString("Status");

            if (a1.equals("Update")) {
              //  ImgDevice.setVisibility(View.GONE);
                Name.setText(a3);
                Power.setText(a4);
                Port.setText(a5);

                button.setText("UPDATE");
                if (a6.equals("1"))
                    Status.setChecked(true);
                else
                    Status.setChecked(false);
            }
        }
    }
     //  BTN  Add Method
    public void ADD(View view) {

        if (a1.equals("Update")) {

            JsonTask_UPDATE jsonTask = new JsonTask_UPDATE();
            jsonTask.execute();

        } else if (a1.equals("Add")) {
            UploadDevice();
        }
    }
     // Update Method
    public class JsonTask_UPDATE extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            try {

                if (Status.isChecked()) {
                    status = "1";
                } else {
                    status = "0";
                }

                url = new URL(SaveSettings.Url+"update.php?ID=" + a2 + "&D_Name=" + Name.getText().toString() + "&D_status=" + status + "&D_Power=" + Power.getText().toString() + "&D_Port=" + Port.getText().toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

                fullfile = stringBuffer.toString();

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
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK
                && null != data) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImgDevice.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        return encodedImage;
    }
     //Uplaoade Method
    private void UploadDevice() {
        class UploadDevice extends AsyncTask<Bitmap, Void, String> {
            ProgressDialog loading;
            HttpRequest rh = new HttpRequest();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ADD_Device.this,
                        "Uploading Device", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @SuppressLint("WrongThread")
            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                if (Status.isChecked()) {
                    status = "1";
                } else {
                    status = "0";
                }
                HashMap<String, String> data = new HashMap<>();
                data.put("D_Name", Name.getText().toString());
                data.put("D_status", status);
                data.put("D_Power", Power.getText().toString());
                data.put("D_Port", Port.getText().toString());
                data.put("User_ID", SaveSettings.UserID);
                data.put("D_Photo", uploadImage);

                String result = rh.postRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadDevice ui = new UploadDevice();
        ui.execute(bitmap);
    }
    // Picture Gallery
    private void ChooesPhoto() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, IMG_REQUEST);
    }

}
