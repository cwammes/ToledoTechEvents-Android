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

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDetails extends AppCompatActivity {

    private static final String TAG = "EventDetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Toledo Tech Events");
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Event event = (Event)intent.getSerializableExtra("Event");

        Log.d(TAG, event.getSummary());

        TextView eventTitle = (TextView)findViewById(R.id.event_title);
        eventTitle.setText(event.getSummary());

        TextView eventDate = (TextView) findViewById(R.id.event_date);
        eventDate.setText(getEventDate(event.getStartTime(), event.getEndTime()));

        TextView eventLocationShort = (TextView) findViewById(R.id.event_location_short);
        eventLocationShort.setText(event.getLocationShort());

        TextView eventDescription = (TextView)findViewById(R.id.event_description);
        eventDescription.setText(event.getDescription());
    }

    private String getEventDate(Date startTime, Date endTime){

        SimpleDateFormat dateCompareFormatDate = new SimpleDateFormat("EEEE MMMM d, yyyy");
        SimpleDateFormat dateCompareFormatAmPm = new SimpleDateFormat("aa");
        String myDate = "";

        //Start and End on Different Days
        if(dateCompareFormatDate.format(startTime).compareTo(dateCompareFormatDate.format(endTime)) != 0){
            SimpleDateFormat myDateFormat = new SimpleDateFormat("EEEE MMMM d, yyyy h:mm aa");
            myDate = myDateFormat.format(startTime) + " - " + myDateFormat.format(endTime);
        }

        //Start and End in AM and PM
        else if(dateCompareFormatAmPm.format(startTime).compareTo(dateCompareFormatAmPm.format(endTime)) != 0){
            SimpleDateFormat myDateFormatStartTime = new SimpleDateFormat("EEEE MMMM d, yyyy h:mm aa");
            SimpleDateFormat myDateFormatEndTime = new SimpleDateFormat("h:mm aa");
            myDate = myDateFormatStartTime.format(startTime) + " - " + myDateFormatEndTime.format(endTime);
        }

        //Everything else
        else{
            SimpleDateFormat myDateFormatStartTime = new SimpleDateFormat("EEEE MMMM d, yyyy h:mm");
            SimpleDateFormat myDateFormatEndTime = new SimpleDateFormat("h:mm aa");
            myDate = myDateFormatStartTime.format(startTime) + " - " + myDateFormatEndTime.format(endTime);
        }

        return myDate;
    }

}
