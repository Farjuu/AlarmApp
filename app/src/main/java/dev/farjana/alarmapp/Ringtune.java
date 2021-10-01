package dev.farjana.alarmapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by Farjana on 8/1/2017.
 */

public class Ringtune extends Service {

    MediaPlayer ringtune;
    int startId;
    boolean isRunning;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCmd(Intent intent, int flags) {

        String state = intent.getExtras().getString("extra");

        Toast.makeText(this, "ringtone class", Toast.LENGTH_LONG).show();

        assert state != null;
        int startId = 0;

        switch (state) {
            case "alarm On":
                startId = 1;
                break;
            case "alarm Off":
                startId = 0;
                break;
            default:
                break;
        }


        if (!this.isRunning && startId == 1) {
            Log.e("running", "log");

            Toast.makeText(this, "song off", Toast.LENGTH_LONG).show();

            ringtune = MediaPlayer.create(this, R.raw.ring);
            ringtune.start();

            Toast.makeText(this, "song on", Toast.LENGTH_LONG).show();
            this.isRunning = true;
            this.startId = 0;

            //notification
            NotificationManager notify_mngr = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            Toast.makeText(this, "notification", Toast.LENGTH_LONG).show();
            Intent intent_main = new Intent(this.getApplicationContext(), MainActivity.class);

            @SuppressLint("UnspecifiedImmutableFlag")
            PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,
                    intent_main, 0);

            Notification popup = new Notification.Builder(this)
                    .setContentTitle("Alarm!!!!")
                    .setContentText("click me!")
                    .setContentIntent(pendingIntent2)
                    .setAutoCancel(true)
                    .build();
            notify_mngr.notify(0, popup);

        } else if (this.isRunning && startId == 0) {
            ringtune.stop();
            ringtune.reset();

            this.isRunning = false;
            this.startId = 0;

        } else if (!this.isRunning) {
            this.startId = 0;
        } else {
            this.startId = 1;
        }

        return START_NOT_STICKY;
    }

    public void onDestroy() {
        Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        this.isRunning = false;

    }
}
