package com.example.igima.bookingapp;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igima.bookingapp.Database.Database;
import com.example.igima.bookingapp.Model.Band;
import com.example.igima.bookingapp.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BandDetail extends AppCompatActivity {

    TextView band_name, band_price, band_description, date;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    NumberPicker numberPicker;

    String bandId="";

    FirebaseDatabase database;
    DatabaseReference bands;

    Band currentBand;

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

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        bandId,
                        currentBand.getName(),
                        Integer.toString(numberPicker.getValue()),
                        currentBand.getPrice(),
                        currentBand.getDiscount(),
                        currentBand.getDate()
                ));
                Toast.makeText(BandDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        band_description = (TextView) findViewById(R.id.band_description);
        band_name = (TextView) findViewById(R.id.band_name);
        band_price = (TextView) findViewById(R.id.band_price);
        date = (TextView) findViewById(R.id.date);

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
                currentBand = dataSnapshot.getValue(Band.class);

                collapsingToolbarLayout.setTitle(currentBand.getName());

                band_price.setText(currentBand.getPrice());
                band_name.setText(currentBand.getName());
                band_description.setText(currentBand.getDescription());
                date.setText(currentBand.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
