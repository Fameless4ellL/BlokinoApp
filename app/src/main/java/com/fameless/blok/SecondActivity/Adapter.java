package com.fameless.blok.SecondActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.fameless.blok.ThirdActivity.ActivityForSerial;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<item> items;
    private Context context;

    public Adapter(ArrayList<item> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serial_layout,parent, false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        item item = items.get(position);
        holder.textView.setText(item.getWeek());
        holder.textView2.setText(item.getTitle());
        holder.textView3.setText(item.getText());
        holder.textView4.setText(item.getGrade());
        Picasso.get().load(item.getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView, textView2, textView3, textView4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView1);
            textView2 = itemView.findViewById(R.id.textView4);
            textView3 = itemView.findViewById(R.id.textView2);
            textView4 = itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            item item = items.get(position);

            Intent intent = new Intent(context, ActivityForSerial.class);
            intent.putExtra("week", item.getWeek());
            intent.putExtra("title", item.getTitle());
            intent.putExtra("Img", item.getImg());
            intent.putExtra("Text", item.getText());
            intent.putExtra("DetailUrl", item.getDetailUrl());
            intent.putExtra("Grade", item.getGrade());
            context.startActivity(intent);
        }
    }
}
