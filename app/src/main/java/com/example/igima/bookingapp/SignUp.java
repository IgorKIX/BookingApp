package com.example.igima.bookingapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igima.bookingapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText editUsername, editName, editPassword, editNif,
            editCardType, editCardNumber, editCardValidity;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName = (EditText) findViewById(R.id.editName);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editNif = (EditText) findViewById(R.id.editNif);
        editCardType = (EditText) findViewById(R.id.editCardType);
        editCardNumber = (EditText) findViewById(R.id.editCardNumber);
        editCardValidity = (EditText) findViewById(R.id.editCardValidity);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //Init Firebase
        final FirebaseDatabase dataBase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = dataBase.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if already exist
                        if(dataSnapshot.hasChild(editUsername.getText().toString())){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Username already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            mDialog.dismiss();
                            User user = new User(
                                    editUsername.getText().toString(),
                                    editName.getText().toString(),
                                    editPassword.getText().toString(),
                                    editNif.getText().toString(),
                                    editCardNumber.getText().toString(),
                                    editCardType.getText().toString(),
                                    editCardValidity.getText().toString());
                            table_user.child(editUsername.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign up succesfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
