package org.techtoledo.activies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import toledotechevets.org.toledotech.R;

import org.jsoup.nodes.Element;
import org.techtoledo.domain.Event;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class EventDetails extends DefaultActivity {

    private static final String TAG = "EventDetails";
    private URL rsvpUrl;
    private Button rsvpButton;


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

        //Has an event URL
        rsvpButton = (Button) findViewById(R.id.rsvp_button);
        //Hide RSVP Button
        Log.d(TAG, "Hide rsvpButton");
        rsvpButton.setVisibility(View.GONE);
        if(event.getUid() != null && event.getUid().compareTo("") != 0){
            try{
                rsvpUrl = getRsvpUrl(new URL(event.getUid()));
                if(rsvpUrl != null){
                    Log.d(TAG, "RSVP: " + rsvpUrl.toString());
                    rsvpButton.setVisibility(View.VISIBLE);
                }
            }
            catch(Exception e){
                Log.e(TAG, e.getMessage());
            }

        }
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

    private URL getRsvpUrl(URL eventURL){


        try {
            Document doc = Jsoup.connect(eventURL.toString()).get();
            Elements detailsHeader = doc.select("#event_details h3");
            for(int x = 0; x < detailsHeader.size(); x++) {
                if (detailsHeader.get(x).text().compareToIgnoreCase("RSVP/Register:") == 0) {
                    Element link = detailsHeader.get(x).nextElementSibling();
                    //Elements link = doc.select("#event_details a");
                    return new URL(link.attr("href"));
                }
            }

        }
        catch(Exception e){
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public void rsvpEvent(View view)
    {
        //Open Browser with RSVP URL
        Log.d(TAG, "Open in Browser: " + rsvpUrl.toString());
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rsvpUrl.toString()));
        startActivity(browserIntent);

    }

}
