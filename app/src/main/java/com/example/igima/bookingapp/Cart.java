package com.example.igima.bookingapp;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.igima.bookingapp.Common.Common;
import com.example.igima.bookingapp.Database.Database;
import com.example.igima.bookingapp.Model.Order;
import com.example.igima.bookingapp.Model.Request;
import com.example.igima.bookingapp.ViewHolder.CartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//TODO: Add working delete button
public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotalPrice;
    Button btnPlace;

    FirebaseDatabase database;
    DatabaseReference requests;
    DatabaseReference bands;

    List<Order> cart =  new ArrayList<>();
    CartAdapter adapter;

    DataSnapshot dataSnapshot;

    private ImageView imageView;
    private TextView ticket_id;
    private EditText editUsername;
    String id_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests =  database.getReference("Requests");
        bands = database.getReference("Bands");

        //Init
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.total);
        editUsername = (EditText) findViewById(R.id.editUsername);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        //QR image
        imageView = findViewById(R.id.imageView);
        loadListBand();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Are you sure?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (Order order : cart) {
                    Request request = new Request(
                            Common.currentUser.getId(),
                            order.getProductId(),
                            true);
                    //Submit to firebase
                    requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                    new Database(getBaseContext()).cleanCart();
                }
                Toast.makeText(Cart.this, "Thank you, your purchase has been successful.", Toast.LENGTH_SHORT).show();

                //Creation of QR code
                ticket_id = (TextView) findViewById(R.id.ticket_id);
                id_ticket = ticket_id.getText().toString()+dataSnapshot.child(editUsername.getText().toString());
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
                ////////
                finish();
            }
        });
        alertDialog.setNegativeButton("Something went wrong, try again.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListBand() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calcutate total price
        int total = 0;

        for(Order order:cart)
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }
}
