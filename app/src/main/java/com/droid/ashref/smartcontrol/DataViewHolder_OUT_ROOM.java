package com.droid.ashref.smartcontrol;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataViewHolder_OUT_ROOM  extends RecyclerView.ViewHolder {
    TextView OUT_Room;
    Button Insert;

    public DataViewHolder_OUT_ROOM(@NonNull View itemView) {
        super(itemView);

        OUT_Room = itemView.findViewById(R.id.d_Name2);
        Insert = itemView.findViewById(R.id.Send_To_IN);
    }
}
