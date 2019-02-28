package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Signout extends AppCompatActivity implements View.OnClickListener {
      Button signout;
      TextView bye;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);
        signout=(Button)findViewById(R.id.btnsignout);
        bye=(TextView)findViewById(R.id.txtbye);
        signout.setOnClickListener(this);

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
