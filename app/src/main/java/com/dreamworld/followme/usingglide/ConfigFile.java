package com.dreamworld.followme.usingglide;

import android.graphics.Bitmap;

/**
 * Created by faizan on 15/06/2017.
 */

public class ConfigFile {
    public static String[] cNames;
    public static String[] cUrl;
    public static Bitmap[] cImage;


    // fetch data tags
    public static final String GET_URL = "http://faizandream21.000webhostapp.com/PhotoUploadWithText/getImage.php";
    public static final String GET_URL_CACHE = "http://faizandream21.000webhostapp.com/PhotoUploadWithText/getImage.php";

    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_IMAGE_NAME = "name";
    public static final String TAG_JSON_ARRAY="result";


    //creating pramaterized constrcter
    //and initilizing array
    public ConfigFile(int i)
    {
        cNames = new String[i];
        cUrl= new String[i];
        cImage = new Bitmap[i];

    }

}
