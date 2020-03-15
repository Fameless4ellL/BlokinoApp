package com.fameless.blok.fourthActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.fameless.blok.VideoPlayerActivity;
import com.fameless.blok.WebPageActivity;

import java.util.ArrayList;

public class ChooserAdapter extends RecyclerView.Adapter<ChooserAdapter.ViewHolder> {

    private static ArrayList<ItemChooser> itemChoosers;
    private static Context context;

    public ChooserAdapter(ArrayList<ItemChooser> itemChoosers, Context context){
        this.itemChoosers = itemChoosers;
        this.context = context;
    }


    @NonNull
    @Override
    public ChooserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video,parent, false);
        return new ChooserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooserAdapter.ViewHolder holder, int position) {
        ItemChooser itemChooser = itemChoosers.get(position);
        holder.textView.setText(itemChooser.getChooserLang());
        //holder.textView2.setText(itemChooser.getVideoUrl());

    }

    @Override
    public int getItemCount() { return itemChoosers.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView, textView2;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title1);
            //textView2 = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ItemChooser itemChooser = itemChoosers.get(position);

            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("title", itemChooser.getChooserLang());
            if (itemChooser.getVideoUrl() == String.valueOf("1")){
                Intent intent1 = new Intent(context, WebPageActivity.class);
                intent1.putExtra("VideoUrl", itemChooser.getVideoUrlDetail());
                context.startActivity(intent1);
            } else {
                intent.putExtra("VideoUrlDetail", itemChooser.getVideoUrl());

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                final android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context
//                        .getSystemService(Context.CLIPBOARD_SERVICE);
//                final android.content.ClipData clipData = android.content.ClipData
//                        .newPlainText("Copy", itemChooser.getVideoUrl());
//                clipboardManager.setPrimaryClip(clipData);
//                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
//
//            } else {
//                final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context
//                        .getSystemService(Context.CLIPBOARD_SERVICE);
//                clipboardManager.setText(itemChooser.getVideoUrl());
//                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
//
//            }

                context.startActivity(intent);
            }
        }
    }
}
