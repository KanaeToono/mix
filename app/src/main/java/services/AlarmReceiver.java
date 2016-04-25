package services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.fragments.EditTaskFragment;
import com.example.conga.tvo.models.Task;


/**
 * Created by ConGa on 1/04/2016.
 */
public class AlarmReceiver extends Service {
    private static String TAG = AlarmReceiver.class.getSimpleName();
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotification;

    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.d("Testing", "Service got created");
        Toast.makeText(this, "ServiceClass.onCreate()", Toast.LENGTH_LONG).show();
    }


    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub

        Task list = (Task) intent.getParcelableExtra("alarm");
        if (list == null) {
            Log.d(TAG, "NULL OBJECT");
        }
        Log.d(TAG, list + "");
        mNotification = (NotificationManager) this.getApplicationContext().
                getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), EditTaskFragment.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mBuilder = (NotificationCompat.Builder) new
                NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(list.getSubject_task())
                .setContentText(list.getDescription_task())
                .setSmallIcon(R.drawable.ic_alarm_on_white_18dp);
        PendingIntent pendingNotificationIntent = PendingIntent.
                getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingNotificationIntent);
        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if(alarmSound == null){
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if(alarmSound == null){
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }

        mBuilder.setSound(alarmSound);
        mNotification.notify(0, mBuilder.build());
        Toast.makeText(this, "Nhắc Nhở " + list.getSubject_task() + " ", Toast.LENGTH_LONG).show();
    }

}
