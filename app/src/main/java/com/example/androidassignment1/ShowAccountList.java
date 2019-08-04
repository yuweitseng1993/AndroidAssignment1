package com.example.androidassignment1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowAccountList extends AppCompatActivity {
    private final int PHOTO_REQUEST_CODE = 404;
    TextView tv_title;
    RecyclerView recyclerView;
    CustomAdapter adapter;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_account);

        tv_title = findViewById(R.id.tv_create_accnt_title);
        Intent intent = getIntent();
        tv_title.setText("Welcome " + intent.getStringExtra("user_name") + " !");


        recyclerView = findViewById(R.id.rv_list_task);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        userList = loadAccount();
        Log.d("DEBUG", "database: " + Arrays.toString(userList.toArray()));
        adapter = new CustomAdapter(userList);
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST_CODE) {
//            if (!checked_large_size) {
//                if (data != null) {
//                    Bundle samplePhoto = data.getExtras();
//                    //we get the small version of the picture.
//                    Bitmap rawData = (Bitmap) samplePhoto.get("data");
//                    iv_photo.setImageBitmap(rawData);
//                }
//            } else {
//                //if the intent contains the path for the image, generated for the Provider.
//                Log.d(TAG, "onActivityResult: currentPhotoPath " + currentPhotoPath);
//                Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
////              Glide.with(this).load(currentPhotoPath).into(iv_photo);
//                iv_photo.setImageBitmap(bitmap);
//            }
//        }
//    }

    public List<User> loadAccount(){
        AccountDatabase database = new AccountDatabase(this);
        SQLiteDatabase readableDB = database.getReadableDatabase();
        Cursor cursor = readableDB.query(DatabaseUtil.AccountTable.tableName, null, null, null, null, null, null, null);
        List<User> newSet = new ArrayList<>();
        while(cursor.moveToNext()){
            String usrName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.nameColumn));
            String usrGender = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.genderColumn));
            String usrBday = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.birthdateColumn));
            String usrCtry = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.countryColumn));
            String usrAddr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.addressColumn));
            String usrPic = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.photouriColumn));
            int usrAge = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.ageColumn));
            int itemPosition = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseUtil.AccountTable.accountNum));
            newSet.add(new User(usrName, usrGender, usrBday, usrAge, usrCtry, usrAddr, itemPosition, usrPic));
        }
        readableDB.close();
        return newSet;
    }
}
