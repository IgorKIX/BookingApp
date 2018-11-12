package com.example.igima.bookingapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.igima.bookingapp.Interface.ItemClickListener;
import com.example.igima.bookingapp.R;

public class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView ticket;

    private ItemClickListener itemClickListener;

    public TicketViewHolder(@NonNull View itemView) {
        super(itemView);

        ticket = (TextView) itemView.findViewById(R.id.ticket_id);

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
