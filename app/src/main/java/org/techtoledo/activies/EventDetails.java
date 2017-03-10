package org.techtoledo.activies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import toledotechevets.org.toledotech.R;

import org.techtoledo.domain.Event;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDetails extends DefaultActivity {

    private static final String TAG = "EventDetails";
    private URL rsvpUrl;
    private Button rsvpButton;
    private Button webButton;
    private Button mapButton;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("Toledo Tech Events");
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();

        onNewIntent(intent);

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

    public void rsvpEvent(View view)
    {
        //Open Browser with RSVP URL
        Log.d(TAG, "Open in Browser: " + event.getRsvpUrl());
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getRsvpUrl()));
        startActivity(browserIntent);

    }

    public void openWebBrowser(View view){

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getEventURL()));
        startActivity(browserIntent);
    }

    public void addToCalendar(View view){


        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", event.getStartTime().getTime());
        intent.putExtra("endTime", event.getEndTime().getTime());
        intent.putExtra("title", event.getSummary());
        intent.putExtra("eventLocation", event.getVenue().getAddress());
        startActivity(intent);
    }

    public void openMap(View view){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + event.getVenue().getAddress());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        event = (Event)intent.getSerializableExtra("Event");

        Log.d(TAG, event.getSummary());

        TextView eventTitle = (TextView)findViewById(R.id.event_title);
        eventTitle.setText(event.getSummary());

        TextView eventDate = (TextView) findViewById(R.id.event_date);
        eventDate.setText(getEventDate(event.getStartTime(), event.getEndTime()));

        TextView eventLocationShort = (TextView) findViewById(R.id.event_location_short);
        eventLocationShort.setText(event.getVenue().getTitle());

        TextView eventDescription = (TextView)findViewById(R.id.event_description);
        eventDescription.setText(event.getDescription());

        //Has an event URL
        //Hide RSVP Button

        if(event.getRsvpUrl() == null || event.getRsvpUrl().isEmpty()){
            rsvpButton = (Button) findViewById(R.id.rsvp_button);
            rsvpButton.setVisibility(View.GONE);
        }

        //Check for URL and set url if there; hide button if no url
        if(event.getEventURL() == null || event.getEventURL().isEmpty()){
            webButton = (Button) findViewById(R.id.web);
            webButton.setVisibility(View.GONE);
        }

        //Check for address
        if(event.getVenue() == null || event.getVenue().getAddress() == null || event.getVenue().getAddress().isEmpty()){
            mapButton = (Button) findViewById(R.id.Map);
            mapButton.setVisibility(View.GONE);
        }
    }
}
