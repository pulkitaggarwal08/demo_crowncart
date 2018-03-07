package com.innovative.crownkart.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.dto.CategoryDTO;
import com.innovative.crownkart.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Pulkit on 24-Jun-17.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CategoryDTO> productDetailList;
    private OnProductSelectionListener onProductSelectionListener;

    public interface OnProductSelectionListener {
        public void onProductSelect(int position, String maincatId);
    }

    public HomeAdapter(ArrayList<CategoryDTO> productDetailList, OnProductSelectionListener onProductSelectionListener) {
        this.productDetailList = productDetailList;
        this.onProductSelectionListener = onProductSelectionListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CategoryDTO linkedTreeMap = productDetailList.get(position);

        HomeHolder holder = (HomeHolder) viewHolder;
        holder.tvCatName.setText(linkedTreeMap.getMainCategoryName());
        holder.ivCategoryImage.setImageResource(linkedTreeMap.getCategoryImage());
    }

    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    class HomeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_category_image)
        ImageView ivCategoryImage;
        @BindView(R.id.tv_cat_name)
        CustomTextView tvCatName;

        public HomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.rl_container)
        public void onClickCard() {
            onProductSelectionListener.onProductSelect(getLayoutPosition(), productDetailList.get(getLayoutPosition()).getMainCatId());
        }
    }
}
