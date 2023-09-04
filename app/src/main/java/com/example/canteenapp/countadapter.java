package com.example.canteenapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenapp.Registration.User;
import com.example.canteenapp.UserComponent.UserPanel.FoodName;
import com.example.canteenapp.constant.FireBaseConstant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class countadapter extends RecyclerView.Adapter<countadapter.MyViewHolder> {

    Context context;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference2, databaseReference;
    Foodcount fooodcount;

    ArrayList list,fname;

    boolean check;
    int i=0;


    public countadapter(Context context, ArrayList list, ArrayList fname) {
        this.context = context;
        this.list = list;
        this.fname=fname;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.foodcount,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Foodcount foodcount= (Foodcount) list.get(position);
        holder.Foodname.setText(String.valueOf(fname.get(position)));
        holder.Foodcount.setText(String.valueOf(foodcount.getCount()));


        i++;
        holder.llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodtempp= holder.Foodname.getText().toString();
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.countresetfragment);
                Button buttonyes,buttonno;
                TextView textchangecount;
                textchangecount=dialog.findViewById(R.id.textchangecount);

                textchangecount.setText("Do you really wish to reset the count for "+ foodtempp);
                buttonyes=dialog.findViewById(R.id.buttonyes);
                buttonno=dialog.findViewById(R.id.buttonno);
                buttonyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.Foodcount.setText("0");
                        String count="0";
                        Foodcount foodcount=new Foodcount(foodtempp,count);
                        list.set(position,foodcount);
                        Log.d("countted red", String.valueOf(position));
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference=firebaseDatabase.getReference(FireBaseConstant.ORDER_COUNT);
                        databaseReference.child(String.valueOf(foodtempp.toString().trim())).child("count").setValue("0".toString());

                        notifyItemChanged(position);
//                        notifyDataSetChanged();
                        holder.setIsRecyclable(false);
                        dialog.dismiss();
                    }
                });

                buttonno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Foodname,Foodcount;
        LinearLayout llayout;
        ImageView deletecount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Foodname=itemView.findViewById(R.id.countFood);
            Foodcount=itemView.findViewById(R.id.countFoodCount);
            llayout=itemView.findViewById(R.id.llayout);
            deletecount=itemView.findViewById(R.id.deletecount);

        }
    }
}
