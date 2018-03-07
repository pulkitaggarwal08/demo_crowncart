package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.SingleProductDTO;
import com.innovative.crownkart.dto.SizeInfoDTo;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.utils.SnackbarUtil;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomEditText;
import com.innovative.crownkart.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    CustomEditText et_email;
    @BindView(R.id.et_new_password)
    CustomEditText et_new_password;
    @BindView(R.id.btn_submit)
    CustomButton btn_submit;
    @BindView(R.id.et_confirm_password)
    CustomEditText et_confirm_password;

    private SharedPreferences sharedPreferences;
    private String emailAddress, new_password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");

    }

    @OnClick(R.id.btn_submit)
    public void OnClickSubmit() {

        new_password = et_new_password.getText().toString();
        confirm_password = et_confirm_password.getText().toString();

        if (!new_password.equalsIgnoreCase(new_password)) {
            SnackbarUtil.showLongSnackbar(ChangePasswordActivity.this, "Password is not matched");
        }
        else if (new_password.equalsIgnoreCase(confirm_password)) {

            App.getApiHelper().getSubmitData(emailAddress, new_password, new ApiCallback<Map>() {
                @Override
                public void onSuccess(Map map) {
                    //Todo: just show the password change confirmation message;
                }

                @Override
                public void onFailure(String message) {

                }
            });

        }

    }


}
