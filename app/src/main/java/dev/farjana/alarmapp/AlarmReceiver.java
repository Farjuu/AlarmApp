package dev.farjana.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Farjana on 8/1/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "receiver", Toast.LENGTH_SHORT).show();
        String getStr = intent.getExtras().getString("extra");
        Log.d("KeyF.", getStr);

        Intent service_intent = new Intent(context, Ringtune.class);
        service_intent.putExtra("extra", getStr);

        Toast.makeText(context, "src intent", Toast.LENGTH_LONG).show();
        context.startService(service_intent);
    }
}
