package com.fameless.blok.ThirdActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ActivityForSerial extends AppCompatActivity {

    private ImageView dateilImgView;
    private TextView detailTextView, detailgradeView, detailtagView;
    private String detailinfo, detailimg, detailgrade, detailtag;

    private RecyclerView recyclerView;
    private AdapterForSerial adapter;
    private ArrayList<ItemSerial> itemSerials = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_serial);

        dateilImgView = findViewById(R.id.imageView2);
        detailTextView = findViewById(R.id.textView20);
        detailgradeView = findViewById(R.id.textView17);
        detailtagView = findViewById(R.id.textView19);


        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterForSerial(itemSerials, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);


        getSupportActionBar().setTitle("Аниме ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Content content = new Content();
        content.execute();


    }

    private class Content extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            detailTextView.setText(detailinfo);
            Picasso.get().load(detailimg).into(dateilImgView);
            detailgradeView.setText(detailgrade);
            detailtagView.setText(detailtag);
            adapter.notifyDataSetChanged();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String detailUrl = getIntent().getStringExtra("DetailUrl");
                String url = detailUrl;
                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.post");
                Elements data1 = doc.select("li[id]");

                detailinfo = data.select("div.opisanie")
                        .text();
                detailimg = data.select("img")
                        .attr("src");
                detailgrade = data.select("a.ratingValue")
                        .select("span")
                        .text();
                detailtag = data.select("a.jan")
                        .text();

                int size = data1.size();
                for (int i=0; i<size; i++){
                    String description = data.select("li[id] > span")
                            .select("span")
                            .eq(i)
                            .text();
                    String title = data.select("li[id]")
                            .select("a")
                            .eq(i)
                            .attr("title");

                    String DetailUrl = data.select("li[id]")
                            .eq(i)
                            .attr("id");

                    itemSerials.add(new ItemSerial(title, description, DetailUrl));
                    //Log.d("items", "title: " + title + ". text: " + description);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
