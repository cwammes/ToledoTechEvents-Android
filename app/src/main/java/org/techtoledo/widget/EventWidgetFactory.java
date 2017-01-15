package org.techtoledo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;

import org.techtoledo.dao.EventsDAO;
import org.techtoledo.domain.Event;

import java.util.ArrayList;

import toledotechevets.org.toledotech.R;

/**
 * Created by chris on 1/2/17.
 */

public class EventWidgetFactory implements RemoteViewsFactory{

    private ArrayList<Event> eventList;
    private Context context;
    private int appWidgetId;
    private LayoutInflater inflater;

    private static final String TAG = "EventWidgetFactory";

    public EventWidgetFactory(Context context, Intent intent){
        this.context = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        //Set ArrayList
        EventsDAO eventsDao = new EventsDAO();
        ArrayList<Event> eventList = eventsDao.getEventList(context);
        Log.d(TAG, "Popluate eventList: " + eventList.size());
        for(int x = eventList.size() -1; x > 2; x--){
            eventList.remove(x);
        }
        Log.d(TAG, "Final Size eventList: " + eventList.size());

        this.eventList = eventList;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.event_widget_details_layout);

        Event event = eventList.get(i);
        remoteView.setTextViewText(R.id.widgetTitle, event.getSummary());
        //remoteView.setTextViewText(R.id.widgetDate, event.getStartTime().toString());

        Log.d(TAG, "getViewAt" + i + "\nsetTextViewText: " + event.getSummary());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
