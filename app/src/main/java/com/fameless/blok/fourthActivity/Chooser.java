package com.fameless.blok.fourthActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chooser extends AppCompatActivity {

    private String detailinfo;
    View view;

    private RecyclerView recyclerView, recyclerView1;
    private ProgressBar progressBar;
    private ChooserAdapter adapter;
    private ArrayList<ItemChooser> itemChoosers = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recycleviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChooserAdapter(itemChoosers, this);
        recyclerView.setAdapter(adapter);

        //view=this.getWindow().getDecorView();
        //view.setBackgroundColor(R.color.black);


        //RecyclerView.ItemDecoration itemDecoration =
        //        new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setNestedScrollingEnabled(false);


        getSupportActionBar().setTitle("Video ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Content content = new Content();
        content.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // This is the home/back button
                onBackPressed(); // Handle what to do on home/back press
                break;
        }

        return false;
    }

    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(Chooser.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(Chooser.this, android.R.anim.fade_out));
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

                Elements data = doc.select("option");
                //Elements data1 = doc.select("li[id]");

                detailinfo = data.select("div.opisanie")
                        .text();

                int size = data.size();
                for (int i=0; i<size; i++){
                    String chooserLang = data.select("option")
                            .eq(i)
                            .text();
                    String VideoUrlDetail = data.select("option")
                            .eq(i)
                            .attr("Value");
                    String VideoUrlDetail1 = VideoUrlDetail.substring(VideoUrlDetail.indexOf("|")+0).trim();
                    VideoUrlDetail = VideoUrlDetail.replace(VideoUrlDetail1, "");
                    int doc2 = Jsoup.connect(VideoUrlDetail).response().statusCode();
                    if(doc2 != 404){
                        try {
                            Document doc1 = Jsoup.connect(VideoUrlDetail)
                                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                                    .get();
                            Elements data2 = doc1.select("body");

                            String VideoUrl1 = String.valueOf(data2.select("script")
                                    .eq(1));
                            List<String> urls = new ArrayList<String>();
                            String myUrlPattern = "((https?|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                            Pattern p = Pattern.compile(myUrlPattern);
                            Matcher m = p.matcher(VideoUrl1);
                            while (m.find()) {
                                urls.add(m.group());
                            }
                            String VideoUrl = urls.get(2);
                            itemChoosers.add(new ItemChooser(VideoUrl, chooserLang, VideoUrlDetail));
                        } catch (Exception e) {
                            itemChoosers.add(new ItemChooser("1", chooserLang, VideoUrlDetail));
                        }
                    }


                }
                // second recycleview


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
