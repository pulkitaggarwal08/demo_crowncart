package com.innovative.crownkart.dto;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 17-Jun-17.
 */

public class CategoryDTO implements Serializable {

    @SerializedName("main_id")
    private String mainCatId;
    @SerializedName("main_category")
    private String mainCategoryName;
    @SerializedName("has_product")
    private String hasProduct;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("category_name")
    private String subCategoryName;
    @SerializedName("category_image")
    private int categoryImage;

    public int getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    private List<SubcategoryDTO> subcategoryDTOList;

    public List<SubcategoryDTO> getSubcategoryDTOList() {
        return subcategoryDTOList;
    }

    public void setSubcategoryDTOList(List<SubcategoryDTO> subcategoryDTOList) {
        this.subcategoryDTOList = subcategoryDTOList;
    }

    public String getMainCatId() {
        return mainCatId;
    }

    public void setMainCatId(String mainCatId) {
        this.mainCatId = mainCatId;
    }

    public String getMainCategoryName() {
        return mainCategoryName;
    }

    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    public String getHasProduct() {
        return hasProduct;
    }

    public void setHasProduct(String hasProduct) {
        this.hasProduct = hasProduct;
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
