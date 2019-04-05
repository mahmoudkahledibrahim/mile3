package com.example.myapp;

import android.content.Intent;
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

public class Signout extends AppCompatActivity implements View.OnClickListener {
      private DatabaseReference myRef ;
      Button signout,temp;
      TextView detail;
      public static final String TAG="ViewDatabase";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);
        signout=(Button)findViewById(R.id.btnsignout);
        temp=(Button)findViewById(R.id.btntemp);
        detail=(TextView)findViewById(R.id.txtDetail);
        signout.setOnClickListener(this);
        temp.setOnClickListener(this);
        Intent intent = getIntent();
        String SS = intent.getStringExtra("SSID");
        String MACAD = intent.getStringExtra("MACAD");
        detail.setText("SSID : " + SS + "\n" + "MAC : " + MACAD);
        detail.setMovementMethod(new ScrollingMovementMethod());
        detail.setTextSize(12);



    }

    @Override
    public void onClick(View v) {
        if(v==signout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        if(v==temp){
            myRef=FirebaseDatabase.getInstance().getReference().child("60:01:94:74:46:3A");

        }myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class).toString();
                detail.setText(value);
                //detail.append("\n");
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
