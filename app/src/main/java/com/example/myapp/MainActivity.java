package com.example.myapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mail, pass;
    private TextView loginpage;
    private Button signin, signup;
    private FirebaseAuth firebaseAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,WiFiDemo.class));
        }


        loginpage = (TextView) findViewById(R.id.txtlogin);
        mail = (EditText) findViewById(R.id.txtmail);
        pass = (EditText) findViewById(R.id.txtpassword);
        signin = (Button) findViewById(R.id.btnsignin);
        signup = (Button) findViewById(R.id.btnsignup);
        signin.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == signin) {
             log();
        }
        if (v == signup) {
            startActivity(new Intent(this, Signup.class));
            finish();
        }
    }
    private void log() {
        String email = mail.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(),WiFiDemo.class));
                            finish();
                        }else
                        Toast.makeText(getApplicationContext(), "please try again", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
