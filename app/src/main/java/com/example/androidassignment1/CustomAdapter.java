package com.example.androidassignment1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private List<User> userList;

    public CustomAdapter(List<User> ul) {
        this.userList = ul;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.account_info_layout, viewGroup, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        String nameString = "Name: <b>" + userList.get(i).name + "</b>";
        customViewHolder.tv_usr_name.setText(Html.fromHtml(nameString));
        String genderString = "Gender: <b>" + userList.get(i).gender + "</b>";
        customViewHolder.tv_usr_gender.setText(Html.fromHtml(genderString));
        String birthdateString = "Birthdate: <b>" + userList.get(i).birthdate + "</b>";
        customViewHolder.tv_usr_bday.setText(Html.fromHtml(birthdateString));
        String ageString = "Age: <b>" + userList.get(i).age + "</b>";
        customViewHolder.tv_usr_age.setText(Html.fromHtml(ageString));
        String countryString = "Country: <b>" + userList.get(i).country + "</b>";
        customViewHolder.tv_usr_ctry.setText(Html.fromHtml(countryString));
        if(!(userList.get(i).address).equals("")){
            String addressString = "Address: <b>" + userList.get(i).address + "</b>";
            customViewHolder.tv_usr_addr.setText(Html.fromHtml(addressString));
        }
        else{
            customViewHolder.tv_usr_addr.setVisibility(View.GONE);
        }
        Log.d("!#customAdapter:", "onBindViewHolder: userList.get(i).photouri -> " + userList.get(i).photouri);
        if(userList.get(i).photouri != null){
            Bitmap bitmap = BitmapFactory.decodeFile(userList.get(i).photouri);
            customViewHolder.iv_usr_pic.setImageBitmap(bitmap);
        }
        else{
            customViewHolder.iv_usr_pic.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }
}
