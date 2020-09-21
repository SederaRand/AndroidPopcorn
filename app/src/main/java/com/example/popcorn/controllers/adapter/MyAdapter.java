package com.example.popcorn.controllers.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.popcorn.R;

import com.example.popcorn.controllers.activities.SingleContentActivity;
import com.example.popcorn.models.Content;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Content> list;
    public Context ctx;
    ArrayList<Content> searchList;
    private int mPost_key ;


    public MyAdapter(ArrayList<Content> list, Context ctx ){
        this.list = list;
        this.ctx= ctx;
        this.searchList = new ArrayList<Content>();
        this.searchList.addAll(list);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View result = LayoutInflater.from(ctx).inflate(R.layout.blog_row, viewGroup, false);

        return new MyViewHolder(result);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        holder.title.setText(list.get(position).getTitle());
        holder.details.setText(list.get(position).getDetails());
        Picasso.with(ctx).load(list.get(position).getImage()).into(holder.image);
        list.get(position);
        Log.d("id", String.valueOf(list));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, SingleContentActivity.class);
                intent.putExtra("ITEM_TITLE", list.get(position).getTitle());
                intent.putExtra("ITEM_DETAILS", list.get(position).getDetails());
                intent.putExtra("ITEM_IMAGE", list.get(position).getImage());

                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, details;
        ImageView image;
        public MyViewHolder(View itemView){
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.post_title);
            details = (TextView)itemView.findViewById(R.id.post_details);
            image = (ImageView) itemView.findViewById(R.id.post_image);
        }


    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();

        if (charText.length() == 0) {
            list.addAll(searchList);
        } else {
            for (Content content: searchList) {
                if (content.getDetails().toLowerCase(Locale.getDefault()).contains(charText) || content.getTitle().toLowerCase(Locale.getDefault()).contains(charText) ) {
                    list.add(content);
                }
            }
        }
        notifyDataSetChanged();
    }


}
