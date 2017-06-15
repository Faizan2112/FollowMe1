package com.dreamworld.followme.usingglide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dreamworld.followme.CardAdapter;
import com.dreamworld.followme.Config;
import com.dreamworld.followme.GetBitmap;
import com.dreamworld.followme.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
  private RecyclerView mRecyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private RecyclerView.Adapter mRecyclerViewAdapter;
  private ConfigFile mConfigFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView)findViewById(R.id.home_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        fetchData();
        //showData();
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
    public void showData(){

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

