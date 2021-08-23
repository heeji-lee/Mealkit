package com.example.mealkit.ui.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mealkit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MealkitActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> menuList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealkit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url="http://118.38.164.174/mealkit_list.php";
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MealkitActivity.this));
        recyclerView.setAdapter(new MealkitAdapter(MealkitActivity.this, menuList));
        RequestQueue requestQueue = Volley.newRequestQueue(MealkitActivity.this);
        menuList=new ArrayList<HashMap<String, String>>();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray prods = jsonObject.getJSONArray("result");
                    for (int i = 0; i < prods.length(); i++) {
                        JSONObject json = prods.getJSONObject(i);
                        HashMap<String, String> menus = new HashMap<String, String>();
                        menus.put("id", json.getString("id"));
                        menus.put("category", json.getString("category"));
                        menus.put("img", json.getString("img"));
                        menus.put("brand", json.getString("brand"));
                        menus.put("name", json.getString("name"));
                        menus.put("price", json.getString("price")+"원");
                        menuList.add(menus);
                        MealkitAdapter adapter = new MealkitAdapter(MealkitActivity.this, menuList);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MealkitActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}