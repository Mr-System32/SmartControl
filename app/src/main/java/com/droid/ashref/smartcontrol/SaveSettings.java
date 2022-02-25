package com.droid.ashref.smartcontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;



import java.util.Objects;

public class SaveSettings {

    private Context context;
    private SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs3" ;
    static String UserID = "0";
    static String S_Type = "";
    static String UserName = "";
    static String UserPhoto = "";
    static String UserEmail = "";
    static String Url = "https://dentalflutter.000webhostapp.com/";

    public SaveSettings(Context context) {
        this.context=context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    public void SaveData()  {

        try

        {

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("UserID",String.valueOf(UserID));
            editor.putString("S_Type",String.valueOf(S_Type));
            editor.putString("UserName",String.valueOf(UserName));
            editor.putString("UserPhoto",String.valueOf(UserPhoto));
            editor.putString("UserEmail",String.valueOf(UserEmail));

            editor.apply();
            LoadData();
        }

        catch( Exception ignored){}
    }
    public   void LoadData( ) {

        String TempUserID=sharedpreferences.getString("UserID","empty");
        String Temp_S_Type=sharedpreferences.getString("S_Type","empty");
        String TempUserName=sharedpreferences.getString("UserName","empty");
        String TempUserPhoto=sharedpreferences.getString("UserPhoto","empty");
        String TempUserEmail=sharedpreferences.getString("UserEmail","empty");

        if(!Objects.equals(TempUserName, "empty") && !Objects.equals(TempUserPhoto, "empty") &&!Objects.equals(TempUserEmail, "empty")   ){
            UserEmail=TempUserEmail;
            UserPhoto=TempUserPhoto;
            UserName=TempUserName;
        }

        if(!Objects.equals(Temp_S_Type, "empty")    ){
            S_Type=Temp_S_Type;
        }
        else {
            Intent intent=new Intent(context,S_Type.class);
            context.startActivity(intent);
        }

        if(!Objects.equals(TempUserID, "empty")){
            UserID=TempUserID;
        }
        else {
            Intent intent=new Intent(context,MainActivity.class);
            context.startActivity(intent);
        }

    }
}
