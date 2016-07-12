package org.techtoledo.activies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import toledotechevets.org.toledotech.R;

public class AboutTechToledo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_tech_toledo);

        WebView webView = (WebView) findViewById(R.id.about_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://toledotechevents.org/about");


    }

}
