package com.example.igima.bookingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.igima.bookingapp.Common.Common;
import com.example.igima.bookingapp.Model.Request;
import com.example.igima.bookingapp.ViewHolder.TicketViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tickets extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,TicketViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listTickets);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getId());
    }

    private void loadOrders(String id) {
        adapter = new FirebaseRecyclerAdapter<Request, TicketViewHolder>(
                Request.class,
                R.layout.ticket_layout,
                TicketViewHolder.class,
                requests.orderByChild("phone").equalTo(id)//TODO:Change to id
        ) {
            @Override
            protected void populateViewHolder(TicketViewHolder viewHolder, Request model, int position) {
                viewHolder.ticket.setText(adapter.getRef(position).getKey());

            }
        };
        recyclerView.setAdapter(adapter);
    }
}
