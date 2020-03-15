package com.fameless.blok.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParseAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();

        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.setDisplay(Display.SNACKBAR);
        appUpdater.setDisplay(Display.DIALOG);
        appUpdater.setDisplay(Display.NOTIFICATION);
        appUpdater.start();

    }

    private class Content extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://blokino.org/anime/raspisanie/";
                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("figure");

                int size = data.size();
                for (int i=1; i<size; i++){
                    String imgUrl = data.select("figure")
                            .select("img")
                            .eq(i)
                            .attr("src");

                    String title = data.select("figure")
                            .select("img")
                            .eq(i)
                            .attr("alt");

                    String detailUrl = data.select("figure")
                            .select("a")
                            .eq(i-1)
                            .attr("href");

                    parseItems.add(new ParseItem(imgUrl, title, detailUrl));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
