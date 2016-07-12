package toledotechevets.org.toledotech;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.techtoledo.activies.AboutTechToledo;
import org.techtoledo.dao.EventsDAO;
import org.techtoledo.domain.Event;
import org.techtoledo.view.EventsAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList = new ArrayList<>();
    private EventsDAO eventsDAO = new EventsDAO();
    private RecyclerView recyclerView;
    private EventsAdapter eAdapter;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        eventList = eventsDAO.getEventList();
        //eventList = loadEvents();
        Log.d(TAG, "Integer.toString(eventList.size()): " + Integer.toString(eventList.size()));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        eAdapter = new EventsAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);
    }

    public void aboutEvent(View view)
    {
        Intent intent = new Intent(MainActivity.this, AboutTechToledo.class);
        startActivity(intent);
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
