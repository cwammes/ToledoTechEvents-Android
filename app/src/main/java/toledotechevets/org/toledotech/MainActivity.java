package toledotechevets.org.toledotech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.techtoledo.dao.EventsDAO;
import org.techtoledo.domain.Event;
import org.techtoledo.view.EventsAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList = new ArrayList<>();
    private EventsDAO eventsDAO = new EventsDAO();
    private RecyclerView recyclerView;
    private EventsAdapter eAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //eventList = eventsDAO.getEventList();
        eventList = loadEvents();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

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
