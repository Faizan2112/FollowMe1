package com.dreamworld.followme.usingglide;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dreamworld.followme.Config;
import com.dreamworld.followme.R;
import com.dreamworld.followme.fetchcchedata.DataAdapterCache;
import com.dreamworld.followme.testcode.NetworkUtill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ProgressDialog progress;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private ConfigFile mConfigFile;
    private DataAdapter adapter;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    Bitmap downloadBitmap;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getConnectivityStatusString(this);
        // fetchData();
        //showData();
//        Bitmap b= loadImageFromStorage(String path, String name)
//        ImageView img=(ImageView)findViewById(R.id.your_image_id);
//        img.setImageBitmap(b);
         verifyStoragePermissions(this) ;

    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



    public String getConnectivityStatusString(Context context) {
        int conn = NetworkUtill.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtill.TYPE_WIFI) {
            status = "Wifi enabled";


            //     Snackbar.make(this,)
            //       Toast.makeText(this,""+status,Toast.LENGTH_LONG ).show();
            //       fetchData();

        } else if (conn == NetworkUtill.TYPE_MOBILE) {
            status = "Mobile data enabled";
            //   createNetErrorDialog();
            Toast.makeText(this, "" + status, Toast.LENGTH_LONG).show();
            fetchData();
        } else if (conn == NetworkUtill.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
            Toast.makeText(this, "" + status, Toast.LENGTH_LONG).show();
               /* Snackbar.make(findViewById(android.R.id.content),"Need permission for enabling data",Snackbar.LENGTH_INDEFINITE).setAction("enable", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.ACCESS_WIFI_STATE},3);
                        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);
                    }
                }).show();*/
            //  createNetErrorDialog();
        }
        return status;
    }

    private void createNetErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need internet connection for this app. Please turn on mobile network or Wi-Fi in Settings.")
                .setTitle("Unable to connect")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                Intent i = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                                startActivity(i);
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HomeActivity.this.finish();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void fetchCacheData() {
        //String url = GlideUtils.getCache(getApplication().getApplicationContext(),ConfigFile.GET_URL);
        StringRequest lFetchData = new StringRequest(Request.Method.GET, ConfigFile.GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonData) {
                parseCacheJson(jsonData);
                Toast.makeText(getApplicationContext(), "" + jsonData, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue lRequestQueue = Volley.newRequestQueue(this);
        lRequestQueue.add(lFetchData);

    }


    private void fetchData() {
        progress=new ProgressDialog(this);
        progress.setMessage("Downloading Music");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);

        StringRequest lFetchData = new StringRequest(Request.Method.GET, ConfigFile.GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonData) {
                progress.show();
                parseJson(jsonData);
                Toast.makeText(getApplicationContext(), "" + jsonData, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue lRequestQueue = Volley.newRequestQueue(this);
        lRequestQueue.add(lFetchData);
  progress.dismiss();
    }

    private void parseJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            mConfigFile = new ConfigFile(array.length());

            for (int i = 0; i <= array.length(); i++) {
                JSONObject fetchedData = array.getJSONObject(i);
                ConfigFile.cNames[i] = getName(fetchedData);
                ConfigFile.cUrl[i] = getURL(fetchedData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRecyclerViewAdapter = new DataAdapter(this, ConfigFile.cNames, ConfigFile.cUrl, ConfigFile.cImage);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        adapter = (DataAdapter) mRecyclerViewAdapter;
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Datamodel item) {
                Toast.makeText(HomeActivity.this, item.getmUrls(), Toast.LENGTH_LONG).show();
               // ContextWrapper cw = new ContextWrapper(HomeActivity.this);
                // path to /data/data/yourapp/app_data/imageDir

                String name_="foldername"; //Folder name in device android/data/
               // File directory = cw.getDir(name_, Context.MODE_PRIVATE);
               // File path =Environment.getExternalStorageDirectory();
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name_);
                new DownloadTask(HomeActivity.this,file,"downloading").execute(item.getmUrls());
                //dt.execute();
              //  downloadBitmap = Glide.with(HomeActivity.this).load(response).asBitmap().into(-1,-1).get();
             //   saveToInternalStrorage(downloadBitmap,getApplicationContext(),"faizn");

                    // cant use thread here for accesing ui data
                    // we have run this tast in bacground to dowload

//                Queue q = new RequestQueue(sr);


            }
        });




      /*  mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerViewObject,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // view is the clicked view (the one you wanted
                                // position is its position in the adapter
                            }
                            @Override public void onLongItemClick(View view, int position) {
                            }
                        }
                )
        );*/

    }

    private String saveToInternalStrorage(Bitmap bitmapImage, Context context, String name) {

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir

        String name_="foldername"; //Folder name in device android/data/
        File directory = cw.getDir(name_, Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("absolutepath ", directory.getAbsolutePath());
        return directory.getAbsolutePath();
    }



    private void parseCacheJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            mConfigFile = new ConfigFile(array.length());

            for (int i = 0; i <= array.length(); i++) {
                JSONObject fetchedData = array.getJSONObject(i);
                ConfigFile.cNames[i] = getName(fetchedData);
                ConfigFile.cUrl[i] = getURL(fetchedData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            mRecyclerViewAdapter = new DataAdapterCache(this, ConfigFile.cNames, ConfigFile.cUrl, ConfigFile.cImage);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);

        }
        mRecyclerViewAdapter = new DataAdapterCache(this, ConfigFile.cNames, ConfigFile.cUrl, ConfigFile.cImage);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }

    private String getName(JSONObject fetchedData) {
        String name = null;
        try {
            name = fetchedData.getString(Config.TAG_IMAGE_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;

    }

    private String getURL(JSONObject fetchedData) {
        String url = null;
        try {
            url = fetchedData.getString(Config.TAG_IMAGE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }
    public Bitmap loadImageFromStorage(String path, String name)
    {
        Bitmap b;
        String name_="foldername";
        try {
            File f=new File(path, name_);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** Retrieve your image from device and set to imageview **/
//Provide your image path and name of the image your previously used.


}

