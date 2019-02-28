package com.example.myapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    private EditText username,email,password,cpassword;
    private Button signup;
    private FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        firebaseAuth= FirebaseAuth.getInstance();
        username=(EditText) findViewById(R.id.etxt_username);
        email=(EditText) findViewById(R.id.etxt_email);
        password=(EditText) findViewById(R.id.etxt_pass);
        cpassword=(EditText) findViewById(R.id.etxt_cpass);
        signup=(Button)findViewById(R.id.btn_signup);
        signup.setOnClickListener(this);
    }

    public void onClick(View v) {
        register();
    }
    private void register() {
        String user= username.getText().toString().trim();
        String mail= email.getText().toString().trim();
        String pass= password.getText().toString().trim();
        String cpass= cpassword.getText().toString().trim();
        if(TextUtils.isEmpty(mail)){
            Toast.makeText(this,"please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(user)){
            Toast.makeText(this,"please enter username",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(cpass)){
            Toast.makeText(this,"please confirm password",Toast.LENGTH_LONG).show();
            return;
        }
        if(!(cpass.equals(pass))){
            Toast.makeText(this,"error in password confirmation",Toast.LENGTH_LONG).show();
            return;
        }
        if(pass.length()<6){
            Toast.makeText(this,"password is short",Toast.LENGTH_LONG).show();
            return;
        }
        else
        firebaseAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this,"Registerd succesfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),WiFiDemo.class));
                        }
                        else
                            Toast.makeText(Signup.this,"could not register .. try agian",Toast.LENGTH_LONG).show();

                    }
                });
         return;
    }
}
