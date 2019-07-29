package com.example.androidassignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class EnterUserInfo extends AppCompatActivity {
    EditText et_name, et_u_name, et_usr_pswd, et_bir_pick, et_age, et_addr;
    Button btn_sv;
    RadioGroup rad_gp;
    RadioButton rad_btn;
    Spinner spin_country;

    String gender, country, address;
    DatePickerDialog datePickerDialog;
    int year, picked_year, month, picked_month, dayOfMonth, picked_dayOfMonth;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_user_info);

        et_name = findViewById(R.id.et_usr_name);
        et_u_name = findViewById(R.id.et_usr_username);
        et_usr_pswd = findViewById(R.id.et_pswd);
        et_age = findViewById(R.id.et_usr_age);
        et_addr= findViewById(R.id.et_postal_addr);
        et_bir_pick = findViewById(R.id.et_datepicker);
        spin_country = findViewById(R.id.sp_country);
        btn_sv = findViewById(R.id.btn_save);
        rad_gp = findViewById(R.id.rg_gender);
        picked_year = picked_month = picked_dayOfMonth = 0;
        gender = address = "none";

        String[] country_array = new String[] {"Not-Specified", "Malaysia", "United States",
                "Indonesia", "France", "Italy", "Singapore", "New Zealand", "India"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, country_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_country.setAdapter(adapter);


        et_bir_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(EnterUserInfo.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                picked_year = year;
                                picked_month = month;
                                picked_dayOfMonth = day;
                                et_bir_pick.setText(month+"/"+day+"/"+year);
//                                Log.i("user_input", "year: " + year + " month: " + month + " day: " + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        btn_sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean has_empty_field = false;
                String name = et_name.getText().toString();
                String username = et_u_name.getText().toString();
                String password = et_usr_pswd.getText().toString();
                String age_str = et_age.getText().toString();
                country = spin_country.getSelectedItem().toString();
                address = et_addr.getText().toString();

                if(name.length() == 0 || username.length() == 0 || password.length() == 0 || age_str.length() == 0){
                    has_empty_field = true;
                }
                //check if "birth date", "gender", and "country" are specified
                if(gender.equals("none") || country.equals("Not-Specified")){
                    has_empty_field = true;
                }
                if(picked_year == 0 || picked_month == 0 || picked_dayOfMonth == 0){
                    has_empty_field = true;
                }

                if(has_empty_field){
                    //Toast to warn empty
                    String err_mes = "One or more empty fields exists";
                    Toast.makeText(getApplicationContext(), err_mes, Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast to display user inputs
                    int age = parseInt(et_age.getText().toString());
                    String message = "name: " + name + "\nusername: " + username + "\npassword: " + password + "\nage: " + age +
                            "\nbirth date: " + picked_month + "/" + picked_dayOfMonth + "/" + picked_year + "\ncountry: " + country +
                            "\ngender: " + gender;
                    Log.i("user_input", message);
                    if(address.equals("")){
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String message_addr = message + "\naddress: " + address;
                        Toast.makeText(getApplicationContext(), message_addr, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void checkButton(View view) {
        int radio_picked = rad_gp.getCheckedRadioButtonId();
        rad_btn = findViewById(radio_picked);
        gender = rad_btn.getText().toString();
    }
}
