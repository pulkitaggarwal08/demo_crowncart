package com.innovative.crownkart.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.innovative.crownkart.R;
import com.innovative.crownkart.adapter.ExpandableAdapter;
import com.innovative.crownkart.adapter.TabsPagerAdapter;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CategoryDTO;
import com.innovative.crownkart.dto.SubcategoryDTO;
import com.innovative.crownkart.fragments.CategoryFragment;
import com.innovative.crownkart.fragments.HomeFragment;
import com.innovative.crownkart.sharePreference.SharedPrefernceValue;
import com.innovative.crownkart.views.CustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity {
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /*@BindView(R.id.homeTabLayout)
    TabLayout homeTabLayout;*/
    @BindView(R.id.drawer_logout)
    CustomTextView drawer_logout;
    @BindView(R.id.drawer_faq_and_return_policy)
    CustomTextView drawer_faq_and_return_policy;
    @BindView(R.id.drawer_contact_us)
    CustomTextView drawer_contact_us;
    @BindView(R.id.rel_blood)
    RelativeLayout shopping_cart_count;
    @BindView(R.id.shopping_cart)
    CustomTextView shopping_cart;
//    @BindView(R.id.pager)
//    ViewPager viewPager;

    private SharedPreferences sharedPreferences;
    boolean isLoggedin;
    String emailAddress;

    List<CategoryDTO> categoryDTOList = new ArrayList<>();
    List<SubcategoryDTO> subcategoryDTOList;
    HashMap<String, List<SubcategoryDTO>> map = new HashMap<>();

    private ExpandableAdapter expandableAdapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String toolbarTitle;
    private Typeface fontAwesomeFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        elvCategory.setGroupIndicator(null);
        getListFromPref();

        toolbarTitle = categoryDTOList.get(0).getMainCategoryName() + " - " + map.get(categoryDTOList.get(0).getMainCategoryName()).get(0).getSubCategoryName();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        shopping_cart.setTypeface(fontAwesomeFont);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(toolbarTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(toolbarTitle);
                invalidateOptionsMenu();
            }
        };

        if (savedInstanceState == null) {
            //SelectItem(0);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        sharedPreferences = getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
        isLoggedin = Boolean.parseBoolean(sharedPreferences.getString(SharedPrefernceValue.IS_LOGGED_IN, ""));
        emailAddress = sharedPreferences.getString(SharedPrefernceValue.EMAIL_ADDRESS, "");
        if(isLoggedin){
            shopping_cart_count.setVisibility(View.VISIBLE);
            shopping_cart.setVisibility(View.VISIBLE);
        }
        else{
            shopping_cart.setVisibility(View.GONE);
            shopping_cart_count.setVisibility(View.GONE);
        }

       /* homeTabLayout.addTab(homeTabLayout.newTab().setIcon(R.mipmap.ic_home));
        homeTabLayout.addTab(homeTabLayout.newTab().setIcon(R.mipmap.ic_profile));
        homeTabLayout.addTab(homeTabLayout.newTab().setIcon(R.mipmap.ic_cart));
        homeTabLayout.addTab(homeTabLayout.newTab().setIcon(R.mipmap.ic_myorder));

        final TabsPagerAdapter adapter = new TabsPagerAdapter
                (getSupportFragmentManager(), homeTabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        homeTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

*/
        elvCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                toolbarTitle = categoryDTOList.get(groupPosition).getMainCategoryName() + " : "
                        + map.get(categoryDTOList.get(groupPosition).getMainCategoryName()).get(childPosition).getSubCategoryName();

                SharedPreferences preferences = getSharedPreferences("crown", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("product_id", map.get(categoryDTOList.get(groupPosition).getMainCategoryName()).get(childPosition).getProductId());
                editor.commit();

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CategoryFragment()).commit();
                getSupportActionBar().setTitle(toolbarTitle);
                drawerLayout.closeDrawers();

                return false;
            }
        });

        elvCategory.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup) {
                    elvCategory.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }

        });
    }

    private void SelectItem(int position) {
        //FragmentManager frgManager = getFragmentManager();
        //frgManager.beginTransaction().replace(R.id.container, new CategoryFragment()).commit();

        //getSupportActionBar().setTitle(toolbarTitle);
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
                    if (Boolean.parseBoolean(childObject.getString("has_product")) == false) {

                    } else {
                        subcategoryDTOList.add(subcategoryDTO);
                    }
                }
                categoryDTO.setSubcategoryDTOList(subcategoryDTOList);
                categoryDTOList.add(categoryDTO);
            }

            for (int i = 0; i < categoryDTOList.size(); i++) {
                map.put(categoryDTOList.get(i).getMainCategoryName(), categoryDTOList.get(i).getSubcategoryDTOList());
            }

            expandableAdapter = new ExpandableAdapter(App.getAppContext(), categoryDTOList, map);
            elvCategory.setAdapter(expandableAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
//    }

    @OnClick(R.id.home)
    public void onClickHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        getSupportActionBar().setTitle("CrownKart");
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.shopping_cart)
    public void on_click_shopping_cart() {

        Intent intent = new Intent(DashboardActivity.this, ViewCartActivity.class);
        intent.putExtra("emailAddress", emailAddress);
        startActivity(intent);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
//        else if (id == R.id.logout) {
//            logout();
//        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.drawer_logout)
    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?")
                .setTitle("CrownKart")
                .setIcon(R.mipmap.splash_logo)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SharedPrefernceValue.MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(SharedPrefernceValue.IS_LOGGED_IN);
//                        editor.clear();
                        editor.commit();

                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @OnClick(R.id.drawer_faq_and_return_policy)
    public void drawer_faq_and_return_policy() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }

    @OnClick(R.id.drawer_contact_us)
    public void drawer_contact_us() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }
}
