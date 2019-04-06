package com.example.myapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Signout extends AppCompatActivity implements View.OnClickListener {
      private DatabaseReference myRef ;
      Button signout;
      TextView detail,temp;
      FirebaseDatabase firebaseDatabase;
      public static final String TAG="ViewDatabase";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);
        signout=(Button)findViewById(R.id.btnsignout);
        detail=(TextView)findViewById(R.id.txtDetail);
        temp=(TextView)findViewById(R.id.txttemp);
        signout.setOnClickListener(this);
        Intent intent = getIntent();
        String SS = intent.getStringExtra("SSID");
        SS.toUpperCase();
        String MACAD = intent.getStringExtra("MACAD");
        MACAD.toUpperCase();
        detail.setText("SSID : " + SS + "\n" + "MAC : " + MACAD);
        detail.setTextSize(14);
        temp.setVerticalScrollBarEnabled(true);
        temp.setScroller(new Scroller(this));
        temp.setTextSize(14);
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference().child(MACAD);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    String value = x.getValue(String.class);
                    temp.setText(value);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                temp.setText("ERROR FOUND");
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v==signout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}
