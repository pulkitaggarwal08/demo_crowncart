package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ViewCartAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CartDTO;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.utils.SnackbarUtil;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomEditText;
import com.innovative.crownkart.views.CustomTextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewCartActivity extends AppCompatActivity {

    @BindView(R.id.rv_view_cart_items)
    RecyclerView rv_view_cart_items;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.sv)
    NestedScrollView scrollView;
    @BindView(R.id.tv_price_one)
    CustomTextView tv_price_one;
    @BindView(R.id.tv_price_two)
    CustomTextView tv_price_two;
    @BindView(R.id.tv_price_three)
    CustomTextView tv_price_three;
    @BindView(R.id.cashback)
    CustomTextView tvCashback;
    @BindView(R.id.btn_place_order)
    CustomButton btn_place_order;
    @BindView(R.id.et_enter_coupon_code)
    CustomEditText et_enter_coupon_code;
    @BindView(R.id.shopping_cart_count)
    CustomTextView shopping_cart_count;
    @BindView(R.id.shopping_cart)
    CustomTextView shopping_cart;
    @BindView(R.id.rel_blood)
    RelativeLayout rel_blood;

    private String emailAddress;
    private SharedPreferences sharedPreferences;
    private ViewCartAdapter viewCartAdapter;
    ArrayList<CartDTO> viewCartList;
    private Typeface fontAwesomeFont;
    ViewCartAdapter adapter;

    TextView sizes,qtys;
    String sizeset,qtyset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        shopping_cart.setTypeface(fontAwesomeFont);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
        boolean isLoggedin = Boolean.parseBoolean(sharedPreferences.getString(SharedPrefernceValue.IS_LOGGED_IN, ""));
        getViewCartItems();
        if(isLoggedin){
            rel_blood.setVisibility(View.VISIBLE);
            shopping_cart.setVisibility(View.VISIBLE);
        }
        else{
            shopping_cart.setVisibility(View.GONE);
            rel_blood.setVisibility(View.GONE);
        }
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCartActivity.this, BuyOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getViewCartItems() {
        rv_view_cart_items.setLayoutManager(new LinearLayoutManager(App.getAppContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        App.getApiHelper().viewCart(emailAddress, new ApiCallback<Map>() {
            @Override
            public void onSuccess(final Map map) {

                System.out.println("map" + map);
                viewCartList = new ArrayList<CartDTO>();

               // viewCartList = ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result"));

                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                String status=((LinkedTreeMap)((ArrayList)((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("status").toString();
                if(status.equals("1")) {
                    String coupon = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("coupon_code").toString();
                    String benifit = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("benifit").toString();

                    String subTotal = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("subtotal").toString();
                    String cart_id = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("cart_id").toString();
                    String total_charge = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("total_charge").toString();
                    for (int i = 0; i < ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).size(); i++) {
                        CartDTO cartDTO = new CartDTO();
                        cartDTO.setCoupon_code(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("coupon_code").toString());
                        cartDTO.setBenifit(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("benifit").toString());
                        cartDTO.setPro_id(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("pro_id").toString());
                        cartDTO.setProduct_id(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("product_id").toString());
                        cartDTO.setCategory_name(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("category_name").toString());
                        cartDTO.setColor_code(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("color_code").toString());
                        cartDTO.setDiscount(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("discount").toString());
                        cartDTO.setPrice(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("price").toString());
                        cartDTO.setProduct_images(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("product_images").toString());
                        cartDTO.setGender(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("gender").toString());
                        cartDTO.setSize(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("size").toString());
                        cartDTO.setQuantity(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("quantity").toString());
                        cartDTO.setProduct_description(((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).get(i)).get("product_description").toString());
                        viewCartList.add(cartDTO);
                    }


                    rv_view_cart_items.setVisibility(View.VISIBLE);
                    tv_price_one.setText(benifit);
                    tv_price_two.setText(subTotal);
                    tvCashback.setText(coupon);
                    tv_price_three.setText(total_charge);
                    Log.v("viewCart", viewCartList.toString());

                    viewCartAdapter = new ViewCartAdapter(getApplicationContext(), viewCartList);
                    adapter = new ViewCartAdapter(viewCartList, new ViewCartAdapter.onClickButtonListener() {
                        @Override
                        public void onClickButton(int position) {
                            Toast.makeText(getApplication(), "LAds", Toast.LENGTH_SHORT).show();
                        }
                    });

                    rv_view_cart_items.setAdapter(adapter);
                }
                else{
                    viewCartAdapter = new ViewCartAdapter(getApplicationContext(), viewCartList);
                    rv_view_cart_items.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @OnClick(R.id.apply)
    void applyCoupon(){
        String couponCode=et_enter_coupon_code.getText().toString();
        App.getApiHelper().applyCoupon(emailAddress, couponCode, new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {
                String server_response = (((LinkedTreeMap) ((LinkedTreeMap) map.get("response")).get("result")).get("message")).toString();

                    SnackbarUtil.showLongSnackbar(ViewCartActivity.this, server_response + " ");
                startActivity(new Intent(ViewCartActivity.this,ViewCartActivity.class));
                finish();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @OnClick(R.id.shopping_cart)
    public void on_click_shopping_cart() {

        Intent intent = new Intent(ViewCartActivity.this, ViewCartActivity.class);
        intent.putExtra("emailAddress", emailAddress);
        startActivity(intent);
    }
}
