package com.example.androidassignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity {

    EditText et_email_addr, et_crt_pswrd, et_rpt_pswrd;
    Button btn_nxt;
    RelativeLayout rl_invalid_email_fmt, rl_nonmatch_pswd_err;
    TextView tv_up_err_mes, tv_btm_err_mes;
    boolean upper_err, bottom_err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        et_email_addr = findViewById(R.id.et_email_addr);
        et_crt_pswrd = findViewById(R.id.et_create_pswrd);
        et_rpt_pswrd = findViewById(R.id.et_repeat_pswrd);
        btn_nxt = findViewById(R.id.btn_crt_accnt_next);

        rl_invalid_email_fmt = findViewById(R.id.rl_invalid_email);
        rl_invalid_email_fmt.setVisibility(View.GONE);
        rl_nonmatch_pswd_err = findViewById(R.id.rl_unmatch_pswd);
        rl_nonmatch_pswd_err.setVisibility(View.GONE);

        upper_err = false;
        bottom_err = false;
        final Drawable tick = getResources().getDrawable(R.drawable.tick);

        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upper_err){
                    rl_invalid_email_fmt.setVisibility(View.GONE);
                    upper_err = false;
                }
                if(bottom_err){
                    rl_nonmatch_pswd_err.setVisibility(View.GONE);
                    bottom_err = false;
                }
                String usr_email = et_email_addr.getText().toString();
                String usr_pswd = et_crt_pswrd.getText().toString();
                String usr_rpt_pswd = et_rpt_pswrd.getText().toString();

                //filter empty user input
                if(usr_email.length() == 0 || usr_pswd.length() == 0 || usr_rpt_pswd.length() == 0){
                    //ERROR: all fields should be filled
                    rl_nonmatch_pswd_err.setVisibility(View.VISIBLE);
                    tv_btm_err_mes = findViewById(R.id.tv_pswd_error_mes);
                    tv_btm_err_mes.setText("One or more fields is empty");
                    // -------- change later: should detect which field is filled, keep it --------
                    et_email_addr.setText("");
                    et_crt_pswrd.setText("");
                    et_rpt_pswrd.setText("");
                    bottom_err = true;
                }
                else{
                    //verify user input
                    if(validate_email_addr(usr_email)){
                        if(ifEmailExisted(usr_email)){
                            rl_invalid_email_fmt.setVisibility(View.VISIBLE);
                            et_email_addr.setBackgroundResource(R.drawable.popup_erret_border);
                            tv_up_err_mes = findViewById(R.id.tv_email_error_mes);
                            tv_up_err_mes.setText(R.string.tv_txt_err_email_existed);
                            upper_err = true;
                            et_email_addr.setText("");
                            //validates password
                            validate_pswd(usr_pswd, usr_rpt_pswd);
                        }
                        else{
                            //Green boarder for email EditText + green tick
                            addEmail(usr_email);
                            et_email_addr.setCompoundDrawablesWithIntrinsicBounds(null, null, tick, null);
                            et_email_addr.setBackgroundResource(R.drawable.popup_cormes_border);
                            //validates password
                            validate_pswd(usr_pswd, usr_rpt_pswd);
                        }
                    }
                    else{
                        //ERROR: invalid email address
                        rl_invalid_email_fmt.setVisibility(View.VISIBLE);
                        et_email_addr.setBackgroundResource(R.drawable.popup_erret_border);
                        tv_up_err_mes = findViewById(R.id.tv_email_error_mes);
                        tv_up_err_mes.setText("Invalid email format");
                        upper_err = true;

                        //validates password
                        validate_pswd(usr_pswd, usr_rpt_pswd);
                        et_email_addr.setText("");
                    }
                }
            }

            boolean validate_email_addr(String email){
                String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                return email.matches(regex);
            }

            void validate_pswd(String pswd_1, String pswd_2){
                if(is_valid_pswd(pswd_1) && is_valid_pswd(pswd_2)){
                    if(pswd_1.equals(pswd_2)){
                        //everything matches, proceed to next intent
                        et_crt_pswrd.setCompoundDrawablesWithIntrinsicBounds(null, null, tick, null);
                        et_crt_pswrd.setBackgroundResource(R.drawable.popup_cormes_border);
                        et_rpt_pswrd.setCompoundDrawablesWithIntrinsicBounds(null, null, tick, null);
                        et_rpt_pswrd.setBackgroundResource(R.drawable.popup_cormes_border);

                        //proceed to next intent only when all matches
                        if(!upper_err){
                            //todo: transition animation; delete sleep()
                            try {
                                Thread.sleep(2000);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            //triggers EnterUserInfo intent when button clicked (after all input info is verified)
                            Intent intent = new Intent(getBaseContext(), EnterUserInfo.class);
                            //move to EnterUserInfo class for input personal info
                            startActivity(intent);
                        }

                    }
                    else{
                        Log.i("tag", "passwords don't match");
                        //ERROR: passwords are valid but do not match
                        et_crt_pswrd.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        et_crt_pswrd.setBackgroundResource(R.drawable.popup_erret_border);
                        et_rpt_pswrd.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        et_rpt_pswrd.setBackgroundResource(R.drawable.popup_erret_border);

                        rl_nonmatch_pswd_err.setVisibility(View.VISIBLE);
                        tv_btm_err_mes = findViewById(R.id.tv_pswd_error_mes);
                        tv_btm_err_mes.setText("Your passwords don't match");
                        et_crt_pswrd.setText("");
                        et_rpt_pswrd.setText("");
                        bottom_err = true;
                    }
                }
                else{
                    Log.i("tag", "passwords are invalid");
                    //ERROR: one or more passwords is invalid
                    et_crt_pswrd.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    et_crt_pswrd.setBackgroundResource(R.drawable.popup_erret_border);
                    et_rpt_pswrd.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    et_rpt_pswrd.setBackgroundResource(R.drawable.popup_erret_border);

                    rl_nonmatch_pswd_err.setVisibility(View.VISIBLE);
                    tv_btm_err_mes = findViewById(R.id.tv_pswd_error_mes);
                    tv_btm_err_mes.setText("Invalid password(s) entered");
                    et_crt_pswrd.setText("");
                    et_rpt_pswrd.setText("");
                    bottom_err = true;
                }
            }

            boolean is_valid_pswd(String pswd){
                if(pswd.length() < 8){
                    return false;
                }
                else{
                    char[] chars = pswd.toCharArray();
                    boolean has_num, has_upper, has_lower;
                    has_num = has_upper = has_lower = false;
                    for(char a : chars){
                        if(!has_num){
                            if(Character.isDigit(a)){
                                has_num = true;
                            }
                        }
                        if(!has_upper){
                            if(Character.isUpperCase(a)){
                                has_upper = true;
                            }
                        }
                        if(!has_lower){
                            if(Character.isLowerCase(a)){
                                has_lower = true;
                            }
                        }
                    }
                    if(has_num && has_upper && has_lower){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        });

    }

    public Boolean ifEmailExisted(String email){
        AccountDatabase database = new AccountDatabase(this);
        SQLiteDatabase readableDB = database.getReadableDatabase();
        Cursor cursor = readableDB.query(DatabaseUtil.EmailTable.tableName, null, null, null, null, null, null, null);
        List<String> emailList = new ArrayList<>();
        while(cursor.moveToNext()){
            emailList.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseUtil.EmailTable.emailColumn)));
        }
        readableDB.close();
        if(emailList.contains(email))
            return true;
        else
            return false;
    }

    public void addEmail(String email){
        AccountDatabase database = new AccountDatabase(this);
        SQLiteDatabase saveData = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseUtil.EmailTable.emailColumn, email);

        if(saveData.insert(DatabaseUtil.EmailTable.tableName, null, values) > 0){
            Toast.makeText(this, "Email info added successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "ERROR: unable to add email to databaase", Toast.LENGTH_SHORT).show();
        }
        saveData.close();
    }
}
