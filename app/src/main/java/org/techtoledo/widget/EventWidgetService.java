package org.techtoledo.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;
import android.util.Log;

/**
 * Created by chris on 1/8/17.
 */

public class EventWidgetService extends RemoteViewsService{

    private static final String TAG = "EventWidgetFactory";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.d(TAG, "onGetViewFactgory");

        return new EventWidgetFactory(this.getApplicationContext(), intent);
    }
}
