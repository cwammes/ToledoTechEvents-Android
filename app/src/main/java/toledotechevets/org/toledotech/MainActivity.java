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

        eventList = eventsDAO.getEventList();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        eAdapter = new EventsAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);
    }
}
