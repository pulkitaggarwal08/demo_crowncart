package com.innovative.crownkart.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lavisha_User on 7/3/2017.
 */

public class SizeInfoDTo {
    @SerializedName("size")
    private String size;
    @SerializedName("qty")
    private String quantity;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
