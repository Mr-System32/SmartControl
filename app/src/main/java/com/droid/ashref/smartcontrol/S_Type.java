package com.droid.ashref.smartcontrol;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class S_Type extends Fragment {

    private CardView Trade, Residential, governmental, agricultural;
    private ImageView IMG_RES, IMG_TRA, IMG_GOV, IMG_AGR;
    private TextView TXT_RES,TXT_TRA,TXT_GOV, TXT_AGR;

    public S_Type() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_s__type, container, false);
        SaveSettings saveSettings = new SaveSettings(Objects.requireNonNull(getContext()));
        saveSettings.LoadData();
        Residential = view.findViewById(R.id.Residential);
        Trade = view.findViewById(R.id.Trade);
        governmental = view.findViewById(R.id.governmental);
        agricultural = view.findViewById(R.id.agriculteral);
        IMG_RES = view.findViewById(R.id.IMG_Res);
        IMG_TRA = view.findViewById(R.id.IMG_Tra);
        IMG_GOV = view.findViewById(R.id.IMG_Gov);
        IMG_AGR = view.findViewById(R.id.IMG_Agri);
        TXT_AGR= view.findViewById(R.id.TXT_AGR);
        TXT_GOV= view.findViewById(R.id.TXT_GOV);
        TXT_RES= view.findViewById(R.id.TXT_RES);
        TXT_TRA= view.findViewById(R.id.TXT_TRA);

        switch (SaveSettings.S_Type) {
            case "Trade":
                IMG_TRA.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_TRA.setTextColor(getResources().getColor(R.color.green));
                break;
            case "governmental":
                IMG_GOV.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_GOV.setTextColor(getResources().getColor(R.color.green));
                break;
            case "agricultural":
                IMG_AGR.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_AGR.setTextColor(getResources().getColor(R.color.green));
                break;
            case "Residential":
                IMG_RES.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_RES.setTextColor(getResources().getColor(R.color.green));
                break;

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        Residential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMG_RES.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_RES.setTextColor(getResources().getColor(R.color.green));

                set_Type("Residential");


            }
        });
        governmental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                IMG_GOV.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_GOV.setTextColor(getResources().getColor(R.color.green));

                set_Type("governmental");
            }

        });
        agricultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IMG_AGR.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_AGR.setTextColor(getResources().getColor(R.color.green));

                set_Type("agricultural");
            }
        });

        Trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMG_TRA.setBackgroundResource(R.drawable.sevenxxhdpi);
                TXT_TRA.setTextColor(getResources().getColor(R.color.green));

                set_Type("Trade");
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }


    private void set_Type(String type) {
        SaveSettings.S_Type = type;
        SaveSettings sv = new SaveSettings(Objects.requireNonNull(getActivity()));
        sv.SaveData();

        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();
    }
}
