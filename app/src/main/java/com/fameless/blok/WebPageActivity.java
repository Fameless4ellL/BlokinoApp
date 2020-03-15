package com.fameless.blok;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebPageActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_web_page);
        webView = findViewById(R.id.webView); // previous data from the first activity

        webView.setInitialScale(1);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        int height = size.y;;
        int width = size.x;
        String VideoURL = getIntent().getStringExtra("VideoUrl");


        String data_html = "<!DOCTYPE html><html> <head> <meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"target-density dpi=high-dpi\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <link rel=\"stylesheet\" media=\"screen and (-webkit-device-pixel-ratio:1.5)\" href=\"hdpi.css\" /></head> <body style=\"background:black;margin:0 0 0 0; padding:0 0 0 0;\"> <iframe class=\"1\" style=\"background:black;\" width=' "+width+"' height='"+height+"' src=\""+VideoURL+"\" frameborder=\"0\" AllowFullScreen></iframe> </body> </html> ";

        webView.loadDataWithBaseURL(getIntent().getStringExtra("VideoUrl"), data_html, "text/html", "UTF-8", null);


        //webView = new WebView(this);
        //webView.loadUrl(getIntent().getStringExtra("VideoUrl"));
        //webView.loadData("<iframe src=\"//aniqit.com/seria/555433/690225852080e583a8180eedeff85805/720p\" width=\"607\" height=\"360\" frameborder=\"0\" AllowFullScreen></iframe>", "text/html",
         //       "utf-8");
        //setContentView(webView);


        //WebSettings webViewSettings = webView.getSettings();
        //webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //webViewSettings.setJavaScriptEnabled(true);


        //webView.setText(getIntent().getStringExtra("title"));


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
