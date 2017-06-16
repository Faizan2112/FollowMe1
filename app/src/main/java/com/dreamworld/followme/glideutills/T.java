package com.dreamworld.followme.glideutills;

import android.app.Activity;
import android.widget.Toast;


public class T {
    private T() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     *
     *
     *
     * @param activity
     * @param message
     */
    public static void S(final Activity activity, final int message) {
        show(activity, message, Toast.LENGTH_SHORT);
    }

    /**

     *
     * @param activity
     * @param message
     */
    public static void S(final Activity activity, final String message) {
        show(activity, message, Toast.LENGTH_SHORT);
    }

    /**

     *
     * @param activity
     * @param message
     */
    public static void SO(final Activity activity, final Object message) {
        show(activity, message.toString(), Toast.LENGTH_SHORT);
    }

    /**

     *
     * @param activity
     * @param message
     */
    public static void L(final Activity activity, final int message) {
        show(activity, message, Toast.LENGTH_LONG);
    }

    /**

     *
     * @param activity
     * @param message
     */
    public static void L(final Activity activity, final String message) {
        show(activity, message, Toast.LENGTH_LONG);
    }

    /**

     *
     * @param activity
     * @param message
     */
    public static void LO(final Activity activity, final Object message) {
        show(activity, message.toString(), Toast.LENGTH_LONG);
    }

    /**
     *
     *
     * @param activity
     * @param message
     * @param duration
     */
    public static void D(final Activity activity, final int message, final int duration) {
        show(activity, message, duration);
    }

    /**
     *
     * @param activity
     * @param message
     * @param duration
     */
    public static void D(final Activity activity, final String message, final int duration) {
        show(activity, message, duration);
    }

    /**

     *
     * @param activity
     * @param message
     * @param duration
     */
    public static void DO(final Activity activity, final Object message, final int duration) {
        show(activity, message.toString(), duration);
    }

    private static void show(final Activity activity, final Object message, final int duration) {
        if (isShow)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, message.toString(), duration).show();
                }
            });
    }
}
