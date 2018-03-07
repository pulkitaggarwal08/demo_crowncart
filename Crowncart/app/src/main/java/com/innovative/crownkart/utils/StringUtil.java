package com.innovative.crownkart.utils;

import android.support.annotation.Nullable;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public class StringUtil {
    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.toString().trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
