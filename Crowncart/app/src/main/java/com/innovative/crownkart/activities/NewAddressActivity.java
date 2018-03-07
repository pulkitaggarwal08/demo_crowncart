package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAddressActivity extends AppCompatActivity {

    @BindView(R.id.et_first_name)
    CustomEditText et_first_name;
    @BindView(R.id.et_last_name)
    CustomEditText et_last_name;
    @BindView(R.id.et_address_name)
    CustomEditText et_address_name;
    @BindView(R.id.et_landmark_name)
    CustomEditText et_landmark_name;
    @BindView(R.id.et_pincode_name)
    CustomEditText et_pincode_name;
    @BindView(R.id.et_city_name)
    CustomEditText et_city_name;
    @BindView(R.id.et_state_name)
    CustomEditText et_state_name;
    @BindView(R.id.spinner_country_name)
    Spinner spinner_country_name;
    @BindView(R.id.et_phone_name)
    CustomEditText et_phone_name;
    @BindView(R.id.btn_save_continue)
    CustomButton btn_save_continue;
    @BindView(R.id.btn_cancel)
    CustomButton btn_cancel;

    private ArrayAdapter<String> adapter;
    private List<String> list;
    private String firstName, lastName, addressName, landmarkName, pincodeName,
            cityName, stateName, countryName, phoneName, saveContinue,emailAddress;
    private SharedPreferences sharedPreferences;
    private Intent getIntentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress=sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS,null);
        getEditData();

        list = new ArrayList<String>();
        list.add("India");
        list.add("Australia");
        list.add("Mumbai");
        list.add("Gujrat");
        list.add("Goa");
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_country_name.setAdapter(adapter);
        spinner_country_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                countryName = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getEditData() {

        firstName = sharedPreferences.getString(SharedPrefernceValue.FIRST_NAME, "");
        lastName = sharedPreferences.getString(SharedPrefernceValue.LAST_NAME, "");
        addressName = sharedPreferences.getString(SharedPrefernceValue.ADDRESS_NAME, "");
        landmarkName = sharedPreferences.getString(SharedPrefernceValue.LANDMARK_NAME, "");
        pincodeName = sharedPreferences.getString(SharedPrefernceValue.PINCODE_NAME, "");
        cityName = sharedPreferences.getString(SharedPrefernceValue.CITY_NAME, "");
        stateName = sharedPreferences.getString(SharedPrefernceValue.STATE_NAME, "");
        countryName = sharedPreferences.getString(SharedPrefernceValue.COUNTRY_NAME, "");
        phoneName = sharedPreferences.getString(SharedPrefernceValue.PHONE_NAME, "");

        et_first_name.setText(firstName);
        et_last_name.setText(lastName);
        et_address_name.setText(addressName);
        et_landmark_name.setText(landmarkName);
        et_pincode_name.setText(pincodeName);
        et_city_name.setText(cityName);
        et_state_name.setText(stateName);
        et_phone_name.setText(phoneName);

    }

    @OnClick(R.id.btn_save_continue)
    public void onClickSaveContinue() {

        final SharedPreferences.Editor editor = sharedPreferences.edit();

        firstName = et_first_name.getText().toString();
        lastName = et_last_name.getText().toString();
        addressName = et_address_name.getText().toString();
        landmarkName = et_landmark_name.getText().toString();
        pincodeName = et_pincode_name.getText().toString();
        cityName = et_city_name.getText().toString();
        stateName = et_state_name.getText().toString();
        phoneName = et_phone_name.getText().toString();
        countryName=spinner_country_name.getSelectedItem().toString();

        App.getApiHelper().setAddress(emailAddress, firstName, lastName, addressName, addressName, landmarkName, cityName, stateName, countryName, pincodeName, phoneName, new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                System.out.println("map" + map);
                finish();
            }

            @Override
            public void onFailure(String message) {

            }
        });

        editor.putString(SharedPrefernceValue.FIRST_NAME, firstName);
        editor.putString(SharedPrefernceValue.LAST_NAME, lastName);
        editor.putString(SharedPrefernceValue.ADDRESS_NAME, addressName);
        editor.putString(SharedPrefernceValue.LANDMARK_NAME, landmarkName);
        editor.putString(SharedPrefernceValue.PINCODE_NAME, pincodeName);
        editor.putString(SharedPrefernceValue.CITY_NAME, cityName);
        editor.putString(SharedPrefernceValue.STATE_NAME, stateName);
        editor.putString(SharedPrefernceValue.PHONE_NAME, phoneName);
        editor.putString(SharedPrefernceValue.COUNTRY_NAME, countryName);
        editor.commit();


        //TODO: Hit the address save service here;

        finish();
    }

    @OnClick(R.id.btn_cancel)
    public void onClickCancel(){
        finish();
    }

}
