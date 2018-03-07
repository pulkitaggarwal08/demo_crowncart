package com.innovative.crownkart.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pulkit on 17-Jun-17.
 */

public class SubcategoryDTO implements Serializable {
    @SerializedName("has_product")
    private String hasProduct;
    @SerializedName("error")
    private String error;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("category_name")
    private String subCategoryName;

    public String getHasProduct() {
        return hasProduct;
    }

    public void setHasProduct(String hasProduct) {
        this.hasProduct = hasProduct;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
