package com.droid.ashref.smartcontrol;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataViewHolder_IN_ROOM extends RecyclerView.ViewHolder {


    TextView IN_Room;
    Button Delete;

    public DataViewHolder_IN_ROOM(@NonNull View itemView) {
        super(itemView);

        IN_Room = itemView.findViewById(R.id.d_Name3);
        Delete = itemView.findViewById(R.id.Send_To_OUT);

    }
}
