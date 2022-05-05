package dk.au.mad22spring.group04.cibusapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class RecipeService extends Service {
    // Code for foreground service is heavily inspired by code demo from the lecture: " DemoServices"
    public static final String SERVICE_CHANNEL = "serviceChannel";  //Notification channel name
    public static final int NOTIFICATION_ID = 42;                   //Notification id
    public static final String TAG = "RecipeService";

    ExecutorService execService;    //ExecutorService for running things off the main thread
    boolean started = false;        //indicating if Service is startet

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
        notification = getRandomNotification("");
        //call to startForeground will promote this Service to a foreground service (needs manifest permission)
        //also require the notification to be set, so that user can always see that Service is running in the background
        startForeground(NOTIFICATION_ID, notification);

        //Start notification loop if it's not started yet
        if (!started) {
            try {
                //Hack - to make it work.
                Thread.sleep(100);
                started = true;
                getUpdateRecipeServiceLoop();
            } catch (Exception e) {
                Log.d(TAG, "onStartCommand: " + e);
            }
        }


        //returning START_STICKY will make the Service restart again eventually if it gets killed off (e.g. due to resources)
        return START_STICKY;
    }

    private void getUpdateRecipeServiceLoop() {
        if (execService == null) {
            execService = Executors.newSingleThreadExecutor();
        } else if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        execService.submit(() -> {
            RecipeDTO randomRecipeFromDB = repository.getRandomRecipeFromDB();
            if(randomRecipeFromDB != null) {
                try {
                    if (randomRecipeFromDB.getName() == null) {
                        Log.d(TAG, "getUpdateRecipeServiceLoop: " + "Something went wrong");
                    } else {
                        notificationManager.notify(NOTIFICATION_ID, getRandomNotification(randomRecipeFromDB.getName() + " : " + randomRecipeFromDB.getDescription()));
                        Log.d(TAG, "run: " + randomRecipeFromDB.getName() + " : " + randomRecipeFromDB.getDescription());
                    }
                    Thread.sleep(60000);

                } catch (Exception e) {
                    Log.d(TAG, "Service error: " + e);
                }

            }
            if (started) {
                getUpdateRecipeServiceLoop();
            }
        });
    }

    private Notification getRandomNotification(String s) {
        //check for Android version - whether we need to create a notification channel (from Android 0 and up, API 26)
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(SERVICE_CHANNEL, "Foreground Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }

        //build the notification
        Notification notification = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                .setContentTitle(Constants.NOTIFICATION_TITLE)
                .setContentText(s)
                .setSmallIcon(R.drawable.default_recipe_image)
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