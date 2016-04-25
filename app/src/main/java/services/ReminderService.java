package services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.activities.DetailsTask;
import com.example.conga.tvo.adapters.recycleradapters.ListTasksAdapter1;
import com.example.conga.tvo.models.Task;

/**
 * Created by ConGa on 9/04/2016.
 */
public class ReminderService   extends WakeReminderIntentService {
    private static String TAG = ReminderService.class.getSimpleName();
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotification;
    private ListTasksAdapter1 mListTasksAdapter1;

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    void doReminderWork(Intent intent) {
        Log.d("ReminderService", "Doing work.");
        Task list = (Task) intent.getParcelableExtra("id_alarm");
        mNotification = (NotificationManager) this.getApplicationContext().
                getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent notyficationIntent = new Intent(this.getApplicationContext(), DetailsTask.class);
      //  notyficationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notyficationIntent.putExtra("show", list);
        notyficationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mBuilder = (NotificationCompat.Builder) new
                NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(list.getSubject_task())
                .setContentText(list.getDescription_task())
                .setSmallIcon(R.drawable.ic_alarm_on_white_18dp);
        PendingIntent pendingNotificationIntent = PendingIntent.
                getActivity(this.getApplicationContext(), 0, notyficationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingNotificationIntent);
        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        mBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;
        mBuilder.setAutoCancel(true);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if (alarmSound == null) {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if (alarmSound == null) {
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
      //  mListTasksAdapter1.cancelAlarm(list.getId_task());

        mBuilder.setSound(alarmSound);
        mNotification.notify(1, mBuilder.build());
        Toast.makeText(this, "Nhắc Nhở " + list.getSubject_task() + " ", Toast.LENGTH_LONG).show();
    }
}