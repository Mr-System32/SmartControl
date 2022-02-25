package com.droid.ashref.smartcontrol;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class DataViewHolder_OUT_MOOD extends RecyclerView.ViewHolder {
    TextView OUT_Mood;
    Button Insert;

    public DataViewHolder_OUT_MOOD(@NonNull View itemView) {
        super(itemView);

        OUT_Mood = itemView.findViewById(R.id.d_Name2);
        Insert = itemView.findViewById(R.id.Send_To_IN);
    }
}
