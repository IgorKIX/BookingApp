package com.example.igima.bookingapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igima.bookingapp.Interface.ItemClickListener;
import com.example.igima.bookingapp.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrderedName;
    public TextView txtOrderedDate;
    public LinearLayout linearLayout;
    public CardView cardView;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtOrderedName = (TextView) itemView.findViewById(R.id.order_name);
        txtOrderedDate = (TextView) itemView.findViewById(R.id.order_date);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.bandNameBox);
        cardView = (CardView) itemView.findViewById(R.id.cardBand);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
