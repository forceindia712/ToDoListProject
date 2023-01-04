package com.company.todolistproject;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class MyService extends Service {

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Service", "Service is running...");
                        SharedPreferences sp;

                        sp = getSharedPreferences("MyList", MODE_PRIVATE);
//                        sp = getApplicationContext().getSharedPreferences("MyList", Context.MODE_PRIVATE);

                        ArrayList<String> list = intent.getExtras().getStringArrayList("list_remove");
                        FileHelper.writeDataSP(list, sp);
                    }
                }
        ).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}