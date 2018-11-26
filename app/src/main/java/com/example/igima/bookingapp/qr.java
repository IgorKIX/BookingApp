package com.example.igima.bookingapp;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igima.bookingapp.Database.Database;
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

    String id_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        final String uid = user.getUid();
        imageView = (ImageView) findViewById(R.id.qr_imageView);
        ticket_id = (TextView) findViewById(R.id.qr_ticket_id);
        dates = (TextView) findViewById(R.id.qr_date);
        tickets = (TextView) findViewById(R.id.qr_num_tickets);
        database = FirebaseDatabase.getInstance();
        reference =  database.getReference();

        //Get Request_ID
        Log.i(TELECOM_SERVICE, "U S U A R I O : "+ user.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String tabla = userSnapshot.getKey();
                    Log.i(TELECOM_SERVICE, "T A B L A : "+ tabla);
                    if (tabla.equals("Requests")) {
                        for (DataSnapshot usrSnapshot : userSnapshot.getChildren()) {
                            String _ticket_id = usrSnapshot.getKey();
                            Log.i(TELECOM_SERVICE, "T I C K E T  I D : "+ _ticket_id);
                            if (_ticket_id.equals(uid)) {
                                Request rqs = new Request();
                                ticket_id.setText(_ticket_id);
                                dates.setText(rqs.getDates());
                                tickets.setText(rqs.getTickets());
                            }
                        }
                    }
                }
                //id_ticket = ticket_id.getText().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        id_ticket = "Me la paso genial programando, simplemente me encanta";
        //Creation of QR code
        if (id_ticket != null) {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(id_ticket, BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

}
