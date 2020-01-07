package com.example.zouyi.appweiget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyWidget extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.layout_widget);
//        Intent intent=new Intent();
//        intent.setAction("btn.itchq.com");
        Intent intent=new Intent(context,MyService.class);
        intent.setAction("ACTION_START");
        PendingIntent pendingIntent=PendingIntent.getService(context, 0, intent, 0);

        remoteViews.setOnClickPendingIntent(R.id.button1, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

        RemoteViews remoteViews2=new RemoteViews(context.getPackageName(), R.layout.layout_widget);
//        Intent intent=new Intent();
//        intent.setAction("btn.itchq.com");
        Intent intent2=new Intent(context,MyService.class);
        intent2.setAction("ACTION_STOP");
        PendingIntent pendingIntent2=PendingIntent.getService(context, 0, intent2, 0);

        remoteViews2.setOnClickPendingIntent(R.id.button2, pendingIntent2);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews2);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // TODO Auto-generated method stub
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // TODO Auto-generated method stub
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // TODO Auto-generated method stub
        super.onDisabled(context);
    }
}
