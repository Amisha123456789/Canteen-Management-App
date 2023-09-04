package com.example.canteenapp.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.canteenapp.AdminComponent.AdminPanel;
import com.example.canteenapp.MainActivity;
import com.example.canteenapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CanteenRegistration extends AppCompatActivity
{
    TextView canteenemail, canteenphone, canteenfullname, canteenpassword;
    Button canteenbutton;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_registration);
        canteenemail = findViewById(R.id.CanteenEmail);
        canteenphone = findViewById(R.id.CanteenPhone);
        canteenfullname = findViewById(R.id.CanteenFullname);
        canteenpassword = findViewById(R.id.CanteenPassword);
        canteenbutton = findViewById(R.id.Canteensignup);
        progressBar = findViewById(R.id.progressBarReg);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);
        canteenbutton.setOnClickListener(view -> {
            final String Email = canteenemail.getText().toString().trim();
            final String Phone = canteenphone.getText().toString().trim();
            final String Fullname = canteenfullname.getText().toString().trim();
            final String Password = canteenpassword.getText().toString().trim();
            if (TextUtils.isEmpty(Email)) {
                Toast.makeText(CanteenRegistration.this, "Email is missing", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Phone)) {
                Toast.makeText(CanteenRegistration.this, "Phone is missing", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Phone.length() < 10) {
                Toast.makeText(CanteenRegistration.this, "Phone number should be greater than 10 digit", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Fullname)) {
                Toast.makeText(CanteenRegistration.this, "Name is missing", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(Password)) {
                Toast.makeText(CanteenRegistration.this, "Password is missing", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Password.length() < 8) {
                Toast.makeText(CanteenRegistration.this, "Password should be or greater than 8 digit", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            if (Password.length() >= 8) {
                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(CanteenRegistration.this, task -> {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(CanteenRegistration.this, "User Added Successfully",
                                        Toast.LENGTH_SHORT).show();
                                // Sign in success, update UI with the signed-in user's information

                                Canteen canteen = new Canteen(
                                        Email,
                                        Fullname,
                                        Phone,
                                        Password

                                );
                                FirebaseDatabase.getInstance().getReference("CanteenStaff").
                                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(canteen).addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Toast.makeText(CanteenRegistration.this, "success",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(CanteenRegistration.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        });
            } else {
                Toast.makeText(CanteenRegistration.this, "We are updating", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
        startActivity(intent);
    }
}

