package com.fameless.blok.ThirdActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.fameless.blok.fourthActivity.Chooser;

import java.util.ArrayList;

public class AdapterForSerial extends RecyclerView.Adapter<AdapterForSerial.ViewHolder> {

    private ArrayList<ItemSerial> itemSerials;
    private Context context;

    public AdapterForSerial(ArrayList<ItemSerial> itemSerials, Context context){
        this.itemSerials = itemSerials;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterForSerial.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serial_video,parent, false);
        return new AdapterForSerial.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForSerial.ViewHolder holder, int position) {
        ItemSerial itemSerial = itemSerials.get(position);
        holder.textView.setText(itemSerial.getTitleSerial());
        holder.textView2.setText(itemSerial.getTextSerial());
        //holder.textView3.setText(itemSerial.getDetailUrlSerial());

    }

    @Override
    public int getItemCount() {
        return itemSerials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView, textView2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            textView2 = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ItemSerial itemSerial = itemSerials.get(position);

            Intent intent = new Intent(context, Chooser.class);
            intent.putExtra("title", itemSerial.getTitleSerial());
            intent.putExtra("description", itemSerial.getTextSerial());
            intent.putExtra("DetailUrl", itemSerial.getDetailUrlSerial());
            context.startActivity(intent);
        }
    }
}
