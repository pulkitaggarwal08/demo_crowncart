package com.innovative.crownkart.api;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public interface ApiCallback<T> {
    void onSuccess(T t);

    void onFailure(String message);
}
