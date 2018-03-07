package com.innovative.crownkart.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pulkit on 28-Jun-17.
 */

public class SingleProductDTO {

    @SerializedName("has_product")
    private String has_product;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("product_description")
    private String product_description;
    @SerializedName("color_code")
    private String color_code;
    @SerializedName("price")
    private int price;
    @SerializedName("discount")
    private int discount;
    @SerializedName("product_images")
    private String product_images;
    @SerializedName("new_price")
    private int new_price;

    public String getHas_product() {
        return has_product;
    }

    public void setHas_product(String has_product) {
        this.has_product = has_product;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    private List<SizeInfoDTo> sizeInfoDTOList;

    public List<SizeInfoDTo> getSizeInfoDTOList() {
        return sizeInfoDTOList;
    }

    public void setSizeInfoDTOList(List<SizeInfoDTo> sizeInfoDTOList) {
        this.sizeInfoDTOList = sizeInfoDTOList;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getProduct_images() {
        return product_images;
    }

    public void setProduct_images(String product_images) {
        this.product_images = product_images;
    }

    public int getNew_price() {
        return new_price;
    }

    public void setNew_price(int new_price) {
        this.new_price = new_price;
    }
}
