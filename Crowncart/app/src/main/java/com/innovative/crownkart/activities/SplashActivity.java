package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.config.ConnectivityReceiver;
import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CategoryDTO;
import com.innovative.crownkart.dto.SubcategoryDTO;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    Button btn_temp;

    private SharedPreferences sharedPreferences;
    private List<CategoryDTO> categoryDTOList = new ArrayList<>();
    boolean isLoggedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        btn_temp = (Button) findViewById(R.id.btn_temp);
        checkConnection();

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Connected to Internet";
            color = Color.WHITE;
            init();
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_temp), message, Snackbar.LENGTH_LONG);
//        SnackbarUtil.showLongSnackbar(SplashActivity.this, message);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("crownkart", Context.MODE_PRIVATE);
        isLoggedin = Boolean.parseBoolean(sharedPreferences.getString(SharedPrefernceValue.IS_LOGGED_IN, ""));
        if (isLoggedin) {
            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            initDataAndSplashLogic();
        }
    }

    private void initDataAndSplashLogic() {
        App.getApiHelper().getDrawerItem(new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                List<SubcategoryDTO> subCategoryDTOList;
                for (int i = 0; i < ((ArrayList) map.get("response")).size(); i++) {
                    subCategoryDTOList = new ArrayList<SubcategoryDTO>();
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setMainCategoryName(((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("main_category").toString());
                    categoryDTO.setMainCatId(((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("main_id").toString());
                    for (int j = 0; j < ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).size(); j++) {
                        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
                        if (Boolean.parseBoolean(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("has_product").toString())) {
                            subcategoryDTO.setProductId(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("product_id").toString());
                            subcategoryDTO.setHasProduct(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("has_product").toString());
                            subcategoryDTO.setSubCategoryName(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("category_name").toString());
                        } else {
                            subcategoryDTO.setError(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("error").toString());
                            subcategoryDTO.setHasProduct(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(i)).get("subcategory")).get(j)).get("has_product").toString());
                        }
                        subCategoryDTOList.add(subcategoryDTO);
                    }
                    categoryDTO.setSubcategoryDTOList(subCategoryDTOList);
                    categoryDTOList.add(categoryDTO);
                }
                Gson gson = new Gson();
                String json = gson.toJson(categoryDTOList);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("crown", json);
                editor.commit();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(App.getAppContext(), LoginActivity.class));
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        App.getAppContext().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
}
