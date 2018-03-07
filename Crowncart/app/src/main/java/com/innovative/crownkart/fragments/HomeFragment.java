package com.innovative.crownkart.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.activities.ProductDetailActivity;
import com.innovative.crownkart.adapter.ExpandableAdapter;
import com.innovative.crownkart.adapter.HomeAdapter;
import com.innovative.crownkart.adapter.SpecificProductAdapter;
import com.innovative.crownkart.api.ApiCallback;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CategoryDTO;
import com.innovative.crownkart.dto.SubcategoryDTO;
import com.innovative.crownkart.views.CustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pulkit on 24-Jun-17.
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rv_items)
    RecyclerView rvItems;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.no_item_found)
    CustomTextView no_item_found;

    ArrayList<CategoryDTO> categoryDTOList = new ArrayList<>();
    List<SubcategoryDTO> subcategoryDTOList;
    private HomeAdapter homeAdapter;
    private Integer[] categoryImage = new Integer[]{R.mipmap.icon_men_category, R.mipmap.icon_women_category,
            R.mipmap.icon_lifestyle, R.mipmap.icon_phonecase};

    public HomeFragment() {
        getListFromPref();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        progress_bar.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvItems.setLayoutManager(new LinearLayoutManager(App.getAppContext()));
        homeAdapter = new HomeAdapter(categoryDTOList, new HomeAdapter.OnProductSelectionListener() {
            @Override
            public void onProductSelect(int position, String maincatId) {
                CategoryCompleteItemsFragment fragment = new CategoryCompleteItemsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("main_cat", maincatId);
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        rvItems.setAdapter(homeAdapter);

    }

    private void getListFromPref() {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences("crownkart", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("crown", "hello");
        try {
            JSONArray parentArray = new JSONArray(json);
            for (int i = 0; i < parentArray.length(); i++) {
                subcategoryDTOList = new ArrayList<>();
                CategoryDTO categoryDTO = new CategoryDTO();
                JSONObject jsonObject = parentArray.getJSONObject(i);
                categoryDTO.setMainCatId(jsonObject.getString("main_id"));
                categoryDTO.setMainCategoryName(jsonObject.getString("main_category"));
                JSONArray childArray = jsonObject.getJSONArray("subcategoryDTOList");
                for (int j = 0; j < childArray.length(); j++) {
                    JSONObject childObject = childArray.getJSONObject(j);
                    SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
                    if (Boolean.parseBoolean(childObject.getString("has_product"))) {
                        subcategoryDTO.setHasProduct(childObject.getString("has_product"));
                        subcategoryDTO.setProductId(childObject.getString("product_id"));
                        subcategoryDTO.setSubCategoryName(childObject.getString("category_name"));
                    } else {
                        subcategoryDTO.setHasProduct(childObject.getString("has_product"));
                        subcategoryDTO.setProductId("");
                        subcategoryDTO.setSubCategoryName("");
                    }
                    subcategoryDTOList.add(subcategoryDTO);
                }
                categoryDTO.setSubcategoryDTOList(subcategoryDTOList);

                categoryDTO.setCategoryImage(categoryImage[i]);
                categoryDTOList.add(categoryDTO);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
