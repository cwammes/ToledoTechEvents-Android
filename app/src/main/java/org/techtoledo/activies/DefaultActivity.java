package org.techtoledo.activies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.techtoledo.service.CacheStatusService;

/**
 * Created by cwammes on 7/12/16.
 */
public class DefaultActivity extends AppCompatActivity {


    public void aboutEvent(View view)
    {
        Intent intent = new Intent(getBaseContext(), AboutTechToledo.class);
        startActivity(intent);
    }

    public void endEvent(View view)
    {
        //End Activity
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();

        CacheStatusService cacheStatusService = new CacheStatusService();
        cacheStatusService.updateMaxCacheDate(getBaseContext());
    }

}
