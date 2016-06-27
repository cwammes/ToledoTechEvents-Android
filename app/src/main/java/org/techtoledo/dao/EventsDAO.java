package org.techtoledo.dao;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.techtoledo.domain.Event;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

/**
 * Created by cwammes on 6/16/16.
 */
public class EventsDAO {

    private static final String TAG = "Get Events ICS Feed";

    public ArrayList<Event> getEventList(){
        ArrayList<Event> eventList = new ArrayList<Event>();
        String urlStr = "http://toledotechevents.org/events.ics";
        URL url;

        HttpURLConnection connection = null;
        Log.d(TAG, "urlStr: " + urlStr);
        try{
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            eventList = setEventList(is);
            Log.d(TAG, "ArrayList Size: " + eventList.size());
        }
        catch(Exception e){
            Log.e(TAG, "Error");
            e.printStackTrace();
        }
        finally{
            if(connection != null){
                connection.disconnect();
            }
        }

        return eventList;

    }

    private ArrayList<Event> setEventList(InputStream is){

        ArrayList<Event> eventList = new ArrayList<Event>();
        try {

            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line + "\n");
            }
            rd.close();
            String str =  result.toString();

            ICalendar ical = Biweekly.parse(str).first();

            Log.e(TAG, "ical.getEvents().size():" + ical.getEvents().size());
            for(int x = 0; x < ical.getEvents().size(); x++){
                Event myEvent = new Event();
                VEvent event = ical.getEvents().get(x);

                myEvent.setDescription(event.getDescription().getValue());
                myEvent.setSummary(event.getSummary().getValue());
                myEvent.setLocation(event.getLocation().getValue());
                myEvent.setStartTime(event.getDateStart().getValue());

                //Get Location Info
                if(event.getLocation().getValue().indexOf(":") > 0) {
                    myEvent.setLocationShort(event.getLocation().getValue().substring(0, event.getLocation().getValue().indexOf(":")));
                    myEvent.setLocationAddress(event.getLocation().getValue().substring(event.getLocation().getValue().indexOf(":"), event.getLocation().getValue().length()));
                }
                else {
                    myEvent.setLocationShort("TBD");
                    myEvent.setLocationAddress("");
                }

                //Get URL
                if(event.getUrl() != null){
                    myEvent.setEventURL(new URL(event.getUrl().getValue()));
                }
                else{
                    //myEvent.setEventURL(new URL(""));
                }

                eventList.add(myEvent);
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        return eventList;
    }

}
