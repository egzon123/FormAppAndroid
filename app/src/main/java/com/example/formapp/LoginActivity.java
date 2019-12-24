package com.example.formapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail,userPassword;
    Button btnSignIn,btnRegister;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().setTitle("Sign In");

        printAllData();
        userEmail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);

        btnSignIn.setOnClickListener((View v) -> {
            if(!validateUserCredentials(userEmail.getText().toString(),userPassword.getText().toString()) ){
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(LoginActivity.this,com.example.formapp.WelcomeActivity.class);
                intent.putExtra("userEmail",userEmail.getText().toString());
                startActivity(intent);
            }

        });

        btnRegister.setOnClickListener((View v) -> {
           Intent intent = new Intent(LoginActivity.this,com.example.formapp.UserRegisterActivity.class);
           startActivity(intent);
        });
    }


    public void printAllData(){
         pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
        Map<String, ?> prefsMap = pref.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            Log.v("SharedPreferences", entry.getKey() + ":" +
                    entry.getValue().toString());
        }
    }


    private boolean validateUserCredentials(String email,String password){
        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
        Map<String, ?   > prefsMap = pref.getAll();
        User user = null;
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
           String userDetails = entry.getValue().toString();
            Gson g = new Gson();
            user = g.fromJson(userDetails, User.class);
          if(user.getEmail().equals(email) && user.getPassword().equals(password)){
              return true;
          }
        }
        return false;
    }

}
