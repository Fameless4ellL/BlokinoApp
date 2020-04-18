package com.fameless.blok.NewActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.fameless.blok.ThirdActivity.ActivityForSerial;
import com.fameless.blok.fourthActivity.Chooser;

import java.util.ArrayList;

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.ViewHolder>{

    private ArrayList<News_Item> news_items;
    private Context context;

    public News_Adapter(ArrayList<News_Item> news_items, Context context){
        this.news_items = news_items;
        this.context = context;
    }

    @NonNull
    @Override
    public News_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_Adapter.ViewHolder holder, int position) {
        News_Item news_item = news_items.get(position);
        holder.textView1.setText(news_item.getSeason());
        holder.textView.setText(news_item.getTxt());
    }

    @Override
    public int getItemCount() {
        return news_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {




        TextView textView, textView1;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView22);
            textView1 = itemView.findViewById(R.id.textView10);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            News_Item news_item = news_items.get(position);
            new AlertDialog.Builder(context, R.style.Theme_AppCompat_DayNight_Dialog)
                    .setTitle("Выберите ссылку")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(news_item.getTitle(), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (news_item.getTitle()=="_") {
                                Toast.makeText(context, "Ссылка не найдена", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    Intent intent = new Intent(context, ActivityForSerial.class);
                                    intent.putExtra("DetailUrl", news_item.getDetailTitleUrl());
                                    context.startActivity(intent);
                                    // Continue with delete operation
                                } catch (Exception e) {
                                    Toast.makeText(context, "Ссылка не найдена", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(news_item.getSerial(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (news_item.getTitle()=="_") {
                                Toast.makeText(context, "Ссылка не найдена", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    Intent intent = new Intent(context, Chooser.class);
                                    intent.putExtra("DetailUrl", news_item.getDetailSerialUrl());
                                    context.startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(context, "Ссылка не найдена", Toast.LENGTH_SHORT).show();
                                }
                            }


                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();






        }
    }
}
