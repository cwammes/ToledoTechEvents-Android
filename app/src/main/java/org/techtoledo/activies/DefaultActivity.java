package org.techtoledo.activies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by cwammes on 7/12/16.
 */
public class DefaultActivity extends AppCompatActivity {


    public void aboutEvent(View view)
    {
        Intent intent = new Intent(getBaseContext(), AboutTechToledo.class);
        startActivity(intent);
    }

}
