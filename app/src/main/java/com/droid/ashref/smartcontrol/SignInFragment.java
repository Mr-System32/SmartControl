package com.droid.ashref.smartcontrol;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private TextView dontHaveAnAccount;
    private TextView Email;
    private TextView Password;
    private Button SinginBTN;
    private FrameLayout ParentframeLayout;
    private String emailPattren = "[a-zA-z0-9._-]+@[a-z]+.[a-z]+";
    ProgressDialog loading;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        dontHaveAnAccount = view.findViewById(R.id.goto_sing_up);
        Email =  view.findViewById(R.id.sign_in_Email);
        Password = view.findViewById(R.id.sign_in_password);
        SinginBTN = view.findViewById(R.id.sing_in_btn);
        ParentframeLayout = getActivity().findViewById(R.id.register_framLayout);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        SinginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chackEmailandPassword();

            }
        });

    }

    private void checkInput() {
//        if (!TextUtils.isEmpty(Email.getText())) {
//            if (!TextUtils.isEmpty(Password.getText()) && (Password.length() >= 8)) {
//
//                SinginBTN.setEnabled(true);
//                SinginBTN.setTextColor(getResources().getColor(R.color.white)) ;
//
//            } else {
//                SinginBTN.setEnabled(false);
//                SinginBTN.setTextColor(getResources().getColor(R.color.customeblue));
//            }
//
//        } else {
//            SinginBTN.setEnabled(false);
//            SinginBTN.setTextColor(getResources().getColor(R.color.customeblue));
//        }


    }

    private void chackEmailandPassword() {

        if (Email.getText().toString().matches(emailPattren)) {
            if (Password.length()>=1) {
                SinginBTN.setEnabled(false);
                SinginBTN.setTextColor(getResources().getColor(R.color.customeblue));

                JsonTask_Singin jsonTask=new JsonTask_Singin();
                jsonTask.execute();

            } else {
                SinginBTN.setEnabled(true);
                SinginBTN.setTextColor(getResources().getColor(R.color.white ));
                Password.setError("Incorrect Password !");
            }
        } else {
            SinginBTN.setEnabled(true);
            SinginBTN.setTextColor(getResources().getColor(R.color.white));
            Email.setError("Incorrect Email !");
        }
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);

        fragmentTransaction.replace(ParentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    @SuppressLint("StaticFieldLeak")
    public class JsonTask_Singin extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getContext(),
                    "Loading ..", "Please wait...", true, true);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {


                url = new URL(SaveSettings.Url+"login.php?Email=" + Email .getText().toString() + "&Password=" + Password.getText().toString());
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = " ";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);

                }

              String  fullfile = stringBuffer.toString();

              //  JSONArray jsonArray = new JSONArray(fullfile);

                JSONObject jsonObject=new JSONObject(fullfile);

                //successful ajax call
                //successful ajax call
                String UserID = jsonObject.getString("User_ID");
                String UserName = jsonObject.getString("UserName");
                String UserEmail = jsonObject.getString("Email");
                String UserPhoto = jsonObject.getString("Photo");
                if (!UserID.equals("0") ) {
                    SaveSettings.UserID = UserID;
                    SaveSettings.UserName = String.valueOf(UserName);
                    SaveSettings.UserEmail = String.valueOf(UserEmail);
                    SaveSettings.UserPhoto = String.valueOf(UserPhoto);
                    SaveSettings sv = new SaveSettings(Objects.requireNonNull(getActivity()));
                    sv.SaveData();
                    setFragment(new S_Type());

                } else {
                    //ajax error
                    Toast.makeText(getActivity(), "noooo", Toast.LENGTH_LONG).show();

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
            loading.dismiss();
            SinginBTN.setEnabled(true);
            SinginBTN.setTextColor(getResources().getColor(R.color.white));

        }
    }
}
