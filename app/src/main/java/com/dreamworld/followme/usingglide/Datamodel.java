package com.dreamworld.followme.usingglide;

import android.graphics.Bitmap;

/**
 * Created by faizan on 12/06/2017.
 */

public class Datamodel {
    private String mName ;
    private String mUrls;
    private Bitmap mImage;

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }



    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmUrls() {
        return mUrls;
    }

    public void setmUrls(String mUrls) {
        this.mUrls = mUrls;
    }


}
