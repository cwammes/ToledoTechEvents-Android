package org.techtoledo.dao;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;

import org.techtoledo.domain.Event;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import toledotechevets.org.toledotech.R;

/**
 * Created by cwammes on 6/16/16.
 */
public class EventsDAO {

    private static final String TAG = "Get Events ICS Feed";
    private static final String cacheFile = "eventCache.ics";

    public ArrayList<Event> getEventList(Context context){
        ArrayList<Event> eventList = new ArrayList<Event>();

        String hostname = context.getResources().getString(R.string.calendar_hostname);
        String urlStr = hostname + "/events.ics";
        URL url;

        HttpURLConnection connection = null;
        Log.d(TAG, "urlStr: " + urlStr);
        try{
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);


            InputStream is = connection.getInputStream();
            int httpResponseCode = connection.getResponseCode();

            //Got Response 200 Response
            if(httpResponseCode == 200) {
                String str = processInputStream(is);
                eventList = setEventList(str);
                Log.d(TAG, "ArrayList Size: " + eventList.size());
                setCachedEvents(str, context);
            }
            //Not a 200 Response
            else{
                eventList = getCachedEvents(context);
            }
        }
        catch(Exception e){
            Log.e(TAG, "Error");
            e.printStackTrace();
            eventList = getCachedEvents(context);
        }
        finally{
            if(connection != null){
                connection.disconnect();
            }
        }

        return eventList;

    }

    private ArrayList<Event> setEventList(String str){

        ArrayList<Event> eventList = new ArrayList<Event>();
        try {

            ICalendar ical = Biweekly.parse(str).first();

            Log.e(TAG, "ical.getEvents().size():" + ical.getEvents().size());
            for(int x = 0; x < ical.getEvents().size(); x++){
                Event myEvent = getEvent(ical, x);

                //Only add future events to the list
                Date currentDate = new Date();
                if(myEvent != null && currentDate.getTime() < myEvent.getEndTime().getTime()) {
                    eventList.add(myEvent);
                }
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        return eventList;
    }

    private void setCachedEvents(String outputStr, Context context){

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(cacheFile, Context.MODE_PRIVATE));
            outputStreamWriter.write(outputStr);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }

    private ArrayList<Event> getCachedEvents(Context context){

        ArrayList<Event> eventList;
        try {
            FileInputStream fin = context.openFileInput(cacheFile);
            String fileString = processInputStream(fin);
            eventList = setEventList(fileString);
            return eventList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String processInputStream(InputStream is){

        String returnStr = "";


        String line;
        String prevLine = "";
        try {
            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            while ((line = rd.readLine()) != null) {

                //Fix issue with location not showing up
                if(line.contains("SEQUENCE:") && !prevLine.contains("LOCATION:"))
                    result.append("LOCATION: TBD\n");

                //Fix encoding issues coming from Calagator
                line = URLDecoder.decode(line, "ASCII");
                line = line.replace("&#13\\;", "");

                result.append(line + "\n");
                prevLine = line;
            }
            rd.close();
            returnStr =  result.toString();
            //Log.d(TAG, result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnStr;
    }

    private Event getEvent(ICalendar ical, int counter) {
        try {
            Event myEvent = new Event();
            VEvent event = ical.getEvents().get(counter);

            //Log.d(TAG, "Description: \n" + event.getDescription().getValue());
            myEvent.setDescription(event.getDescription().getValue());
            myEvent.setSummary(event.getSummary().getValue());
            myEvent.setLocation(event.getLocation().getValue());
            myEvent.setStartTime(event.getDateStart().getValue());
            myEvent.setEndTime(event.getDateEnd().getValue());
            myEvent.setUid(event.getUid().getValue());

            //Get Location Info
            if (event.getLocation().getValue().indexOf(":") > 0) {
                myEvent.setLocationShort(event.getLocation().getValue().substring(0, event.getLocation().getValue().indexOf(":")));
                myEvent.setLocationAddress(event.getLocation().getValue().substring(event.getLocation().getValue().indexOf(":") + 1, event.getLocation().getValue().length()).trim());
            } else {
                myEvent.setLocationShort("TBD");
                myEvent.setLocationAddress("");
            }

            //Get URL
            if (event.getUrl() != null) {
                myEvent.setEventURL(new URL(event.getUrl().getValue()));
            } else {
                //myEvent.setEventURL(new URL(""));
            }

            return myEvent;
        }
        catch (Exception e){
            Log.e(TAG, "File write failed: " + e.toString());
        }

        return null;
    }

}
