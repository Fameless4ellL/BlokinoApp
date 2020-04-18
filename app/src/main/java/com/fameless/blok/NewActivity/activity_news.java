package com.fameless.blok.NewActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fameless.blok.ALLAnimeActivity.Anime_Activity;
import com.fameless.blok.R;
import com.fameless.blok.main.MainActivity;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class activity_news extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private News_Adapter adapter;
    private ArrayList<News_Item> news_items = new ArrayList<>();
    private ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Content content = new Content();
                content.execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new News_Adapter(news_items, this);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_News);
        }

        Content content = new Content();
        content.execute();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_anime:
                Intent intent = new Intent(this, Anime_Activity.class);
                startActivity(intent);
                break;
            case R.id.nav_Schedule:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_News:
                Intent intent2 = new Intent(this, activity_news.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(activity_news.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(activity_news.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://blokino.org";
                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.kalendar");
                Elements data1 = data.select("div.zavernuto");
                int d=0, x=0, y=1,f=0, k=0;

                int size = data1.size();
                for (int i=1; i<size; i++){
                    String chislo = "_", season = "";
                    String size1 = data1.get(i).toString();
                    //int x = size1.indexOf("dobavlenoblu");
                    //int y = size1.indexOf("dobavlenored");
                    //int z = size1.indexOf("dobavlenoR");
                    if (size1.contains("datachislo")==true){
                        chislo = data.select("span.datachislo")
                                .eq(d)
                                .text();
                        String season1 = data.select("div.zavernuto")
                                .eq(i)
                                .text();
                        season = season1.replace(chislo, " ");
                        d++;
                        news_items.add(new News_Item("_", chislo+""+season, "_", "", "_", "_"));
                        Log.d(" datachislo", chislo +" "+ season);
                    }
                    if (size1.contains("razdel")){
                        String title= "_", titleUrl = "_", serial = "_", serialUrl = "_", Txt = "";
                        String word = "class=\"razdel\"";
                        String temp[] = size1.split(" ");
                        int count = 0;
                        for (int j = 0; j < temp.length; j++) {
                            if (word.equals(temp[j]))
                                count++;
                        }
                        if (count >= 2){
                            x=x+count;
                            x=x-1;
                        }
                        title = data.select("a.razdel")
                                .eq(x)
                                .text();
                        titleUrl = data.select("a.razdel")
                                .eq(x)
                                .attr("href");
                        x++;
                        if (size1.contains("nomer")){
                            serial = data1.select("a.nomer")
                                    .eq(y)
                                    .text();
                            serialUrl = data1.select("a.nomer")
                                    .eq(y)
                                    .attr("href");
                            y++;
                        }
                        Txt = data.select("div.zavernuto")
                                .eq(i)
                                .text();
                        news_items.add(new News_Item(title, chislo+""+season, serial, Txt, titleUrl, serialUrl));
                        Log.d("razdel", title +" "+ chislo+""+season +" " + serial +" "+serialUrl +" "+ Txt);

                    }




                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
