package org.techtoledo.dao;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import java.util.Iterator;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import java.util.ArrayList;
import java.util.Date;

import org.techtoledo.domain.Event;

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
//Log.d(TAG, "urlStr: " + urlStr);
        try{
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();
            eventList = setEventList(is);
System.out.println("ArrayList Size: " + eventList.size());
        }
        catch(Exception e){
            //Log.e(TAG, e.getMessage());
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

        //Log.d(TAG, "Start iCal4j");
        try {
            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(is);

            for (Iterator i = calendar.getComponents().iterator(); i.hasNext(); ) {
                Event event = new Event();
                Component component = (Component) i.next();
                System.out.println("Component [" + component.getName() + "]");

                for (Iterator j = component.getProperties().iterator(); j.hasNext(); ) {
                    Property property = (Property) j.next();
                    System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");

                    if(property.getName().matches("DESCRIPTION")){
                        event.setDescription(property.getValue());
                    }

                    else if(property.getName().matches("DTEND")){

                        event.setEndTime(new Date());
                    }

                    else if(property.getName().matches("DTSTART")){

                        event.setStartTime(new Date());
                    }

                    else if(property.getName().matches("URL")){

                        try {
                            URL url = new URL(property.getValue());
                            event.setEventURL(url);
                        } catch (Exception e) {

                        }
                    }
                    else if(property.getName().matches("SUMMARY")){
                        event.setSummary(property.getValue());
                    }
                    else if(property.getName().matches("LOCATION")){
                        event.setLocation(property.getValue());
                    }
                    else if(property.getName().matches("UID")){
                        event.setUid(property.getValue());
                    }
                }

                eventList.add(event);
            }
        }
        catch(Exception e){

        }
        return eventList;
    }

}
