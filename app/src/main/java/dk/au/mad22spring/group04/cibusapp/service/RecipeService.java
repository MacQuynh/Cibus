package dk.au.mad22spring.group04.cibusapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class RecipeService extends Service {
    // Code for foreground service is heavily inspired by code demo from the lecture: " DemoServices"
    public static final String SERVICE_CHANNEL = "serviceChannel";  //Notification channel name
    public static final int NOTIFICATION_ID = 42;                   //Notification id
    public static final String TAG = "RecipeService";
    public static final String FOREGROUND_SERVICE = "Foreground Service";

    ExecutorService execService;    //ExecutorService for running things off the main thread
    boolean started = false;        //indicating if Service is started

    //Notification
    Repository repository;
    Notification notification;
    NotificationManager notificationManager;

    public RecipeService() {
    }

    //onCreate called before onStartCommand when Service first created
    @Override
    public void onCreate() {
        super.onCreate();
        repository = Repository.getInstance(getApplication());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notification = getRandomNotification("", -1);
        //call to startForeground will promote this Service to a foreground service (needs manifest permission)
        //also require the notification to be set, so that user can always see that Service is running in the background
        startForeground(NOTIFICATION_ID, notification);

        //Start notification loop if it's not started yet
        if (!started) {
            try {
                //Hack - to make it work.
                Thread.sleep(1000);
                started = true;
                getUpdateRecipeServiceLoop();
            } catch (Exception e) {
                Log.e(TAG, "onStartCommand: ", e);
            }
        }

        //returning START_STICKY will make the Service restart again eventually if it gets killed off (e.g. due to resources)
        return START_STICKY;
    }

    private void getUpdateRecipeServiceLoop() {
        //Inspired by the service made in our own individual code in assignment 2
        if (execService == null) {
            execService = Executors.newSingleThreadExecutor();
        } else if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        execService.submit(() -> {
            RecipeDTO randomRecipeFromDB = repository.getRandomRecipeFromDB();
            if (randomRecipeFromDB != null) {
                try {
                    if (randomRecipeFromDB.getName() == null) {
                    } else {
                        notificationManager.notify(NOTIFICATION_ID, getRandomNotification((randomRecipeFromDB.getName() + " : " + randomRecipeFromDB.getDescription()), randomRecipeFromDB.idRecipe));
                        Log.d(TAG, "run: " + randomRecipeFromDB.getName() + " : " + randomRecipeFromDB.getDescription() + "ID: " + randomRecipeFromDB.idRecipe);
                    }
                    Thread.sleep(6000);

                } catch (Exception e) {
                    Log.e(TAG, "getUpdateRecipeServiceLoop: ", e);
                }

            }
            if (started) {
                getUpdateRecipeServiceLoop();
            }
        });
    }

    private Notification getRandomNotification(String s, int i) {
        //check for Android version - whether we need to create a notification channel (from Android 0 and up, API 26)
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(SERVICE_CHANNEL, FOREGROUND_SERVICE, NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        //build the notification
        Notification notification = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                .setContentTitle(Constants.NOTIFICATION_TITLE)
                .setContentText(s)
                .setSmallIcon(R.drawable.default_recipe_image)
                .setAutoCancel(false)
                .setColor(getResources().getColor(R.color.ic_add_recipe_background))
                .build();
        return notification;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        started = false;
        super.onDestroy();
    }
}