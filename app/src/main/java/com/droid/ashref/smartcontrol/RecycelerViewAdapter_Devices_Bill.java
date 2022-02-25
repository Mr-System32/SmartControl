package com.droid.ashref.smartcontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycelerViewAdapter_Devices_Bill extends RecyclerView.Adapter<DataViewHolder_Devices_Bill> {

    private ArrayList<DataList> arrayList;
    private Context context;


    public RecycelerViewAdapter_Devices_Bill(ArrayList<DataList> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public DataViewHolder_Devices_Bill onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DataViewHolder_Devices_Bill(LayoutInflater.from(context).inflate(R.layout.row_devices_bill, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder_Devices_Bill dataViewHolder, int i) {
        final DataList currentDatalist = arrayList.get(i);
        dataViewHolder.Name.setText(currentDatalist.getName());

        // هنا لازم نخلي العملية الرياضية مال حساب السعر
        dataViewHolder.Price.setText("$"+currentDatalist.getPower());
        Picasso.get()
                .load(currentDatalist.getImage())
                .placeholder(R.drawable.addphoto)
                .error(R.drawable.addphoto)
                .into(dataViewHolder.imageView
                );


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }


}

