package toledotechevets.org.toledotech;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.techtoledo.activies.DefaultActivity;
import org.techtoledo.dao.EventsDAO;
import org.techtoledo.domain.Event;
import org.techtoledo.service.CacheStatusService;
import org.techtoledo.service.SearchService;
import org.techtoledo.view.EventsAdapter;
import org.techtoledo.widget.EventWidgetProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;
import android.widget.SearchView;

public class MainActivity extends DefaultActivity implements SearchView.OnQueryTextListener {

    private List<Event> eventList = new ArrayList<>();
    private EventsDAO eventsDAO = new EventsDAO();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private EventsAdapter eAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
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
        searchView = (SearchView) findViewById(R.id.search_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh(){
                refreshContent();
            }

        });

        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);

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
        this.eventList = eventsDAO.getEventList(getApplicationContext());
        //eventList = loadEvents();
    }

    private void setEventsAdapter(List<Event> eventList){
        eAdapter = new EventsAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Log.d(TAG, "onQueryTextSubmit: " + query);
        SearchService searchService = new SearchService();
        setEventsAdapter(searchService.search(query, eventList));

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        //Reset results
        if(newText != null && newText.isEmpty()){
            setEventsAdapter(eventList);
        }

        Log.d(TAG, "onQueryTextChange: " + newText);
        return true;
    }

    private void refreshContent(){

        CacheStatusService cacheStatusService = new CacheStatusService();
        cacheStatusService.expireCacheStatus(getApplicationContext());

        Log.d(TAG, "refreshContent");

        //Get New Event List
        setEventList();
        setEventsAdapter(eventList);

        //Update last Update Date
        lastUpdateDate = new Date();

        //Update Widget
        Intent updateWidget = new Intent(this, EventWidgetProvider.class);
        updateWidget.setAction("android.appwidget.action.APPWIDGET_UPDATE");

        sendBroadcast(updateWidget);

        //Done Refreshing
        swipeRefreshLayout.setRefreshing(false);

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
