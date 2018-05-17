package com.assamgovernmentjob.fcm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akash
 * on 14/5/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message="";
        Log.d("this","push get");
        if(!remoteMessage.getData().isEmpty()) {
            try {
                JSONObject js = new JSONObject(remoteMessage.getData().get("payload"));
                message = (new JSONObject(js.get("aps").toString()).get("alert")).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Calling method to generate notification
            sendNotification(message);
        }
    }

    private void sendNotification(String message) {

    }
}
