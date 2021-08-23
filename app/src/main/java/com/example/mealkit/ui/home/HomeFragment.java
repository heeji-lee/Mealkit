package com.example.mealkit.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mealkit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {
    ImageView new_img1,new_img2;
    TextView new_name1,new_price1,new_name2,new_price2;
    ImageView best_img1,best_img2,best_img3;
    TextView best_name1,best_price1,best_name2,best_price2,best_name3,best_price3;

    ImageView mealkit_all;

    ArrayList<HashMap<String, String>> menuList;

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        new_img1=root.findViewById(R.id.new_img1);
        new_name1=root.findViewById(R.id.new_name1);
        new_price1=root.findViewById(R.id.new_price1);
        new_img2=root.findViewById(R.id.new_img2);
        new_name2=root.findViewById(R.id.new_name2);
        new_price2=root.findViewById(R.id.new_price2);

        best_img1=root.findViewById(R.id.best_img1);
        best_name1=root.findViewById(R.id.best_name1);
        best_price1=root.findViewById(R.id.best_price1);
        best_img2=root.findViewById(R.id.best_img2);
        best_name2=root.findViewById(R.id.best_name2);
        best_price2=root.findViewById(R.id.best_price2);
        best_img3=root.findViewById(R.id.best_img3);
        best_name3=root.findViewById(R.id.best_name3);
        best_price3=root.findViewById(R.id.best_price3);

        String url="http://118.38.164.174/mealkit_list.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray new_prods = jsonObject.getJSONArray("new");
                    for (int i = 0; i < new_prods.length(); i++) {
                        JSONObject json = new_prods.getJSONObject(i);
                        String img_url = json.getString("img");
                        String name = json.getString("name");
                        String price = json.getString("price");

                        if(i==0){
                            Glide.with(getActivity())
                                    .load(img_url)
                                    .into(new_img1);
                            new_name1.setText(name);
                            new_price1.setText(price+"원");
                        } else if(i==1) {
                            Glide.with(getActivity())
                                    .load(img_url)
                                    .into(new_img2);
                            new_name2.setText(name);
                            new_price2.setText(price+"원");
                        }
                    }

                    JSONArray best_prods = jsonObject.getJSONArray("best");
                    for (int i = 0; i < best_prods.length(); i++) {
                        JSONObject json = best_prods.getJSONObject(i);
                        String img_url = json.getString("img");
                        String brand = json.getString("brand");
                        String name = json.getString("name");
                        String price = json.getString("price");

                        if(i==0){
                            Glide.with(getActivity())
                                    .load(img_url)
                                    .into(best_img1);
                            best_name1.setText(name);
                            best_price1.setText(price+"원");
                        } else if(i==1) {
                            Glide.with(getActivity())
                                    .load(img_url)
                                    .into(best_img2);
                            best_name2.setText(name);
                            best_price2.setText(price+"원");
                        } else if(i==2) {
                            Glide.with(getActivity())
                                    .load(img_url)
                                    .into(best_img3);
                            best_name3.setText(name);
                            best_price3.setText(price + "원");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

        mealkit_all=root.findViewById(R.id.mealkit_all);
        mealkit_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MealkitActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.notifications:

            case R.id.coupon:
//                transaction.replace(R.id., new SubFragment());
                transaction.commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}