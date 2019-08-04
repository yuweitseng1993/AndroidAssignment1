package com.example.androidassignment1;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class EnterUserInfo extends AppCompatActivity {
    EditText et_name, et_u_name, et_usr_pswd, et_bir_pick, et_age, et_addr;
    Button btn_sv, btn_take_photo;
    RadioGroup rad_gp;
    RadioButton rad_btn;
    Spinner spin_country;
    ImageView iv_photo;

    String gender, country, address;
    DatePickerDialog datePickerDialog;
    int year, picked_year, month, picked_month, dayOfMonth, picked_dayOfMonth;
    Calendar calendar;
    private final int PHOTO_REQUEST_CODE = 404;
    String currentPhotoPath;
    private static final String TAG = "EnterUserInfo";

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
        btn_take_photo = findViewById(R.id.btn_chg_photo);
        rad_gp = findViewById(R.id.rg_gender);
        iv_photo = findViewById(R.id.iv_user_photo);
        picked_year = picked_month = picked_dayOfMonth = 0;
        gender = address = "none";

        String[] country_array = new String[] {"Not-Specified", "Malaysia", "United States",
                "Indonesia", "France", "Italy", "Singapore", "New Zealand", "India"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, country_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_country.setAdapter(adapter);

        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_take_photo clicked");
//                takePhoto();
                dispatchTakePictureIntent();
            }
        });

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
                    String err_mes = "One or more required fields is empty";
                    Toast.makeText(getApplicationContext(), err_mes, Toast.LENGTH_SHORT).show();
                }
                else{
                    int age = parseInt(et_age.getText().toString());
                    String bday = picked_month + "/" + picked_dayOfMonth + "/" + picked_year;
                    addAccount(name, gender, bday, age, country, address, currentPhotoPath);

                    Intent intent = new Intent(getBaseContext(), ShowAccountList.class);
                    intent.putExtra("user_name", name);
                    startActivity(intent);
                    //todo: transition animation
                }
            }
        });

    }

    public void checkButton(View view) {
        int radio_picked = rad_gp.getCheckedRadioButtonId();
        rad_btn = findViewById(radio_picked);
        gender = rad_btn.getText().toString();
    }

    public void addAccount(String name, String gender, String birthdate, int age, String country, String address, String photouri){
        AccountDatabase database = new AccountDatabase(this);
        SQLiteDatabase saveData = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseUtil.AccountTable.nameColumn, name);
        values.put(DatabaseUtil.AccountTable.genderColumn, gender);
        values.put(DatabaseUtil.AccountTable.birthdateColumn, birthdate);
        values.put(DatabaseUtil.AccountTable.ageColumn, age);
        values.put(DatabaseUtil.AccountTable.countryColumn, country);
        values.put(DatabaseUtil.AccountTable.addressColumn, address);
        values.put(DatabaseUtil.AccountTable.photouriColumn, photouri);

        if(saveData.insert(DatabaseUtil.AccountTable.tableName, null, values) > 0){
            Toast.makeText(this, "Account added successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "ERROR: unable to add account to databaase", Toast.LENGTH_SHORT).show();
        }
        saveData.close();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        Log.d(TAG, "createImageFile: currentPhotoPath -> " + currentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = getCameraIntent();
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.androidassignment1.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PHOTO_REQUEST_CODE);
            }
        }
    }

    public Intent getCameraIntent() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: currentPhotoPath " + currentPhotoPath);
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            iv_photo.setImageBitmap(bitmap);
        }
    }
}
