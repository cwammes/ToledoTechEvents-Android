package org.techtoledo.activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import toledotechevets.org.toledotech.R;

public class AboutTechToledo extends DefaultActivity {

    private static final String TAG = "AboutTechToledoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_tech_toledo);

        WebView webView = (WebView) findViewById(R.id.about_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://toledotechevents.org/about");
    }

    @Override
    public void aboutEvent(View view)
    {
        //End Activity
        Log.d(TAG, "End About Activity");
        this.finish();

    }

}
