package com.innovative.crownkart.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;

import com.innovative.crownkart.R;
import com.innovative.crownkart.activities.RegisterActivity;

/**
 * Created by Pulkit on 10-Jun-17.
 */

public class SnackbarUtil {
    public static void showShortSnackbar(Activity activity, String message){
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    public static Snackbar showLongSnackbar(Activity activity, String message) {
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
        return null;
    }
}
