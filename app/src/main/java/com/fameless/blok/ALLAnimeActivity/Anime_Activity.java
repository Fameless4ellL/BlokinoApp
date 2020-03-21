package com.fameless.blok.ALLAnimeActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fameless.blok.R;
import com.fameless.blok.main.MainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Anime_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    public static String dataPath;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DrawerLayout drawerLayout;
    private AnimeAdapter animeAdapter;
    private transient  ArrayList<AnimeItem> animeItems;
    private static final  String SHARED_PREFS = "sharedPrefs";
    public  static final String data ="data";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        if (ContextCompat.checkSelfPermission(Anime_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(Anime_Activity.this, "You have already granted this permision!", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions();
        }


        loadData();
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        animeAdapter = new AnimeAdapter(animeItems,this);
        recyclerView.setAdapter(animeAdapter);


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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Anime_Fragment()).commit();
            navigationView.setCheckedItem(R.id.nav_anime);
        }



            Content content = new Content();
            content.execute();
    }


    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task anime", null);
        Type type = new TypeToken<ArrayList<AnimeItem>>() {}.getType();
        animeItems = gson.fromJson(json, type);

        if (animeItems == null) {
            animeItems = new ArrayList<>();
        }

    }

    private void saveData() throws IOException {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(animeItems);
        editor.putString("task anime", json);
        editor.apply();
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Разрешение на запись")
                    .setMessage("Это разрешение нужно для записи кеш файлов, иначе приложение Blokino будет работать не так как нужно.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Anime_Activity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permision GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permision DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                animeAdapter.getFilter().filter(newText);
                loadData();
                return false;
            }
        });
        return true;
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
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private class Content extends AsyncTask<Void, Void, Void> implements Serializable  {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(Anime_Activity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(Anime_Activity.this, android.R.anim.fade_out));
            animeAdapter.notifyDataSetChanged();
            try {
                dataPath = ObjectToFileUtil.objectToFile(animeItems);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                saveData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            if (animeItems.isEmpty()) {
                try {
                    String url = "https://blokino.org/anime/";
                    Document doc = Jsoup.connect(url).get();

                    Elements data = doc.select("div.preview");
                    Elements data1 = doc.select("div.god");
                    int size = data.size();
                    for (int i = 0; i < size; i++) {
                        String img = data.select("img.img")
                                .eq(i)
                                .attr("src");
                        String title = data.select("div.perehod")
                                .select("a")
                                .eq(i)
                                .text();
                        String text = data.select("div.god")
                                .select("span")
                                .eq(i)
                                .text();
                        String grade = data.select("div.reit")
                                .select("span").next()
                                .eq(i)
                                .text();
                        String DetailUrl = data.select("div.perehod")
                                .select("a")
                                .eq(i)
                                .attr("href");
                        animeItems.add(new AnimeItem(title, img, text, DetailUrl, grade));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                loadData();
            }
            return null;
        }
    }
}
