package com.banks.go4lunchappoc.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class AlarmManagerHelper {

    public static void onRestaurantSelected(Context context, String restaurantId) {
        // Créer une instance de l'AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Créer une intention pour votre classe de notification
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);

        // Ajouter l'ID du restaurant sélectionné à l'intention
        notificationIntent.putExtra("restaurantId", restaurantId);

        // Créer une instance de PendingIntent avec l'intention créée ci-dessus et le flag FLAG_IMMUTABLE
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Définir l'heure à laquelle vous souhaitez que l'alarme se déclenche (midi)
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 44);
        calendar.set(Calendar.SECOND, 0);

        // Activer l'alarme
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("AlarmManagerHelper", "Alarm set for restaurant " + restaurantId + " at " + calendar.getTime());
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("AlarmManagerHelper", "Alarm set for restaurant " + restaurantId + " at " + calendar.getTime() + ")");
        }
    }
}
