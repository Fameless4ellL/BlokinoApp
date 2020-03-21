package com.fameless.blok.SecondActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleTextView;


    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<item> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);




        imageView = findViewById(R.id.imageView); // previous data from the first activity
        titleTextView = findViewById(R.id.textView);

        getSupportActionBar().setTitle("Расписание ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.recyclerViewS); // data in the second activity
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new Adapter(items, this);
        recyclerView.setAdapter(adapter);



        titleTextView.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);

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

    private class Content extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String detailUrl = getIntent().getStringExtra("detailUrl");
                String url = detailUrl;
                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.spisokanime");
                Elements data1 = doc.select("div.preview");
                Elements data2 = doc.select("li.kolonki");
                int j = 0;
                Element data3 = data2.get(j);

                Elements compare = data3.select("div.preview");
                int size1 = compare.size();

                int size = data1.size();
                for (int i = 0; i < size; i++) {

                    if (i<size1) {
                        String week = data.select("p.alfavit")
                                .eq(j)
                                .text();
                        String img = data.select("img.imganime")
                                .eq(i)
                                .attr("src");
                        String title = data.select("div.perehod")
                                .select("a")
                                .eq(i)
                                .text();
                        String text = data.select("ul.kale")
                                .select("em")
                                .eq(i)
                                .text();
                        String grade = data.select("div.god")
                                .select("span").next()
                                .eq(i)
                                .text();
                        String DetailUrl = data.select("div.perehod")
                                .select("a")
                                .eq(i)
                                .attr("href");

                        items.add(new item(week, title, img, text, DetailUrl, grade));
                        //Log.d("items", "img: " + img + "title: " + title + "text: " + text);
                    } else {
                        j=j+1;
                        i=i-1;
                        data3 = data2.get(j);
                        compare = data3.select("div.preview");
                        int size2 = compare.size();
                        size1 = size1 +size2;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
