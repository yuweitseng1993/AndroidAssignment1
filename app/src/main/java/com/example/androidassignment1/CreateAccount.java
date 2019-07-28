package com.example.androidassignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upper_err == true){
                    rl_invalid_email_fmt.setVisibility(View.GONE);
                }
                if(bottom_err == true){
                    rl_nonmatch_pswd_err.setVisibility(View.GONE);
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
                    et_email_addr.setText("");
                    et_crt_pswrd.setText("");
                    et_rpt_pswrd.setText("");
                    bottom_err = true;
                }
                else{
                    //verify user input
                    if(validate_email_addr(usr_email) == true){
                        //Green boarder for email EditText + green tick
                        Drawable tick = getResources().getDrawable(R.drawable.tick);
                        et_email_addr.setCompoundDrawablesWithIntrinsicBounds(null, null, tick, null);
                        et_email_addr.setBackgroundResource(R.drawable.popup_cormes_border);

                        //validates password
//                        validate_pswd(usr_pswd, usr_rpt_pswd, true);
                    }
                    else{
                        //Error: invalid email address
                        rl_invalid_email_fmt.setVisibility(View.VISIBLE);
                        tv_up_err_mes = findViewById(R.id.tv_email_error_mes);
                        tv_up_err_mes.setText("Invalid email format");

                        //validates password
//                        validate_pswd(usr_pswd, usr_rpt_pswd, false);
                        et_email_addr.setText("");
                        et_crt_pswrd.setText("");
                        et_rpt_pswrd.setText("");
                        upper_err = true;
                    }
                }
            }

            boolean validate_email_addr(String email){
                String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                return email.matches(regex);
            }

//            void validate_pswd(String pswd_1, String pswd_2, boolean email_cond){
//                if(email_cond == true){
//                    if(is_valid_pswd(pswd_1) && is_valid_pswd(pswd_2)){
//                        if(pswd_1.equals(pswd_2)){
//
//                        }
//                        else{
//
//                        }
//                    }
//                    else{
//
//                    }
//                }
//                else{
//
//                }
//            }
//
//            boolean is_valid_pswd(String pswd){
//
//            }
        });

    }
}
