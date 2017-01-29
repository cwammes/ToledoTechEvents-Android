package org.techtoledo.dao;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.techtoledo.domain.Event;
import org.techtoledo.domain.Venue;

import toledotechevets.org.toledotech.R;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.reflect.TypeToken;

/**
 * Created by cwammes on 6/16/16.
 */

public class EventsDAO {

    private static final String TAG = "Get Events ICS Feed";
    private static final String cacheFile = "eventCache.ics";

    public ArrayList<Event> getEventList(Context context){
        ArrayList<Event> eventList = new ArrayList<Event>();

        String hostname = context.getResources().getString(R.string.calendar_hostname);
        String urlStr = hostname + "/events.json";
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

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Event>>(){}.getType();
        ArrayList<Event> eventList = gson.fromJson(str, listType);

        Date currentDate = new Date();

        for(int x = 0; x < eventList.size(); x++){

            //Remove events already passed, but still in feed
            if(currentDate.getTime() > eventList.get(x).getEndTime().getTime()){
                eventList.remove(x);
            }

            if(eventList.get(x).getVenue() == null){
                Venue tempVenue = new Venue();
                tempVenue.setTitle("TBD");
                eventList.get(x).setVenue(tempVenue);
            }
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

    public Event getEventById(Context context, int eventId){
        ArrayList<Event> eventList = getCachedEvents(context);

        for(int x = 0; x < eventList.size(); x++){
            if(eventList.get(x).getId() == eventId)
                return eventList.get(x);
        }

        return null;
    }

}
