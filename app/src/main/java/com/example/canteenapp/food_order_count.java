package com.example.canteenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.canteenapp.AdminComponent.AdminPanel;
import com.example.canteenapp.UserComponent.UserPanel.FoodName;
import com.example.canteenapp.constant.FireBaseConstant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class food_order_count extends AppCompatActivity {
    RecyclerView recyclerView;
//    Button reset;
//    DatabaseReference database, database2;

    FirebaseDatabase firebaseDatabase;
    countadapter countadapter;
    ArrayList list, fname;
    Foodcount foodcount;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_count);
        recyclerView=findViewById(R.id.foodlist);

//        reset=findViewById(R.id.countreset);
        // database = firebaseDatabase.getReference(FireBaseConstant.ORDER_COUNT);

        DatabaseReference database= FirebaseDatabase.getInstance().getReference("OrderCount");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        fname=new ArrayList<>();
        list.clear();
        countadapter=new countadapter(this,list,fname);
        recyclerView.setAdapter(countadapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    foodcount=dataSnapshot.getValue(Foodcount.class);
                    //Foodcount foodcount=dataSnapshot.getValue(Foodcount.class);
                    Log.d("counted count", String.valueOf(foodcount));
                    fname.add(dataSnapshot.getKey());

                    list.add(foodcount);
                }

                countadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//





    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Intent intent = new Intent(getApplicationContext(), KitchenPanel.class);
        startActivity(intent);

    }
}