package com.example.igima.bookingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igima.bookingapp.Database.Database;
import com.example.igima.bookingapp.Model.Band;
import com.example.igima.bookingapp.Model.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class qr extends AppCompatActivity {

    private ImageView imageView;
    private TextView ticket_id, dates, tickets;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference reference;
    private FirebaseDatabase database;
    DatabaseReference requests;
    DatabaseReference bands;

    String id_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        Bundle b = getIntent().getExtras();
        final String[] recieved_ticket_id = new String[1];
        if(b != null)
            recieved_ticket_id[0] = b.getString("ticket_id");

        final String uid = user.getUid();
        imageView = (ImageView) findViewById(R.id.qr_imageView);
        ticket_id = (TextView) findViewById(R.id.qr_ticket_id);
        dates = (TextView) findViewById(R.id.qr_date);
        tickets = (TextView) findViewById(R.id.qr_num_tickets);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");
        bands = database.getReference("Bands");

        //Get Request_ID
        //Log.i(TELECOM_SERVICE, "U S U A R I O : "+ user.getUid());




        requests.child(recieved_ticket_id[0]).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Request request = dataSnapshot.getValue(Request.class);
                        tickets.setText(String.valueOf(request.getTickets()));
                        bands.child(request.getBandId()).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Band band = dataSnapshot.getValue(Band.class);
                                        ticket_id.setText(band.getName());
                                        dates.setText(band.getDate());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {}
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });


        //id_ticket = "Me la paso genial programando, simplemente me encanta";
        //Creation of QR code
        if (recieved_ticket_id != null) {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(recieved_ticket_id[0], BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

}
