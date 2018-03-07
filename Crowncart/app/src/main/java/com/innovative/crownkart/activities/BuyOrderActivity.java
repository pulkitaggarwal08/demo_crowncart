package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ViewCartAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CartDTO;
import com.innovative.crownkart.dto.SingleProductDTO;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomTextView;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyOrderActivity extends AppCompatActivity {

    @BindView(R.id.tv_logged_in)
    CustomTextView tv_logged_in;
    @BindView(R.id.add_new_address)
    CustomButton add_new_address;
    @BindView(R.id.tv_shipment_edit)
    CustomTextView tv_shipment_edit;
    @BindView(R.id.rv_cart_items)
    RecyclerView rv_cart_items;
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
    private ViewCartAdapter viewCartAdapter;
    private SharedPreferences sharedPreferences;
    private String emailAddress, firstName, lastName, addressName, landmarkName, pincodeName,
            cityName, stateName, countryName, phoneName,pro_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_order);
        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
        firstName = sharedPreferences.getString(SharedPrefernceValue.FIRST_NAME, "");
        lastName = sharedPreferences.getString(SharedPrefernceValue.LAST_NAME, "");
        addressName = sharedPreferences.getString(SharedPrefernceValue.ADDRESS_NAME, "");
        landmarkName = sharedPreferences.getString(SharedPrefernceValue.LANDMARK_NAME, "");
        pincodeName = sharedPreferences.getString(SharedPrefernceValue.PINCODE_NAME, "");
        cityName = sharedPreferences.getString(SharedPrefernceValue.CITY_NAME, "");
        stateName = sharedPreferences.getString(SharedPrefernceValue.STATE_NAME, "");
        countryName = sharedPreferences.getString(SharedPrefernceValue.COUNTRY_NAME, "");
        phoneName = sharedPreferences.getString(SharedPrefernceValue.PHONE_NAME, "");
        pro_id=getIntent().getStringExtra("pro_id");
        if(pro_id!=null){
            getViewCartItems(pro_id);
        }
        else {
            getViewCartItems();
        }


        tv_logged_in.setText(emailAddress);

        if (addressName.equals(null) || addressName.equals("")) {
            add_new_address.setVisibility(View.VISIBLE);
        }
        else
        {
            add_new_address.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.tv_shipment_edit)
    public void onClickEdit() {

        Intent intent = new Intent(BuyOrderActivity.this, NewAddressActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.add_new_address)
    public void onClickNewAddress() {

        Intent intent = new Intent(BuyOrderActivity.this, NewAddressActivity.class);
        startActivity(intent);
    }
    private void getViewCartItems() {
        rv_cart_items.setLayoutManager(new LinearLayoutManager(App.getAppContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        App.getApiHelper().viewCart(emailAddress, new ApiCallback<Map>() {
            @Override
            public void onSuccess(final Map map) {

                System.out.println("map" + map);
                ArrayList<CartDTO> viewCartList = new ArrayList<CartDTO>();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                String coupon=((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("coupon_code").toString();
                String benifit=((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("benifit").toString();

                String subTotal = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("subtotal").toString();
                String cart_id = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("cart_id").toString();
                String total_charge= ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("total_charge").toString();
                for(int i=0;i<((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).size();i++){
                    CartDTO cartDTO=new CartDTO();
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


                rv_cart_items.setVisibility(View.VISIBLE);
                tv_price_one.setText(benifit);
                tv_price_two.setText(subTotal);
                tvCashback.setText(coupon);
                tv_price_three.setText(total_charge);
                Log.v("viewCart",viewCartList.toString());
                viewCartAdapter = new ViewCartAdapter(getApplicationContext(), viewCartList);
                rv_cart_items.setAdapter(viewCartAdapter);

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
    private void getViewCartItems(String pro_id) {
        rv_cart_items.setLayoutManager(new LinearLayoutManager(App.getAppContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        Log.v("pro_id",pro_id);
        App.getApiHelper().buySingleProduct(emailAddress,pro_id, new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {

                System.out.println("map" + map);
                ArrayList<CartDTO> viewCartList = new ArrayList<CartDTO>();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                String coupon=((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("coupon_code").toString();
                String benifit=((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("benifit").toString();

                String subTotal = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("subtotal").toString();
                String cart_id = ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("cart_id").toString();
                String total_charge= ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("total_charge").toString();
                for(int i=0;i<((ArrayList) ((LinkedTreeMap) ((ArrayList) ((LinkedTreeMap) map.get("response")).get("result")).get(0)).get("product_description")).size();i++){
                    CartDTO cartDTO=new CartDTO();
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


                rv_cart_items.setVisibility(View.VISIBLE);
                tv_price_one.setText(benifit);
                tv_price_two.setText(subTotal);
                tvCashback.setText(coupon);
                tv_price_three.setText(total_charge);
                Log.v("viewCart",viewCartList.toString());
                viewCartAdapter = new ViewCartAdapter(getApplicationContext(), viewCartList);
                rv_cart_items.setAdapter(viewCartAdapter);

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

}
