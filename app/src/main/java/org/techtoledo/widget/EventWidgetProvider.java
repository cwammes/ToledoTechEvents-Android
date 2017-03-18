package org.techtoledo.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.RemoteViews;

import toledotechevets.org.toledotech.MainActivity;
import toledotechevets.org.toledotech.R;
import android.util.Log;
import android.widget.Toast;

import org.techtoledo.activies.EventDetails;
import org.techtoledo.domain.Event;
import org.techtoledo.dao.EventsDAO;
import org.techtoledo.service.CacheStatusService;

/**
 * Created by chris on 12/30/16.
 */

public class EventWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "EventWidgetProvider";
    public static final String CLICK_ACTION = "org.techtoledo.widget.EventsAdapter.CLICK_ACTION";
    public static final String EXTRA_ITEM = "org.techtoledo.widget.EventsAdapter.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){

        ComponentName thisComponent = new ComponentName(context, EventWidgetProvider.class);

        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisComponent);

        CacheStatusService cacheStatusService = new CacheStatusService();

        for(int x = 0; x < allWidgetIds.length; x++){

            Intent intent = new Intent(context, EventWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[x]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.event_widget_layout);
            remoteViews.setRemoteAdapter(R.id.listView, intent);

            Intent clickIntent = new Intent(context, EventWidgetProvider.class);
            clickIntent.setAction(EventWidgetProvider.CLICK_ACTION);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[x]);
            clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            //remoteViews.setRemoteAdapter(R.id.listView, clickIntent);
            remoteViews.setPendingIntentTemplate(R.id.listView, clickPendingIntent);

            Log.d(TAG, "SetTextViewText: " +  x);

            appWidgetManager.updateAppWidget(appWidgetIds[x], remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onReceive(Context context, Intent intent){

        Log.d(TAG, "onReceive getAction: " + intent.getAction());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if(intent.getAction().equals(CLICK_ACTION)){
            //Call Activity
            Bundle extras = intent.getExtras();
            int eventId = (int) extras.get("eventId");

            EventsDAO eventsDAO = new EventsDAO();
            Event event = eventsDAO.getEventById(context, eventId);
            Log.d(TAG, "onReceive eventId: " + eventId);
            Log.d(TAG, "onReceive event.getId(): " + event.getId());

            Intent eventDetailsIntent = new Intent(context, EventDetails.class);
            eventDetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            eventDetailsIntent.putExtra("Event", event);

            context.startActivity(eventDetailsIntent);

        }
        else if(intent.getAction().equals("android.appwidget.action.APPWIDGET_UPDATE")){
            Log.d(TAG, "onReceive: android.appwidget.action.APPWIDGET_UPDATE");

            ComponentName thisComponent = new ComponentName(context, EventWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisComponent);
            Log.d(TAG, "appWidgetIds.length: " + appWidgetIds.length);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }

        super.onReceive(context, intent);

    }

}
