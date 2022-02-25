package com.droid.ashref.smartcontrol;

import android.os.Bundle;

import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        frameLayout = findViewById(R.id.register_framLayout);
        setFragment(new SignInFragment());

        Bundle a = getIntent().getExtras(); // load the notifications
        if (a != null) {
         String   a1 = a.getString("S_Type");

            if (a1.equals("1")){
                setFragment(new S_Type());

            }
        }


    }

    private void setFragment(Fragment fragment ) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }


}
