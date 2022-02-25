package com.droid.ashref.smartcontrol;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class DataViewHolder_IN_MOOD extends RecyclerView.ViewHolder {
    TextView IN_Mood;
    Button Delete;

    public DataViewHolder_IN_MOOD(@NonNull View itemView) {
        super(itemView);

        IN_Mood = itemView.findViewById(R.id.d_Name3);
        Delete = itemView.findViewById(R.id.Send_To_OUT);

    }
}
