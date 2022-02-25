package com.droid.ashref.smartcontrol;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DataViewHolder_Devices_Bill extends RecyclerView.ViewHolder {
    TextView Name,Price;
    CardView cardView;
    ImageView imageView;
    public DataViewHolder_Devices_Bill(@NonNull View itemView) {
        super(itemView);
        Name = itemView.findViewById(R.id.TXT_Name_devices_Bill);
        Price = itemView.findViewById(R.id.TXT_Price_devices_Bill);
        cardView= itemView.findViewById(R.id.Card_D_Bill);
        imageView= itemView.findViewById(R.id.IMG_devices_Bill);
    }


}
