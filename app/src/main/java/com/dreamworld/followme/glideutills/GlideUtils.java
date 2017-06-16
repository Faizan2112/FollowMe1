package com.dreamworld.followme.glideutills;

import android.content.Context;

import java.io.File;
import java.util.List;



public class GlideUtils {


    public static String getCache(Context context, String fileUrl) {
        String path = context.getExternalCacheDir() + Md5.getFileName(fileUrl);
        return haveCache(context, fileUrl) ? path : null;
    }

    /**

     *
     * @param context
     * @param fileUrl
     * @return
     */
    public static boolean haveCache(Context context, String fileUrl) {
        return FileUtils.fileIsExists(context.getExternalCacheDir() + Md5.getFileName(fileUrl));
    }

    /**

     * @param fileUrl
     * @param context
     */
    public static void cacheImage(String fileUrl, Context context) {
        cacheImage(fileUrl, context, null, null);
    }

    /**

     * @param fileUrl
     * @param context
     * @param cacheFile
     */
    public static void cacheImage(String fileUrl, Context context, File cacheFile) {
        cacheImage(fileUrl, context, cacheFile, null);
    }

    /**

     *
     * @param fileUrl
     * @param context
     * @param listener
     */
    public static void cacheImage(String fileUrl, Context context, GlideCacheListener listener) {
        cacheImage(fileUrl, context, null, listener);
    }

    /**

     *
     * @param fileUrl
     * @param context
     * @param cacheFile
     * @param listener
     */
    public static void cacheImage(String fileUrl, Context context, File cacheFile, GlideCacheListener listener) {
        new GlideCacheTask(context, cacheFile, listener).execute(fileUrl);
    }

    /**

     * @param fileUrls
     * @param context
     */
    public static void cacheImage(List<String> fileUrls, Context context ) {
        for (String fileUrl : fileUrls) {
            cacheImage(fileUrl, context, null, null);
        }
    }

    /**

     * @param fileUrls
     * @param context
     * @param cacheFile
     */
    public static void cacheImage(List<String> fileUrls, Context context, File cacheFile) {
        for (String fileUrl : fileUrls) {
            cacheImage(fileUrl, context, cacheFile, null);
        }
    }
}
