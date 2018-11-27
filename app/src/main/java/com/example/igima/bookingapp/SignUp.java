package com.example.igima.bookingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.igima.bookingapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    private EditText editUsername, editName, editPassword, editNif,
            editCardType, editCardNumber, editCardValidity;
    private Button btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editUsername = findViewById(R.id.editUsername);
        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);
        editNif = findViewById(R.id.editNif);
        editCardType = findViewById(R.id.editCardType);
        editCardNumber = findViewById(R.id.editCardNumber);
        editCardValidity = findViewById(R.id.editCardValidity);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = editUsername.getText().toString();
                final String name = editName.getText().toString();
                final String password = editPassword.getText().toString();
                final String nif = editNif.getText().toString();
                final String c_num = editCardNumber.getText().toString();
                final String c_type = editCardType.getText().toString();
                final String c_val = editCardValidity.getText().toString();

                if (username.isEmpty() || name.isEmpty() || password.isEmpty() || nif.isEmpty() || c_num.isEmpty() || c_type.isEmpty() || c_val.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill in all the gaps.", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.content_home);

                } else {
                    CreateUser(username, password, name, nif, c_num, c_type, c_val);
                }
            }
        });
    }

    private void CreateUser(final String username, final String password, final String name, final String nif, final String c_num, final String c_type, final String c_val) {
        mAuth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(username, name, password, nif, c_num, c_type, c_val);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUp.this, "Account created." , Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this, SignIn.class));
                                    }else{
                                        Toast.makeText(SignUp.this, "Please try again.." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(SignUp.this, "Please try again.." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}