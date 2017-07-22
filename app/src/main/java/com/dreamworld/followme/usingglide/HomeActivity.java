package com.dreamworld.followme.usingglide;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dreamworld.followme.CardAdapter;
import com.dreamworld.followme.Config;
import com.dreamworld.followme.GetBitmap;
import com.dreamworld.followme.R;
import com.dreamworld.followme.fetchcchedata.DataAdapterCache;
import com.dreamworld.followme.glideutills.GlideCacheListener;
import com.dreamworld.followme.glideutills.GlideUtils;
import com.dreamworld.followme.testcode.NetworkUtill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private RecyclerView.Adapter mRecyclerViewAdapter;
  private ConfigFile mConfigFile;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView)findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getConnectivityStatusString(this);
       // fetchData();
        //showData();
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
                createNetErrorDialog();
                Toast.makeText(this,""+status,Toast.LENGTH_LONG ).show();
                fetchData();
            } else if (conn == NetworkUtill.TYPE_NOT_CONNECTED) {
                status = "Not connected to Internet";
                Toast.makeText(this,""+status,Toast.LENGTH_LONG ).show();
               /* Snackbar.make(findViewById(android.R.id.content),"Need permission for enabling data",Snackbar.LENGTH_INDEFINITE).setAction("enable", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(HomeActivity.this,new String[]{Manifest.permission.ACCESS_WIFI_STATE},3);
                        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);
                    }
                }).show();*/
                createNetErrorDialog();
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
                Toast.makeText(getApplicationContext(),""+jsonData,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue lRequestQueue = Volley.newRequestQueue(this);
        lRequestQueue.add(lFetchData);

    }


    private void fetchData() {
        StringRequest lFetchData = new StringRequest(Request.Method.GET, ConfigFile.GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonData) {
             parseJson(jsonData);
                Toast.makeText(getApplicationContext(),""+jsonData,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue lRequestQueue = Volley.newRequestQueue(this);
        lRequestQueue.add(lFetchData);

    }

    private void parseJson(String jsonData ) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            mConfigFile = new ConfigFile(array.length());

            for(int i=0; i<=array.length(); i++){
                JSONObject fetchedData = array.getJSONObject(i);
                ConfigFile.cNames[i] = getName(fetchedData);
                ConfigFile.cUrl[i] = getURL(fetchedData);
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRecyclerViewAdapter = new DataAdapter(this,ConfigFile.cNames,ConfigFile.cUrl, ConfigFile.cImage);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }
    private void parseCacheJson(String jsonData ) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            mConfigFile = new ConfigFile(array.length());

            for(int i=0; i<=array.length(); i++){
                JSONObject fetchedData = array.getJSONObject(i);
                ConfigFile.cNames[i] = getName(fetchedData);
                ConfigFile.cUrl[i] = getURL(fetchedData);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            mRecyclerViewAdapter = new DataAdapterCache(this,ConfigFile.cNames,ConfigFile.cUrl, ConfigFile.cImage);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);

        }
        mRecyclerViewAdapter = new DataAdapterCache(this,ConfigFile.cNames,ConfigFile.cUrl, ConfigFile.cImage);
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

}

