package com.innovative.crownkart.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innovative.crownkart.R;
import com.innovative.crownkart.config.App;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public class ApiHelper {
    private static App app;
    private static ApiHelper apiHelper;
    private ApiService apiService;

    private ApiHelper() {
    }

    public static ApiHelper init(App app) {
        if (apiHelper == null) {
            apiHelper = new ApiHelper();
            apiHelper.initApiService();
            setApp(app);
        }
        return apiHelper;
    }

    public static void setApp(App app) {
        ApiHelper.app = app;
    }

    private void initApiService() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://crownkar.escuela.in/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //http://crownkart.com/crownKart/api/
        //http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors
        //http://lifebeat.16mb.com/
        apiService = retrofit.create(ApiService.class);
    }

    public void registerUser(String username, String mobile, String emailAddress, String password, final ApiCallback<Map> apiCallback) {
        apiService.registerUser(username, mobile, emailAddress, password).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void loginUser(String emailAddress, String password, final ApiCallback<Map> apiCallback) {
        apiService.loginUser(emailAddress, password).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getSpecificProduct(String productId, final ApiCallback<Map> apiCallback) {
        apiService.getSpecificProduct(productId).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getDrawerItem(final ApiCallback<Map> apiCallback) {
        apiService.getDrawerItems().enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getMainProduct(String mainId, final ApiCallback<Map> apiCallback) {
        apiService.getMainProduct(mainId).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void getSingleProductDetails(String pro_id, final ApiCallback<Map> apiCallback) {
        apiService.getSingleProductDetails(pro_id).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void buyProducts(String email, String pro_id, String size, String quantity, String price, String total_price, final ApiCallback<Map> apiCallback) {
        apiService.buyProducts(email, pro_id, size, quantity, price, total_price).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });

    }

    public void addToCart(String email, String pro_id, String size, String quantity, String price, final ApiCallback<Map> apiCallback) {
        apiService.addToCart(email, pro_id, size, quantity, price).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void viewCart(String email, final ApiCallback<Map> apiCallback) {
        apiService.viewCart(email).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
            }
        });
    }

    public void buySingleProduct(String email, String pro_id, final ApiCallback<Map> apiCallback) {
        apiService.buySingleProduct(email, pro_id).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
                t.printStackTrace();
            }
        });
    }

    public void setAddress(String email, String firstname, String lastname, String add_line1,
                           String add_line2, String landmark, String city, String state,
                           String country, String pincode, String phone,final ApiCallback<Map> apiCallback){
        apiService.setAddress(email,firstname,lastname,add_line1,
                add_line2,landmark,city,state,country,pincode,phone).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
                t.printStackTrace();
            }
        });
    }

    public void deleteProduct(String email, String pro_id, final  ApiCallback<Map> apiCallback){
        apiService.deleteProduct(email,pro_id).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
                t.printStackTrace();
            }
        });
    }

    public  void updateProduct(String email, String pro_id, String size, String qty, final ApiCallback<Map> apiCallback){
        apiService.updateProduct(email,pro_id,size,qty).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");
                t.printStackTrace();
            }
        });
    }
    public void applyCoupon(String email, String couponCode, final ApiCallback<Map> apiCallback) {
        apiService.applyCoupon(email, couponCode).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");

            }
        });
    }

    public void getSubmitData(String email, String new_password, final ApiCallback<Map> apiCallback) {
        apiService.getSubmitData(email, new_password).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");

            }
        });
    }


    public void getMyAccount(String firstName, String lastName, String phone, String gender, String emailAddress, final ApiCallback<Map> apiCallback) {
        apiService.getMyAccount(firstName, lastName, phone, gender, emailAddress).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                apiCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                apiCallback.onFailure("unable to parse data from server");

            }
        });
    }
}
