package com.example.igima.bookingapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igima.bookingapp.Interface.ItemClickListener;
import com.example.igima.bookingapp.R;

public class QRViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtIDTicket;
    public TextView txtDate;
    public TextView txtNoTickets;
    public ImageView imgQR;
    public LinearLayout linearLayout;

    private ItemClickListener itemClickListener;

    public QRViewHolder(@NonNull View itemView) {
        super(itemView);

        txtIDTicket = (TextView) itemView.findViewById(R.id.qr_ticket_id);
        txtDate = (TextView) itemView.findViewById(R.id.qr_date);
        txtNoTickets = (TextView) itemView.findViewById(R.id.qr_num_tickets);
        imgQR = (ImageView) itemView.findViewById(R.id.qr_imageView);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.qr_layout);

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
