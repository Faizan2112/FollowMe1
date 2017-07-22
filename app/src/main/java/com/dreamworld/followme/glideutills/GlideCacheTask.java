package com.dreamworld.followme.glideutills;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;



public class GlideCacheTask extends AsyncTask<String, Void, File> {
    private Context context;
    private String imgUrl;
    private File cacheFile;
    private GlideCacheListener listener;

//    public GlideCacheTask(Context context) {
//        this(context, null, null);
//    }
//
//    public GlideCacheTask(Context context, GlideCacheListener listener) {
//        this(context, null, listener);
//    }
//
//    public GlideCacheTask(Context context, File cacheFile) {
//        this(context, cacheFile, null);
//    }

    public GlideCacheTask(Context context, File cacheFile, GlideCacheListener listener) {
        this.context = context;
        this.cacheFile = cacheFile == null?context.getExternalCacheDir():cacheFile;
        this.listener = listener;
    }

    @Override
    protected File doInBackground(String... params) {
        imgUrl = params[0];
        File oldfile;
        File newfile = new File(cacheFile+ Md5.getFileName(imgUrl));
        try {
            oldfile = Glide.with(context)
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
//            throw new NullPointerException();
        } catch (Exception ex) {
            if (listener != null) {
                listener.error(ex);
            }
            oldfile = null;
        }
        if (oldfile != null) {

//        copyFile(path, Environment.getExternalStorageDirectory().getAbsolutePath() + "/#gh/"+ Md5.getFileName(imgUrl));
//        copyFile(path, context.getExternalCacheDir()+ Md5.getFileName(imgUrl));
            copyFile(oldfile.getAbsolutePath(), newfile.getAbsolutePath());
        }
        return newfile;
    }

    @Override
    protected void onPostExecute(File result) {

    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int byteRead;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();

                if (listener != null) {
                    listener.success(newPath);
                }
            } else {
                if (listener != null) {
                    listener.error(new NullPointerException("csdcsdcs"));
                }
            }
//            throw new RuntimeException();
        }
        catch (Exception e) {
            if (listener != null) {
                listener.error(e);

            }

            e.printStackTrace();
        }
    }
}
