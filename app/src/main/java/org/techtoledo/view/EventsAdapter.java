package org.techtoledo.view;

/**
 * Created by cwammes on 6/22/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import toledotechevets.org.toledotech.R;

import org.techtoledo.domain.Event;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<Event> eventList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, date;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.genre);
            date = (TextView) view.findViewById(R.id.year);
        }
    }


    public EventsAdapter(List<Event> evnetList) {
        this.eventList = evnetList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title.setText(event.getSummary());
        holder.description.setText(event.getDescription());
        holder.date.setText(event.getStartTime().toString());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
