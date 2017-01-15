package org.techtoledo.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.widget.ListView;
import android.widget.RemoteViews;
import toledotechevets.org.toledotech.R;
import android.util.Log;

/**
 * Created by chris on 12/30/16.
 */

public class EventWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "EventWidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){

        ComponentName thisComponent = new ComponentName(context, EventWidgetProvider.class);

        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisComponent);

        for(int x = 0; x < allWidgetIds.length; x++){

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.event_widget_layout);

            Intent intent = new Intent(context, EventWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[x]);
            remoteViews.setRemoteAdapter(R.id.listView, intent);

            Log.d(TAG, "SetTextViewText: " +  x);

            appWidgetManager.updateAppWidget(appWidgetIds[x], remoteViews);
        }

    }
}
