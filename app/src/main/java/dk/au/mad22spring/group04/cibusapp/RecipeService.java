package dk.au.mad22spring.group04.cibusapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.ExecutorService;

import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class RecipeService extends Service {
    // Code for foreground service is heavily inspired by code demo from the lecture: " DemoServices"
    public static final String SERVICE_CHANNEL = "serviceChannel";  //Notification channel name
    public static final int NOTIFICATION_ID = 42;                   //Notification id

    ExecutorService execService;    //ExecutorService for running things off the main thread
    boolean started = false;        //indicating if Service is startet

    //Notification
    Repository repository;
    Notification notification;
    NotificationManager notificationManager;
    public RecipeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}