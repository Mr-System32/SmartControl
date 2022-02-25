package com.droid.ashref.smartcontrol;


//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class DataViewHolder_MOOD extends RecyclerView.ViewHolder {
    public TextView Mood;
    public CardView cardView;
    public DataViewHolder_MOOD(@NonNull View itemView) {
        super(itemView);

        Mood = itemView.findViewById(R.id.TXT_Mood);
        cardView= itemView.findViewById(R.id.Mood_Card);
    }
}
