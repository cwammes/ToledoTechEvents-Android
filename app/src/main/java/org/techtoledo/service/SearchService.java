package org.techtoledo.service;

import android.util.Log;

import org.techtoledo.domain.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cwammes on 8/12/16.
 */
public class SearchService {

    private static final String TAG = "SearchService";


    public List<Event> search(String query, List <Event> eventList){

        Log.d(TAG, "query: " + query);

        List<Event> resultList = new ArrayList<Event>();
        for(int x = 0; x < eventList.size(); x++){

            //Search Event Title
            if(eventList.get(x).getSummary().toLowerCase().contains(query.toLowerCase())){
                resultList.add((eventList.get(x)));
            }

            //Search Event Description
            else if(eventList.get(x).getDescription().toLowerCase().contains(query.toLowerCase())) {
                resultList.add(eventList.get(x));
            }

            //Search Event Location
            else if(eventList.get(x).getVenue().getTitle().toLowerCase().contains(query.toLowerCase())){
                resultList.add(eventList.get(x));
            }

        }

        Log.d(TAG, "eventList.size()" + eventList.size() + "\nresultList.size(): " + resultList.size());

        return resultList;
    }
}
