package com.droid.ashref.smartcontrol;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class DataViewHolder_Room  extends RecyclerView.ViewHolder{

    public TextView Room;
    public CardView cardView;
    public DataViewHolder_Room(@NonNull View itemView) {
        super(itemView);

        Room = itemView.findViewById(R.id.TXT_Room);
        cardView= itemView.findViewById(R.id.Room_Card);
    }
}
