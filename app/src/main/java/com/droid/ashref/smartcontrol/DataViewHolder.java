package com.droid.ashref.smartcontrol;

//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class DataViewHolder extends RecyclerView.ViewHolder {
    TextView Name,Power,Port;
    CardView cardView;
    ImageView imageView;
    ImageView Status;
    public DataViewHolder(@NonNull View itemView) {
        super(itemView);
        Name = itemView.findViewById(R.id.D_Name);
        Port = itemView.findViewById(R.id.D_Port);
        Power = itemView.findViewById(R.id.D_Power);
        Status = itemView.findViewById(R.id.IMG_status);
        cardView= itemView.findViewById(R.id.row_card);
        imageView= itemView.findViewById(R.id.D_Image);
    }
}
