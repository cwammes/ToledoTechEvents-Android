package toledotechevets.org.toledotech;

import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.techtoledo.activies.DefaultActivity;
import org.techtoledo.dao.EventsDAO;
import org.techtoledo.domain.Event;
import org.techtoledo.view.EventsAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends DefaultActivity {

    private List<Event> eventList = new ArrayList<>();
    private EventsDAO eventsDAO = new EventsDAO();
    private RecyclerView recyclerView;
    private EventsAdapter eAdapter;
    private Date lastUpdateDate;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Set Last Updated Date
        lastUpdateDate = new Date();

        setEventList();
        Log.d(TAG, "Integer.toString(eventList.size()): " + Integer.toString(eventList.size()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        setEventsAdapter(eventList);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"Activity Resumed");

        Date currentDate = new Date();
        if(currentDate.getTime() - lastUpdateDate.getTime() > 240 * 60 * 1000){
            Log.d(TAG, "Activity Resumed: Get new feed");

            //Get New Event List
            setEventList();
            setEventsAdapter(eventList);

            //Update last Update Date
            lastUpdateDate = new Date();
        }
        else if(currentDate.getTime() - lastUpdateDate.getTime() > 30 * 60 * 1000){
            Log.d(TAG, "Activity Resumed: Remove past event from current feed");

            //Remove any events that have ended
            for(int x = 0; x < eventList.size(); x++){
                if(currentDate.getTime() > eventList.get(x).getEndTime().getTime()){
                    eventList.remove(x);
                }
            }
            setEventsAdapter(eventList);
        }
    }

    private void setEventList(){
        this.eventList = eventsDAO.getEventList();
        //eventList = loadEvents();
    }

    private void setEventsAdapter(List<Event> eventList){
        eAdapter = new EventsAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);
    }

    private ArrayList<Event> loadEvents(){
        ArrayList<Event> eventList = new ArrayList<Event>();

        Event event1 = new Event();
        event1.setDescription("Description1");
        event1.setSummary("Summary1");
        event1.setStartTime(new Date());


        Event event2 = new Event();
        event2.setDescription("Description2");
        event2.setSummary("Summary2");
        event2.setStartTime(new Date());


        Event event3 = new Event();
        event3.setDescription("Description3");
        event3.setSummary("Summary3");
        event3.setStartTime(new Date());

        eventList.add(event1);
        eventList.add(event2);
        eventList.add(event3);

        return eventList;
    }

}
