package com.innovative.crownkart.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.innovative.crownkart.R;
import com.innovative.crownkart.config.App;
import com.innovative.crownkart.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Pulkit on 11-Jun-17.
 */

public class SpecificProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<LinkedTreeMap> productDetailList;
    private OnProductSelectionListener onProductSelectionListener;

    public interface OnProductSelectionListener {
        public void onProductSelect(int position);
    }

    public SpecificProductAdapter(ArrayList<LinkedTreeMap> productDetailList, OnProductSelectionListener onProductSelectionListener) {
        this.productDetailList = productDetailList;
        this.onProductSelectionListener = onProductSelectionListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpecificProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_items, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final LinkedTreeMap linkedTreeMap = productDetailList.get(position);

        final SpecificProductHolder holder = (SpecificProductHolder) viewHolder;
        if (linkedTreeMap.get("category_name") != null)
            holder.tvCatName.setText(linkedTreeMap.get("category_name").toString());
        if (linkedTreeMap.get("product_description") != null)
            holder.tvProductDescription.setText(linkedTreeMap.get("product_description").toString());
        if (linkedTreeMap.get("price") != null)
            holder.tvPrice.setText(linkedTreeMap.get("price").toString());
        if (linkedTreeMap.get("product_images") != null)
            Picasso.with(App.getAppContext()).load("http://crownkar.escuela.in/admin/" + linkedTreeMap.get("product_images").toString()).error(R.mipmap.tshirt).into(holder.ivProductImage);

    }

    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    class SpecificProductHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_image)
        ImageView ivProductImage;
        @BindView(R.id.tv_cat_name)
        CustomTextView tvCatName;
        @BindView(R.id.tv_product_description)
        CustomTextView tvProductDescription;
        @BindView(R.id.tv_price)
        CustomTextView tvPrice;
        @BindView(R.id.cardView)
        CardView cardView;

        public SpecificProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            /*ivProductImage = ButterKnife.findById(activity, R.id.iv_product_image);
            tvCatName = ButterKnife.findById(activity, R.id.tv_cat_name);
            tvProductDescription = ButterKnife.findById(activity, R.id.tv_product_description);*/
        }

        @OnClick(R.id.rl_container)
        public void onClickCard() {
            onProductSelectionListener.onProductSelect(getLayoutPosition());
        }
    }
}
