package com.example.formapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

//coment add
public class UserRegisterActivity extends AppCompatActivity {

    EditText emri,mbiemri,email,password,confirm_password;
    Button btnRegister;
    TextView date;

    Button selectDate;
    DatePickerDialog datePickerDialog;
    int yearD;
    int monthD;
    int dayOfMonthD;
    SharedPreferences pref;
    SharedPreferences.Editor prefsEditor;


    Calendar calendar;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        this.getSupportActionBar().setTitle("Register");
        emri=  findViewById(R.id.etEmri);
        mbiemri = findViewById(R.id.etMbiemri);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirmPassword);
        selectDate = findViewById(R.id.btnDate);
        date = findViewById(R.id.tvSelectedDate);
        btnRegister = findViewById(R.id.btnRegister);


        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                yearD = calendar.get(Calendar.YEAR);
                monthD = calendar.get(Calendar.MONTH);
                dayOfMonthD = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(UserRegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                                setYearD(year);
                                setMonthD(month+1);
                                setDayOfMonthD(day);
                            }
                        }, yearD, monthD, monthD);

                datePickerDialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidEmail(email.getText().toString())){
                    Toast.makeText(UserRegisterActivity.this,"Please enter valid email format",Toast.LENGTH_SHORT).show();
                }else if(!matchPws(password.getText().toString(),confirm_password.getText().toString())){
                    Toast.makeText(UserRegisterActivity.this,"Passwords dont match",Toast.LENGTH_SHORT).show();
                }else{
                    int selectedId = radioSexGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioSexButton = (RadioButton) findViewById(selectedId);
                    System.out.println("Registred :  emri: "+emri.getText().toString()+", mbiemri: "+mbiemri.getText().toString()+" ,email: "+email.getText().toString()+", birthday: "+getYearD()+"\\"+getMonthD()+"\\"+getDayOfMonthD()+
                            ", gender:"+ radioSexButton.getText()  +" password: "+password.getText().toString()+" ,confirmpsw: "+confirm_password.getText().toString());

                    User user= new User();
                    user.setName(emri.getText().toString());
                    user.setSurname(mbiemri.getText().toString());
                    user.setEmail(email.getText().toString());
                    String bD = getDayOfMonthD()+"/"+getMonthD()+"/"+getYearD();
                    Date date = null;
                    try {
                        date=new SimpleDateFormat("dd/MM/yyyy").parse(bD);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    user.setBirthdate(date);
                    user.setGender(radioSexButton.getText().charAt(0));
                    user.setPassword(password.getText().toString());
                    if(ifEmailExists(email.getText().toString())){
                        Toast.makeText(UserRegisterActivity.this, "Email Exists", Toast.LENGTH_SHORT).show();
                    }else{
                        userRegisterSharedP(user);
                    }


                }
            }
        });

    }

    private void userRegisterSharedP(User user){
         pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);

         prefsEditor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        String uniqueID = UUID.randomUUID().toString();
        prefsEditor.putString(uniqueID, json);
        boolean isRegistredSuccessful = prefsEditor.commit();
        if(isRegistredSuccessful){
            Toast.makeText(this, "Registred Successfuly", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserRegisterActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Not Registerd", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean ifEmailExists(String email){
        pref = getApplicationContext().getSharedPreferences("userData", MODE_PRIVATE);
        Map<String, ?> prefsMap = pref.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            String userDetails = entry.getValue().toString();
            if(userDetails.contains(email)){
                return true;
            }else{
                continue;
            }
        }
        return false;
    }




    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean matchPws(String p1,String p2){
        return p1.equals(p2);
    }


    public void setYearD(int yearD) {
        this.yearD = yearD;
    }

    public void setMonthD(int monthD) {
        this.monthD = monthD;
    }

    public void setDayOfMonthD(int dayOfMonthD) {
        this.dayOfMonthD = dayOfMonthD;
    }

    public int getYearD() {
        return yearD;
    }

    public int getMonthD() {
        return monthD;
    }

    public int getDayOfMonthD() {
        return dayOfMonthD;
    }
}
