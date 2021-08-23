package com.example.mealkit.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealkit.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MealkitAdapter extends RecyclerView.Adapter<MealkitAdapter.ViewHolder> {
    Context context;
    ArrayList<HashMap<String, String>> menuList;

    public MealkitAdapter(FragmentActivity activity, ArrayList<HashMap<String, String>> menuList) {
        this.context=activity;
        this.menuList=menuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mealkit_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(menuList.get(position).get("img"))
                .circleCrop()
                .into(holder.img);
        holder.brand.setText(menuList.get(position).get("brand"));
        holder.name.setText(menuList.get(position).get("name"));
        holder.price.setText(menuList.get(position).get("price"));
    }

    @Override
    public int getItemCount() {
        return menuList==null?0:menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView brand, name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            brand=itemView.findViewById(R.id.brand);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
}