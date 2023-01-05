package tbc.uncagedmist.bhulekhserver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import java.util.Calendar;

import tbc.uncagedmist.bhulekhserver.Common.Common;
import tbc.uncagedmist.bhulekhserver.Utility.Receiver;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        TextView tv =  findViewById(R.id.txtPower);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/sport.ttf");
        tv.setTypeface(face);

        if (Common.isConnectedToInternet(SplashActivity.this))  {
            SplashActivity.this.startActivity(new
                    Intent(SplashActivity.this.getApplicationContext(), MainActivity.class));
            SplashActivity.this.finish();
        }
        else    {
            Toast.makeText(this, "Please connect to the Internet.", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerNotification() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(SplashActivity.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 300, pendingIntent);
    }
}