package com.guggiemedia.fibermetric.ui.utility;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 *
 */
public class ToastHelper {

    public static void show(int message, Context context) {
        show(context.getString(message), context);
    }

    public static void show(String message, Context context) {
        // Switch from Toast to Snackbar
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        showSnackbar(context, message);
    }

    public static void show(View view, String message) {
        showSnackbar(view, message, null, null, null, null);
    }

    /**
     * @param context
     * @param message
     * @param buttonText
     * @param listener
     * @param onDismissedCallback
     * @param duration
     */
    public static void showSnackbar(Context context, String message,
                                    String buttonText, View.OnClickListener listener,
                                    Snackbar.Callback onDismissedCallback, Integer duration) {

        View view = ((Activity) context).findViewById(android.R.id.content);

        showSnackbar(view, message, buttonText, listener, onDismissedCallback, duration);
    }

    /**
     * @param context
     * @param message
     * @param buttonText
     * @param listener
     * @param onDismissedCallback
     */
    public static void showSnackbar(Context context, String message,
                                    String buttonText, View.OnClickListener listener,
                                    Snackbar.Callback onDismissedCallback) {
        showSnackbar(context, message, buttonText, listener,
                onDismissedCallback, Snackbar.LENGTH_LONG);
    }

    /**
     * @param context
     * @param message
     */
    public static void showSnackbar(Context context, String message) {
        View view = ((Activity) context).findViewById(android.R.id.content);

        showSnackbar(view, message, null, null, null, null);
    }

    /**
     * @param view
     * @param message
     * @param buttonText
     * @param listener
     * @param onDismissedCallback
     */
    public static void showSnackbar(View view, String message,
                                    String buttonText, View.OnClickListener listener,
                                    Snackbar.Callback onDismissedCallback, Integer duration) {

        duration = (duration == null) ? Snackbar.LENGTH_LONG : duration;

        Snackbar snackbar = Snackbar.make(view, message, duration);

        if (listener != null) {
            snackbar.setAction(buttonText, listener);
        }

        View snackBarView = snackbar.getView();

        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);

        tv.setTextColor(Color.WHITE);

        if (onDismissedCallback != null) {
            snackbar.setCallback(onDismissedCallback);
        }

        snackbar.show();
    }
}
