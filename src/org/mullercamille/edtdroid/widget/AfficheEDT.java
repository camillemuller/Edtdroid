package org.mullercamille.edtdroid.widget;


import android.annotation.SuppressLint;
import android.appwidget.AppWidgetProvider;
/*
import java.util.Arrays;

import org.mullercamille.edtdroid.R;
import org.mullercamille.edtdroid.adapters.DaysPagerAdapter;
import org.mullercamille.edtdroid.adapters.LessonAdapter;
import org.mullercamille.edtdroid.fragments.DayFragment;
import org.mullercamille.edtdroid.model.Day;

import android.appwidget.AppWidgetManager;
import android.app.FragmentManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

*/


public class AfficheEDT extends AppWidgetProvider {

	
	/*private DaysPagerAdapter paDays;
	public static final String DATA_FETCHED="org.muller.camille.edtdroid";


    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Intent serviceIntent = new Intent(context, AfficheEDT.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    appWidgetIds[i]);
            context.startService(serviceIntent);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
	
    @SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
    	 
        // which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget);
 
        // RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, AfficheEDT.class);
        // passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // setting a unique Uri to the intent
        // don't know its purpose to me right now
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        // setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.lvlessonWidget,
                svcIntent);
        // setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.lvlessonWidget, R.id.empty_view);
        return remoteViews;
    }
    
    
    /**
     * It receives the broadcast as per the action set on intent filters on
     * Manifest.xml once data is fetched from RemotePostService,it sends
     * broadcast and WidgetProvider notifies to change the data the data change
     * right now happens on ListProvider as it takes RemoteFetchService
     * listItemList as data
     */
  /*  @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(DATA_FETCHED)) {
            int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
 
    }
    */

}





