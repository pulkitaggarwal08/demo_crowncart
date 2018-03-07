package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.utils.SnackbarUtil;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomEditText;
import com.innovative.crownkart.views.CustomTextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccountActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    CustomEditText et_first_name;
    @BindView(R.id.et_last_name)
    CustomEditText et_last_name;
    @BindView(R.id.et_phone)
    CustomTextView et_phone;
    @BindView(R.id.et_email)
    CustomEditText et_email;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.btn_save_info)
    CustomButton btn_save_info;

    RadioButton rb_gender;
    private SharedPreferences sharedPreferences;
    private String emailAddress, firstName, lastName, phone, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");

    }

    @OnClick(R.id.btn_save_info)
    public void OnClickSaveInfo() {

        firstName = et_first_name.getText().toString();
        lastName = et_last_name.getText().toString();
        phone = et_phone.getText().toString();
        rb_gender = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
        gender = rb_gender.getText().toString();
        if (emailAddress.isEmpty() || emailAddress.equals(null) || emailAddress.equals("")) {
            emailAddress = et_email.getText().toString();
        } else {
            emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
        }

        App.getApiHelper().getMyAccount(firstName, lastName, phone, gender, emailAddress, new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                //Todo: just show the address confirmation message;

            }

            @Override
            public void onFailure(String message) {

            }
        });


    }

}
