package dev.farjana.alarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker picker;
    AlarmManager manager;
    Button strBtn, stpBtn;
    TextView updateTxt;
    Context context;
    PendingIntent pendingIntent;
    int hour, minute;

    @SuppressLint({"UnspecifiedImmutableFlag", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        picker = findViewById(R.id.timePicker);
        updateTxt = findViewById(R.id.updateAlarm);

        final Calendar calendar = Calendar.getInstance();

        final Intent intent = new Intent(this.context, AlarmReceiver.class);
        strBtn = findViewById(R.id.setAlarm);

        strBtn.setOnClickListener(
                (View v) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                        calendar.set(Calendar.MINUTE, picker.getMinute());
                        Toast.makeText(MainActivity.this, "string clicked", Toast.LENGTH_LONG).show();
                        hour = picker.getHour();
                        minute = picker.getMinute();
                        Toast.makeText(this,hour,Toast.LENGTH_LONG).show();
                    }


                    setText("Alarm On " + hour + " : " + minute + "!!");
                    Log.d("alarm", "Hello");

                    Toast.makeText(MainActivity.this, "tttt", Toast.LENGTH_LONG).show();
                    intent.putExtra("extra", "alarm On");

                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    Toast.makeText(MainActivity.this, "ppp ccc", Toast.LENGTH_LONG).show();
                    manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
        );


        stpBtn = findViewById(R.id.off);

        stpBtn.setOnClickListener(
                (View v) -> {
                    setText("Alarm Off");
                    manager.cancel(pendingIntent);

                    intent.putExtra("extra", "Alarm Off ");
                    sendBroadcast(intent);
                }
        );
    }

    private void setText(String str) {
        updateTxt.setText(str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}