package com.innovative.crownkart.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.activities.ProductDetailActivity;
import com.innovative.crownkart.adapter.SpecificProductAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.views.CustomTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pulkit on 24-Jun-17.
 */

public class CategoryCompleteItemsFragment extends Fragment {
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.no_item_found)
    CustomTextView no_item_found;

    private String mainCatId;

    public CategoryCompleteItemsFragment() {
    }

    public void setId(String mainCatId) {
        this.mainCatId = mainCatId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        progress_bar.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainCatId = getArguments().getString("main_cat");
        rvItems.setLayoutManager(new GridLayoutManager(App.getAppContext(), 2));

        HashMap<String, Object> map = new HashMap<>();
        map.put("main_id", mainCatId);
        App.getApiHelper().getMainProduct(mainCatId, new ApiCallback<Map>() {
            @Override
            public void onSuccess(final Map map) {
                progress_bar.setVisibility(View.INVISIBLE);
                ArrayList<LinkedTreeMap> linkedTreeMaps = new ArrayList<LinkedTreeMap>();
                linkedTreeMaps = (((ArrayList) map.get("response")));
                for (int i = 0; i < linkedTreeMaps.size(); i++) {
                    String has_product = ((LinkedTreeMap) (((ArrayList) map.get("response")).get(i))).get("has_product").toString();
                    if (has_product.equals("false")) {
                        rvItems.setVisibility(View.INVISIBLE);
                        no_item_found.setVisibility(View.VISIBLE);
                        no_item_found.setText("No Item found");
                    } else {
                        rvItems.setVisibility(View.VISIBLE);
                        no_item_found.setVisibility(View.INVISIBLE);
                        rvItems.setAdapter(new SpecificProductAdapter(linkedTreeMaps, new SpecificProductAdapter.OnProductSelectionListener() {
                            @Override
                            public void onProductSelect(int position) {
                                final String pro_id = ((LinkedTreeMap) (((ArrayList) map.get("response")).get(position))).get("pro_id").toString();
                                Intent intent = new Intent(App.getAppContext(), ProductDetailActivity.class);
                                intent.putExtra("pro_id", pro_id);
                                startActivity(intent);
                            }
                        }));
                    }
                }
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    /*rvItems.setLayoutManager(new GridLayoutManager(App.getAppContext(), 2));
    SharedPreferences preferences = App.getAppContext().getSharedPreferences("crown", Context.MODE_PRIVATE);
    String id = preferences.getString("product_id", "1");
    App.getApiHelper().getSpecificProduct(id, new ApiCallback<Map>() {
        @Override
        public void onSuccess(Map map) {
            progress_bar.setVisibility(View.INVISIBLE);
            ArrayList<LinkedTreeMap> productDetailList = new ArrayList();
            productDetailList = (ArrayList) map.get("response");
            for (int i = 0; i < productDetailList.size(); i++) {
                Object o = ((LinkedTreeMap) (((ArrayList) map.get("response")).get(i))).get("has_product");
                if (o.equals("false")) {
                    rvItems.setVisibility(View.INVISIBLE);
                    no_item_found.setVisibility(View.VISIBLE);
                    no_item_found.setText("No Item found");
                } else {
                    rvItems.setVisibility(View.VISIBLE);
                    no_item_found.setVisibility(View.INVISIBLE);
                    rvItems.setAdapter(new SpecificProductAdapter(productDetailList, new SpecificProductAdapter.OnProductSelectionListener() {
                        @Override
                        public void onProductSelect(int position) {
                            Intent intent = new Intent(App.getAppContext(), ProductDetailActivity.class);
                            startActivity(intent);
                        }
                    }));
                }
            }

        }

        @Override
        public void onFailure(String message) {

        }
    });*/
}
