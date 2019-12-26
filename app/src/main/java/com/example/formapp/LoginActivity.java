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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail,userPassword;
    Button btnSignIn,btnRegister;
//    SharedPreferences pref;
SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().setTitle("Sign In");
        sqliteHelper = new SqliteHelper(this);
//        printAllData();
            initViews();
        sqliteHelper.printData();
        btnSignIn.setOnClickListener((View v) -> {
            if(validate()){
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                User currentUser = sqliteHelper.authenticate(new User(null,null,null,email,password,null,null));

                if(currentUser != null){
                    Toast.makeText(this, "Successfuly loged In!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                intent.putExtra("userEmail",currentUser.getEmail());
                startActivity(intent);
            }else{
                    Toast.makeText(this, "Failed to log in , please try again", Toast.LENGTH_SHORT).show();
            }
            }
        });

        btnRegister.setOnClickListener((View v) -> {
           Intent intent = new Intent(LoginActivity.this,com.example.formapp.UserRegisterActivity.class);
           startActivity(intent);
        });
    }


//    public void printAllData(){
//         pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//        Map<String, ?> prefsMap = pref.getAll();
//        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
//            Log.v("SharedPreferences", entry.getKey() + ":" +
//                    entry.getValue().toString());
//        }
//    }


//    private boolean validateUserCredentialsPrf(String email,String password){
//        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
//        Map<String, ?   > prefsMap = pref.getAll();
//        User user = null;
//        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
//           String userDetails = entry.getValue().toString();
//            Gson g = new Gson();
//            user = g.fromJson(userDetails, User.class);
//          if(user.getEmail().equals(email) && user.getPassword().equals(password)){
//              return true;
//          }
//        }
//        return false;


    private void initViews(){
        userEmail = findViewById(R.id.username);
        userPassword = findViewById(R.id.password);
        btnSignIn = findViewById(R.id.login);
        btnRegister = findViewById(R.id.register);
    }

        private boolean validate(){
        boolean valid = false;
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                valid = false;
                Toast.makeText(this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();
            } else {
                valid = true;

            }
            if(password.isEmpty()){
                valid = false;
                Toast.makeText(this, "Please enter a valid password!", Toast.LENGTH_SHORT).show();
            }
            return valid;
        }

}
