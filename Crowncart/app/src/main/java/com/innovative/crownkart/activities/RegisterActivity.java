package com.innovative.crownkart.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.utils.SnackbarUtil;
import com.innovative.crownkart.utils.StringUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.et_email_address)
    EditText etEmailAddress;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_password)
    EditText etPassword;

    private String emailAddress, username, password, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup)
    public void onClickSignup() {
        emailAddress = etEmailAddress.getText().toString().trim();
        username = etUsername.getText().toString().trim();
        mobile = etMobile.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (validate()) {
            App.getApiHelper().registerUser(username, mobile, emailAddress, password, new ApiCallback<Map>() {
                @Override
                public void onSuccess(Map map) {
                    String message = ((LinkedTreeMap) ((LinkedTreeMap) map.get("response")).get("result")).get("message").toString();
                    SnackbarUtil.showLongSnackbar(RegisterActivity.this, message);
                }

                @Override
                public void onFailure(String message) {
                    SnackbarUtil.showShortSnackbar(RegisterActivity.this, message);
                }
            });
        }
    }

    private boolean validate() {
        if (StringUtil.isEmpty(username)) {
            SnackbarUtil.showShortSnackbar(this, getResources().getString(R.string.fields_empty));
            return false;
        } else if (StringUtil.isEmpty(mobile)) {
            SnackbarUtil.showShortSnackbar(this, getResources().getString(R.string.fields_empty));
            return false;
        } else if (mobile.length() < 10) {
            SnackbarUtil.showShortSnackbar(this, getResources().getString(R.string.register_mobile_error));
            return false;
        } else if (StringUtil.isEmpty(emailAddress)) {
            SnackbarUtil.showShortSnackbar(this, getResources().getString(R.string.fields_empty));
            return false;
        } else if (StringUtil.isEmpty(password)) {
            SnackbarUtil.showShortSnackbar(this, getResources().getString(R.string.fields_empty));
            return false;
        }

        return true;
    }

    @OnClick(R.id.tv_already_exist)
    public void onClickExistAccount() {
        startActivity(new Intent(App.getAppContext(), LoginActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }
}
