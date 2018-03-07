package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.SingleProductDTO;
import com.innovative.crownkart.dto.SizeInfoDTo;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.utils.SnackbarUtil;
import com.innovative.crownkart.views.CustomButton;
import com.innovative.crownkart.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_product_name)
    CustomTextView tv_product_name;
    @BindView(R.id.tv_product_price)
    CustomTextView tv_product_price;
    @BindView(R.id.tv_colour_code)
    CustomTextView tv_colour_code;
    @BindView(R.id.rb_product_rating)
    AppCompatRatingBar rb_product_rating;
    @BindView(R.id.tv_size)
    CustomTextView tv_size;
    @BindView(R.id.tv_quantity)
    CustomTextView tv_quantity;
    @BindView(R.id.buy_now)
    CustomButton buy_now;
    @BindView(R.id.add_to_cart)
    CustomButton add_to_cart;
    @BindView(R.id.toolbar_title)
    CustomTextView toolbar_title;
    @BindView(R.id.iv_product_image)
    ImageView iv_product_image;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    @BindView(R.id.iv_add)
    ImageView iv_add;
    @BindView(R.id.iv_minus)
    ImageView iv_minus;
    @BindView(R.id.iv_quantity_add)
    ImageView iv_quantity_add;
    @BindView(R.id.iv_quantity_minus)
    ImageView iv_quantity_minus;
    @BindView(R.id.single_product_detail_toolbar)
    Toolbar single_product_detail_toolbar;
    @BindView(R.id.shopping_cart_count)
    CustomTextView shopping_cart_count;
    @BindView(R.id.shopping_cart)
    CustomTextView shopping_cart;
    @BindView(R.id.rel_blood)
    RelativeLayout rel_blood;

    private String getIntentProId, emailAddress, category_name, product_description, price = "0";
    private SharedPreferences sharedPreferences;
    boolean isLoggedin;
    private Typeface fontAwesomeFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);

        setSupportActionBar(single_product_detail_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        shopping_cart.setTypeface(fontAwesomeFont);

        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        isLoggedin = Boolean.parseBoolean(sharedPreferences.getString(SharedPrefernceValue.IS_LOGGED_IN, "false"));
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
        getIntentProId = getIntent().getExtras().getString("pro_id");

        if (isLoggedin) {
            rel_blood.setVisibility(View.VISIBLE);
            shopping_cart.setVisibility(View.VISIBLE);
        } else {
            shopping_cart.setVisibility(View.GONE);
            rel_blood.setVisibility(View.GONE);
        }
        getSingleProductDetails();
    }

    SingleProductDTO singleProductDTO;

    private void getSingleProductDetails() {
        App.getApiHelper().getSingleProductDetails(getIntentProId, new ApiCallback<Map>() {
            @Override
            public void onSuccess(Map map) {

                String image_uri = "http://crownkar.escuela.in/admin/";
                singleProductDTO = new SingleProductDTO();
                singleProductDTO.setCategory_name(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("category_name").toString());
                singleProductDTO.setGender(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("gender").toString());
                singleProductDTO.setProduct_description(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("product_description").toString());
                singleProductDTO.setColor_code(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("color_code").toString());
                singleProductDTO.setPrice(Integer.parseInt(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("price").toString()));
                singleProductDTO.setDiscount(Integer.parseInt(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("discount").toString()));
                singleProductDTO.setProduct_images(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("product_images").toString());
                singleProductDTO.setNew_price(Integer.parseInt(((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("new_price").toString()));

                ArrayList<SizeInfoDTo> sizeInfoDToList = new ArrayList();
                for (int i = 0; i < ((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("sizes")).size(); i++) {
                    SizeInfoDTo sizeInfoDTo = new SizeInfoDTo();//((ArrayList)((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("sizes")).get(i);
                    sizeInfoDTo.setSize(((LinkedTreeMap) (((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("sizes")).get(i))).get("size").toString());
                    sizeInfoDTo.setQuantity(((LinkedTreeMap) (((ArrayList) ((LinkedTreeMap) ((ArrayList) map.get("response")).get(0)).get("sizes")).get(i))).get("qty").toString());
                    sizeInfoDToList.add(sizeInfoDTo);
                }
                singleProductDTO.setSizeInfoDTOList(sizeInfoDToList);
                tv_product_name.setText(singleProductDTO.getProduct_description());
                tv_product_price.setText(singleProductDTO.getPrice() + "");
                tv_colour_code.setText(singleProductDTO.getColor_code());
                tv_size.setText(singleProductDTO.getSizeInfoDTOList().get(0).getSize());
                tv_quantity.setText(singleProductDTO.getSizeInfoDTOList().get(0).getQuantity());
                Picasso.with(getApplicationContext()).load(image_uri + singleProductDTO.getProduct_images()).into(iv_product_image);
                progress_bar.setVisibility(View.INVISIBLE);

                toolbar_title.setText(singleProductDTO.getCategory_name());

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
    int count = 1, sizecount = 1, total_item = 1, total_price;

    @OnClick(R.id.buy_now)
    public void on_click_buy_Product() {

        if (isLoggedin) {
            total_price = Integer.parseInt(tv_product_price.getText().toString());
            App.getApiHelper().buyProducts(emailAddress, getIntentProId, tv_size.getText().toString(), String.valueOf(total_item), singleProductDTO.getPrice() + "", String.valueOf(total_price), new ApiCallback<Map>() {
                @Override
                public void onSuccess(Map map) {

                    Intent intent = new Intent(ProductDetailActivity.this, BuyOrderActivity.class);
                    intent.putExtra("pro_id", getIntentProId);
                    startActivity(intent);

                    System.out.println("Successfully done");
                }

                @Override
                public void onFailure(String message) {

                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please login first")
                    .setTitle("CrownKart")
                    .setIcon(R.mipmap.splash_logo)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
    }

    int max_size = 0;

    @OnClick(R.id.iv_add)
    public void on_click_size_add() {
        if (max_size < singleProductDTO.getSizeInfoDTOList().size() - 1) {
            max_size = sizecount++;
            String size = singleProductDTO.getSizeInfoDTOList().get(max_size).getSize();
            tv_size.setText(size);
        } else {
            max_size = singleProductDTO.getSizeInfoDTOList().size() - 1;
            tv_size.setText(singleProductDTO.getSizeInfoDTOList().get(max_size).getSize());
            iv_add.setClickable(false);
            iv_add.setImageResource(R.mipmap.light_plus);
        }

        iv_minus.setClickable(true);
        iv_minus.setImageResource(R.mipmap.dark_minus);
    }

    @OnClick(R.id.iv_minus)
    public void on_click_size_minus() {
        if (max_size > 0) {
            max_size = --sizecount;
            String size = singleProductDTO.getSizeInfoDTOList().get(max_size).getSize();
            tv_size.setText(size);
        } else {
            max_size = 0;
            tv_size.setText(singleProductDTO.getSizeInfoDTOList().get(max_size).getSize());
            iv_minus.setClickable(false);
            iv_minus.setImageResource(R.mipmap.light_minus);
        }

        iv_add.setClickable(true);
        iv_add.setImageResource(R.mipmap.dark_plus);
    }

    @OnClick(R.id.iv_quantity_add)
    public void on_click_quantity_add() {
        int qty = Integer.parseInt(singleProductDTO.getSizeInfoDTOList().get(max_size).getQuantity());
        price = String.valueOf(singleProductDTO.getPrice());
        if (total_item < qty) {
            total_item = count++;
            total_price = Integer.parseInt(price) * total_item;

            tv_quantity.setText(String.valueOf(total_item));
            tv_product_price.setText(String.valueOf(total_price));

            iv_quantity_minus.setClickable(true);
            iv_quantity_minus.setImageResource(R.mipmap.dark_minus);
        } else {
            iv_quantity_add.setClickable(false);
            iv_quantity_add.setImageResource(R.mipmap.light_plus);
        }
    }

    @OnClick(R.id.iv_quantity_minus)
    public void on_click_quantity_minus() {
        int qty = Integer.parseInt(singleProductDTO.getSizeInfoDTOList().get(max_size).getQuantity());
        price = String.valueOf(singleProductDTO.getPrice());

        if (total_item > qty) {
            total_item = count--;
            total_price = Integer.parseInt(price) * total_item;

            tv_quantity.setText(String.valueOf(total_item));
            tv_product_price.setText(String.valueOf(total_price));

            iv_quantity_add.setClickable(true);
            iv_quantity_add.setImageResource(R.mipmap.dark_plus);
        } else {
            iv_quantity_minus.setClickable(false);
            iv_quantity_minus.setImageResource(R.mipmap.light_minus);
        }
    }

//    @OnClick(R.id.buy_now)
//    public void on_click_buy_now() {
//
//        if (isLoggedin) {
//            //TODO: Buy now
//
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Please login first")
//                    .setTitle("CrownKart")
//                    .setIcon(R.mipmap.splash_logo)
//                    .setCancelable(false)
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            builder.show();
//        }
//    }

    @OnClick(R.id.add_to_cart)
    public void on_click_add_to_cart() {
        if (isLoggedin) {
            App.getApiHelper().addToCart(emailAddress, getIntentProId, tv_size.getText().toString(), String.valueOf(total_item), String.valueOf(total_price), new ApiCallback<Map>() {
                @Override
                public void onSuccess(Map map) {
                    String server_response = (((LinkedTreeMap) ((LinkedTreeMap) map.get("response")).get("result")).get("message")).toString();
                    if (total_item == 1) {
                        SnackbarUtil.showLongSnackbar(ProductDetailActivity.this, total_item + " " + server_response);
                    } else {
                        SnackbarUtil.showLongSnackbar(ProductDetailActivity.this, total_item + " " + "Product added to cart");
                    }

                    shopping_cart_count.setText(String.valueOf(total_item));
                }

                @Override
                public void onFailure(String message) {

                }
            });

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please login first")
                    .setTitle("CrownKart")
                    .setIcon(R.mipmap.splash_logo)
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(ProductDetailActivity.this, LoginActivity.class));
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
    }

    @OnClick(R.id.shopping_cart)
    public void on_click_shopping_cart() {

        Intent intent = new Intent(ProductDetailActivity.this, ViewCartActivity.class);
        intent.putExtra("emailAddress", emailAddress);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
