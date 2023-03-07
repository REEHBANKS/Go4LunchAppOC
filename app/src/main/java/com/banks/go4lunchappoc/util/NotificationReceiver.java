package com.banks.go4lunchappoc.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.banks.go4lunchappoc.R;
import com.banks.go4lunchappoc.activities.MainActivity;
import com.banks.go4lunchappoc.model.Restaurant;
import com.banks.go4lunchappoc.useCase.GetSelectedRestaurantByCurrentUserUseCase;

public class NotificationReceiver extends BroadcastReceiver {

    private GetSelectedRestaurantByCurrentUserUseCase useCase;
    private LiveData<Restaurant> restaurantLiveData;
    private Context context;
    private Restaurant restaurantNotification = new Restaurant();



    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        // Récupérer l'ID du restaurant à partir de l'intention
      //  String restaurantId = intent.getStringExtra("restaurantId");

        // Obtenir une instance de GetSelectedRestaurantByCurrentUserUseCase
        useCase = GetSelectedRestaurantByCurrentUserUseCase.getInstance();

        // Observer les changements dans le LiveData
        restaurantLiveData = useCase.getRestaurantLiveData();
        restaurantLiveData.observeForever(new Observer<Restaurant>() {
            @Override
            public void onChanged(Restaurant restaurant) {
                // Stocker l'objet restaurant dans une variable de la classe
                restaurantNotification = restaurant;
            }
        });

        // Créer le canal de notification pour les versions d'Android >= OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Nom du canal", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Description du canal");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Créer une intention pour ouvrir l'application lorsque l'utilisateur touche la notification
        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);

        // Créer une intention pour jouer un son
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(context, soundUri);

        // Afficher la notification sous forme de pop-up avec le son
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Restaurant reminder")
                .setContentText("N'oubliez pas votre réservation pour le restaurant " + restaurantNotification.getRestaurantName() + " à midi !")
                .setPriority(NotificationCompat.PRIORITY_HIGH )
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setFullScreenIntent(mainPendingIntent, true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, builder.build());
    }
}
