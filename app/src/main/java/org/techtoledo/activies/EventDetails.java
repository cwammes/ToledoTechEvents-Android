package org.techtoledo.activies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.util.Log;
import android.widget.TextView;

import toledotechevets.org.toledotech.R;

import org.techtoledo.domain.Event;

public class EventDetails extends AppCompatActivity {

    private static final String TAG = "EventDetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Event event = (Event)intent.getSerializableExtra("Event");

        Log.d(TAG, event.getSummary());

        TextView eventTitle = (TextView)findViewById(R.id.event_title);
        eventTitle.setText(event.getSummary());

        TextView eventDate = (TextView) findViewById(R.id.event_date);
        eventDate.setText(event.getStartTime().toString() + " - " + event.getEndTime().toString());

        TextView eventLocationShort = (TextView) findViewById(R.id.event_location_short);
        eventLocationShort.setText(event.getLocationShort());

        TextView eventDescription = (TextView)findViewById(R.id.event_description);
        eventDescription.setText(event.getDescription());
    }

}
