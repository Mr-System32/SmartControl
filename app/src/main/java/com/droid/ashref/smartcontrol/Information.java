package com.droid.ashref.smartcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Information extends Activity {
    private TextView Name, N_Hours, Invoice, Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;
//        int higth = dm.heightPixels;
//        getWindow().setLayout((int) (width * .8), (int) (higth * .5));

        Name = findViewById(R.id.D_Name);
        N_Hours = findViewById(R.id.N_Hours);
        Invoice = findViewById(R.id.Invoice);
        Status = findViewById(R.id.Status);
        Bundle a = getIntent().getExtras(); // load the notifications
        assert a != null;

        String a1 = a.getString("Hours");
        String a2 = a.getString("Name");

        N_Hours.setText(a1);
        Name.setText(a2);
    }
}
