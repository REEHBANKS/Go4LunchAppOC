package com.banks.go4lunchappoc.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Calendar;

public class AlarmManagerHelper {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void onRestaurantSelected(Context context, String restaurantId) {
        // Create an instance of AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an intent for your notification class
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);

        // Add the ID of the selected restaurant to the intent
        notificationIntent.putExtra("restaurantId", restaurantId);

        // Create an instance of PendingIntent with the intent created above and the FLAG_IMMUTABLE flag
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the time at which you want the alarm to trigger (noon)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 44);
        calendar.set(Calendar.SECOND, 0);

        // Activate the alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("AlarmManagerHelper", "Alarm set for restaurant " + restaurantId + " at " + calendar.getTime());
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("AlarmManagerHelper", "Alarm set for restaurant " + restaurantId + " at " + calendar.getTime() + ")");
        }
    }
}
