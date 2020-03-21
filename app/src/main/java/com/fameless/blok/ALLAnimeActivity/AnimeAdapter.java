package com.fameless.blok.ALLAnimeActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.fameless.blok.ThirdActivity.ActivityForSerial;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.ViewHolder> implements Filterable {

    private ArrayList<AnimeItem> animeItems;
    private ArrayList<AnimeItem> animeFull;// kek
    private Context context;

    public AnimeAdapter(ArrayList<AnimeItem> animeItems, Context context){
        this.animeItems = animeItems;
        this.context = context;
        animeFull = new ArrayList<>(animeItems);// kek
    }

    @NonNull
    @Override
    public AnimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allanime_layout,parent, false);
        return new AnimeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeAdapter.ViewHolder holder, int position) {
        AnimeItem animeItem = animeItems.get(position);
        holder.textView2.setText(animeItem.getTitle());
        holder.textView3.setText(animeItem.getText());
        holder.textView4.setText(animeItem.getGrade());
        Picasso.get().load(animeItem.getImg()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return animeItems.size();
    }

    @Override
    public Filter getFilter() {
        return AnimeFilter;
    }

    private Filter AnimeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<AnimeItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(animeFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (AnimeItem item: animeFull){
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            animeItems.clear();
            animeItems.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView2, textView3, textView4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView4);
            textView4 = itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            AnimeItem animeItem = animeItems.get(position);

            Intent intent = new Intent(context, ActivityForSerial.class);
            intent.putExtra("title", animeItem.getTitle());
            intent.putExtra("Img", animeItem.getImg());
            intent.putExtra("Text", animeItem.getText());
            intent.putExtra("DetailUrl", animeItem.getDetailUrl());
            intent.putExtra("Grade", animeItem.getGrade());
            context.startActivity(intent);

        }
    }
}
