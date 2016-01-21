package com.example.bheemesh.myapplication.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.bheemesh.myapplication.http.HttpEngine;

/**
 * Created by bheemesh on 1/1/16.
 */
public class Utils {

    private static Application application;

    public static Application getApplication() {
        return application;
    }
    // Check if a string is empty or null
    public static boolean isEmpty(String str) {
        return !(str != null && !(str.trim()).equals(Constants.EMPTY_STRING));
    }

    /**
     * method to check email and redirecting user to next screen
     */
    public static int getDpFromPixels(int pixel, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pixel / scale);
    }

    public static boolean isEmptyWithoutTrim(String str) {
        if (str != null && (str.length() > 0)) {
            return false;
        }
        return true;
    }

    /**
     * method to check email and redirecting user to next screen
     */
    public static boolean validateEmailAddress(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    /**
     * This method convets dp unit to equivalent device specific value in
     * pixels.
     */
    public static int getPixelFromDP(int dp, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }

    public static int getDeviceScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getDeviceScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static Drawable getDrawable(Context context, int resId) {
        if (Build.VERSION.SDK_INT < 21) {
            return context.getResources().getDrawable(resId);
        } else {
            return context.getDrawable(resId);
        }
    }

    public static boolean isAppInstalled(Activity activity, String packageName) {
        try {
            activity.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        } catch (Exception e) {
            // some other exception and still return false
            return false;
        }
    }

    public static void showKeyBoard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        editText.requestFocus();
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
