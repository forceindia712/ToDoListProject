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
        Log.i("Service", "Service is running...");

        SharedPreferences sp;

        sp = getSharedPreferences("MyList", MODE_PRIVATE);;

        ArrayList<String> list = intent.getExtras().getStringArrayList("list");
        FileHelper.writeDataSP(list, sp);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}