package com.example.igima.bookingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.igima.bookingapp.Model.Band;
import com.example.igima.bookingapp.Model.Request;
import com.example.igima.bookingapp.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ordered extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference requests;
    DatabaseReference bands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        bands = database.getReference("Bands");

        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(mAuth.getUid());
    }

    private void loadOrders(String id) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("userId").equalTo(id)
        ) {
            @Override
            protected void populateViewHolder(final OrderViewHolder viewHolder, Request model, int position) {

                final String[] band_name = new String[1];
                final String[] band_date = new String[1];
                bands.child(model.getBandId()).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Band band = dataSnapshot.getValue(Band.class);
                                band_name[0] = band.getName();
                                band_date[0] = band.getDate();
                                viewHolder.txtOrderedName.setText(band_name[0]);
                                viewHolder.txtOrderedDate.setText(band_date[0]);
                                viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(Ordered.this, qr.class));
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}