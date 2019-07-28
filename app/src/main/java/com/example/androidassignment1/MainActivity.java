package com.example.androidassignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_to_create_accnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // recognize button by id
        btn_to_create_accnt = findViewById(R.id.btn_create_accnt);
        // set onClickListener for clicking button
        btn_to_create_accnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //triggers CreateAccount intent when button clicked
                Intent intent = new Intent(getBaseContext(), CreateAccount.class);
                //move to CreateAccount class for login
                startActivity(intent);
            }
        });
    }
}
