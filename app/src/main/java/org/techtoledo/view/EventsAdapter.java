package org.techtoledo.view;

/**
 * Created by cwammes on 6/22/16.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import toledotechevets.org.toledotech.R;

import android.util.Log;

import org.techtoledo.activies.EventDetails;
import org.techtoledo.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<Event> eventList;
    private static final String TAG = "EventsAdapter";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, location, date;
        private final Context context;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            title = (TextView) view.findViewById(R.id.title);
            location = (TextView) view.findViewById(R.id.location);
            date = (TextView) view.findViewById(R.id.date);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, String.valueOf(getAdapterPosition()));

            Event event = eventList.get(getAdapterPosition());
            Log.d(TAG, event.getSummary());

            final Intent intent = new Intent(context, EventDetails.class);
            intent.putExtra("Event", event);

            context.startActivity(intent);
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
        holder.location.setText(event.getVenue().getTitle());

        //Format the Date
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM d, yyyy h:mm aa");
        holder.date.setText(sdf.format(event.getStartTime()).toString());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
