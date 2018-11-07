package com.example.igima.bookingapp;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.igima.bookingapp.Model.Band;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BandDetail extends AppCompatActivity {

    TextView band_name, band_price, band_description;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    NumberPicker numberPicker;

    String bandId="";

    FirebaseDatabase database;
    DatabaseReference bands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        bands = database.getReference("Bands");

        //init view
        numberPicker = (NumberPicker) findViewById(R.id.number_button);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(4);

        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        band_description = (TextView) findViewById(R.id.band_description);
        band_name = (TextView) findViewById(R.id.band_name);
        band_price = (TextView) findViewById(R.id.band_price);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);

        //Get bandId from intent
        if(getIntent() != null)
            bandId = getIntent().getStringExtra("BandId");
        if(!bandId.isEmpty()){
            getDetailBand(bandId);
        }
    }

    private void getDetailBand(String bandId) {
        bands.child(bandId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Band band = dataSnapshot.getValue(Band.class);

                collapsingToolbarLayout.setTitle(band.getName());

                band_price.setText(band.getPrice());
                band_name.setText(band.getName());
                band_description.setText(band.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}