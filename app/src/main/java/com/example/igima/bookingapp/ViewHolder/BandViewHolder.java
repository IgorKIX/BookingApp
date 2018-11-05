package com.example.igima.bookingapp.ViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.igima.bookingapp.Interface.ItemClickListener;
import com.example.igima.bookingapp.R;

public class BandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView band_name;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

        band_name = (TextView) itemView.findViewById(R.id.band_name);

        itemView.setOnClickListener(this);
    }

    public BandViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
