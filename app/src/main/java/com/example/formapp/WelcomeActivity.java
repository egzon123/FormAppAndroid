package com.example.formapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.example.formapp.SqliteHelper.TABLE_USERS;

public class WelcomeActivity extends AppCompatActivity {
//    SharedPreferences pref;
    TextView name,surname,email,gender,birthdate;
    SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.getSupportActionBar().setTitle("Welcome");
        initView();
        String userEmail = getIntent().getStringExtra("userEmail");
        sqliteHelper = new SqliteHelper(this);
        User logedUser = sqliteHelper.retriveUserByEmail(userEmail);

        name.setText("Name : "+logedUser.getName());
        surname.setText("Surname : "+logedUser.getSurname());
        email.setText("Email : "+logedUser.getEmail());
        gender.setText("Gender : "+logedUser.getGender());
        birthdate.setText("Birthdate : "+logedUser.getBirthdate());




    }

    private void initView(){
        name = findViewById(R.id.etWname);
        surname = findViewById(R.id.etWsurname);
        email = findViewById(R.id.etWemail);
        gender = findViewById(R.id.etWgender);
        birthdate = findViewById(R.id.etWbirthdate);
    }



//    public User retriveUserByEmail(String email){
//        Gson g = new Gson();
//        User user = null;
//
//        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//        Map<String, ?> prefsMap = pref.getAll();
//        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
//            String userDetails = entry.getValue().toString();
//            if(userDetails.contains(email)){
//                user = g.fromJson(userDetails,User.class);
//            }else{
//                continue;
//            }
//        }
//        return user;
//    }

}

