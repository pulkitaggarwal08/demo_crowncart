package com.innovative.crownkart.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lavisha_User on 7/8/2017.
 */

public class CartDTO {
    @SerializedName("cart_id")
    String cart_id;
    @SerializedName("product_images")
    String product_images;
    @SerializedName("pro_id")
    String pro_id;
    @SerializedName("category_name")
    String category_name;
    @SerializedName("product_id")
    String product_id;
    @SerializedName("product_description")
    String product_description;
    @SerializedName("color_code")
    String color_code;
    @SerializedName("gender")
    String gender;
    @SerializedName("price")
    String price;
    @SerializedName("size")
    String size;
    @SerializedName("qty")
    String quantity;
    @SerializedName("discount")
    String discount;
    @SerializedName("subtotal")
    String subtotal;

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getBenifit() {
        return benifit;
    }

    public void setBenifit(String benifit) {
        this.benifit = benifit;
    }

    @SerializedName("coupon_code")
    String coupon_code;
    @SerializedName("benifit")
    String benifit;
    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quanity) {
        this.quantity = quanity;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getProduct_images() {
        return product_images;
    }

    public void setProduct_images(String product_images) {
        this.product_images = product_images;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
