package com.example.androidassignment1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    ImageView iv_usr_pic;
    TextView tv_usr_name, tv_usr_gender, tv_usr_bday, tv_usr_age, tv_usr_ctry, tv_usr_addr;

    public CustomViewHolder(@NonNull View accntView) {
        super(accntView);
        iv_usr_pic = accntView.findViewById(R.id.iv_usr_photo);
        tv_usr_name = accntView.findViewById(R.id.tv_name);
        tv_usr_gender = accntView.findViewById(R.id.tv_gender);
        tv_usr_bday = accntView.findViewById(R.id.tv_birthday);
        tv_usr_age = accntView.findViewById(R.id.tv_age);
        tv_usr_ctry = accntView.findViewById(R.id.tv_country);
        tv_usr_addr = accntView.findViewById(R.id.tv_address);
    }
}
