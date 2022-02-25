package com.droid.ashref.smartcontrol;

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
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private TextView AlredyHaveAnAccount;
    private TextView FullName;
    private TextView Email;
    private TextView Password;
    private TextView Repassword;
    private TextView PhoneNumber;
    private TextView Age;
    private Button SingupBTN;
    private ProgressBar progressBar;
    private FrameLayout ParentframeLayout;
    private String emailPattren = "[a-zA-z0-9._-]+@[a-z]+.[a-z]+";
    ProgressDialog loading;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        AlredyHaveAnAccount = view.findViewById(R.id.goto_sing_in);
        FullName = view.findViewById(R.id.sing_up_fullName);
        Email = view.findViewById(R.id.sing_up_email);
        Password = view.findViewById(R.id.sing_up_password);
        Repassword = view.findViewById(R.id.sing_up_repassword);
        PhoneNumber = view.findViewById(R.id.sing_up_phone);

        SingupBTN = view.findViewById(R.id.sing_up_btn);

        ParentframeLayout = Objects.requireNonNull(getActivity()).findViewById(R.id.register_framLayout);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);

        FullName.addTextChangedListener(new TextWatcher() {
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
        Repassword.addTextChangedListener(new TextWatcher() {
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
        PhoneNumber.addTextChangedListener(new TextWatcher() {
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


        AlredyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        SingupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chackEmailandPassword();
            }
        });
    }

    private void chackEmailandPassword() {

        if (Email.getText().toString().matches(emailPattren)) {
            if (Password.getText().toString().equals(Repassword.getText().toString())) {
                SingupBTN.setEnabled(false);
                SingupBTN.setTextColor(getResources().getColor(R.color.white));

                JsonTask_Singup jsonTask_Singup=new JsonTask_Singup();
                jsonTask_Singup.execute();

            } else {
                Repassword.setError("Password dose not mached !");
            }
        } else {
            Email.setError("Invalid Email !");
        }
    }

    private void checkInput() {
//        if (!TextUtils.isEmpty(Email.getText())) {
//            if (!TextUtils.isEmpty(FullName.getText())) {
//                if (!TextUtils.isEmpty(Password.getText()) && (Password.length() >= 8)) {
//                    if (!TextUtils.isEmpty(Repassword.getText())) {
//                        if (!TextUtils.isEmpty(PhoneNumber.getText())) {
//                            if (!TextUtils.isEmpty(Age.getText())) {
//                                SingupBTN.setEnabled(true);
//                                SingupBTN.setTextColor(getResources().getColor(R.color.white));
//                            }else {
//                                    SingupBTN.setEnabled(false);
//                                    SingupBTN.setTextColor(getResources().getColor(R.color.customeblue));
//
//                                }
//                        } else {
//                            SingupBTN.setEnabled(false);
//                            SingupBTN.setTextColor(getResources().getColor(R.color.customeblue));
//                        }
//
//                    } else {
//                        SingupBTN.setEnabled(false);
//                        SingupBTN.setTextColor(getResources().getColor(R.color.customeblue));
//                    }
//
//
//                } else {
//                    SingupBTN.setEnabled(false);
//                    SingupBTN.setTextColor(getResources().getColor(R.color.customeblue));
//                }
//
//
//            } else {
//                SingupBTN.setEnabled(false);
//                SingupBTN.setTextColor(getResources().getColor(R.color.customeblue));
//            }
//
//
//        } else {
//            SingupBTN.setEnabled(false);
//            SingupBTN.setTextColor(getResources().getColor(R.color.customeblue));
//
//        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);

        fragmentTransaction.replace(ParentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    public class JsonTask_Singup extends AsyncTask<String, String, String> {
        URL url;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        String aJsonString = "non";



        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(getContext(),
                    "Loading ..", "Please wait...", true, true);
        }
        @Override
        protected String doInBackground(String... strings) {
            try {



                url = new URL(SaveSettings.Url+"register.php?UserName=" + FullName.getText().toString() + "&Password=" + Password.getText().toString()+ "&Email=" + Email.getText().toString()+ "&Phone=" + PhoneNumber.getText().toString());
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

                if (jsonObject != null) {
                    //successful ajax call
                    //successful ajax call
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    if (success == 1) {


                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                        startActivity(mainIntent);
                        getActivity().finish();

                    } else {
                        //ajax error
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                    }

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
//            Toast.makeText(getApplicationContext(), aJsonString, Toast.LENGTH_LONG).show();
//            finish();
            loading.dismiss();
            SingupBTN.setEnabled(true);

        }
    }

}
