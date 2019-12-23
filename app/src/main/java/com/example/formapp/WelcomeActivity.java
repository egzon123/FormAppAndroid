package com.example.formapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    SharedPreferences pref;
    TextView name,surname,email,gender,birthdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getSupportActionBar().setTitle("Welcome");
        String userEmail = getIntent().getStringExtra("userEmail");
        name = findViewById(R.id.etWname);
        surname = findViewById(R.id.etWsurname);
        email = findViewById(R.id.etWemail);
        gender = findViewById(R.id.etWgender);
        birthdate = findViewById(R.id.etWbirthdate);

        User logedUser = retriveUserByEmail(userEmail);

        name.setText("Name : "+logedUser.getName());
        surname.setText("Surname : "+logedUser.getSurname());
        email.setText("Email : "+logedUser.getEmail());
        gender.setText("Gender : "+logedUser.getGender());
        Date date = logedUser.getBirthdate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String birthdateString = formatter.format(date);
        birthdate.setText("Birthdate : "+birthdateString);




    }

    public User retriveUserByEmail(String email){
        Gson g = new Gson();
        User user = null;

        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
        Map<String, ?> prefsMap = pref.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            String userDetails = entry.getValue().toString();
            if(userDetails.contains(email)){
                user = g.fromJson(userDetails,User.class);
            }else{
                continue;
            }
        }
        return user;
    }

}

