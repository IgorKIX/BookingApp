package com.example.igima.bookingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.igima.bookingapp.Interface.ItemClickListener;
import com.example.igima.bookingapp.Model.Band;
import com.example.igima.bookingapp.ViewHolder.BandViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BandList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference bandList;

    String categoryId="";

    FirebaseRecyclerAdapter<Band,BandViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        bandList = database.getReference("Bands");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_band);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get intent
        if(getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId != null){
            loadListBand(categoryId);
        }
    }

    private void loadListBand(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Band, BandViewHolder>(Band.class,
                R.layout.band_item,
                BandViewHolder.class,
                bandList.orderByChild("MenuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(BandViewHolder viewHolder, Band model, int position) {
                viewHolder.band_name.setText(model.getName());

                final Band local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent bandDetail = new Intent(BandList.this,BandDetail.class);
                        //Sending bandId to new activity
                        bandDetail.putExtra("BandId",adapter.getRef(position).getKey());
                        startActivity(bandDetail);
                    }
                });
            }
        };
        //Set adapter
        recyclerView.setAdapter(adapter);
    }
}
