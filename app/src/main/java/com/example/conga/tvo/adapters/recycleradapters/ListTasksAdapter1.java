package com.example.conga.tvo.adapters.recycleradapters;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.broadcastreveivers.OnAlarmReceiver;
import com.example.conga.tvo.databases.databaseadapters.TaskDatabaseAdapter;
import com.example.conga.tvo.interfaces.IListTaskAdapter;
import com.example.conga.tvo.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ConGa on 4/04/2016.
 */
public class ListTasksAdapter1 extends BaseAdapter implements IListTaskAdapter {
    private static String TAG = ListTasksAdapter1.class.getSimpleName();
    private ArrayList<Task> mArrayListTasks;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private TaskDatabaseAdapter mTaskDatabaseAdapter;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    private Integer[] mImageAlarm;
    private Integer[] mImagePiorityTask;
    private Integer[] mImageDoneTask;


    public ListTasksAdapter1(Context mContext, ArrayList<Task> mArrayListTasks, Integer[] mImageAlarm, Integer[] mImagePiorityTask, Integer[] mImageDoneTask) {
        this.mArrayListTasks = mArrayListTasks;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mImageAlarm = mImageAlarm;
        this.mImagePiorityTask =mImagePiorityTask;
        this.mImageDoneTask = mImageDoneTask;
        mTaskDatabaseAdapter = new TaskDatabaseAdapter(mContext);
        mTaskDatabaseAdapter.openDB();
    }

    @Override
    public int getCount() {
        return mArrayListTasks.size();
    }

    @Override
    public Object getItem(int pos) {
        return mArrayListTasks.get(pos);
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public ArrayList<Task> getTasksToday() {
        mArrayListTasks = mTaskDatabaseAdapter.getTaskToday();
        notifyDataSetChanged();
        return mArrayListTasks;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        mArrayListTasks = mTaskDatabaseAdapter.getAllTask();
        notifyDataSetChanged();
        return mArrayListTasks;
    }

    // get completetasks
    @Override
    public ArrayList<Task> getCompleteTasks() {
        mArrayListTasks = mTaskDatabaseAdapter.getCompleteTasks();
        notifyDataSetChanged();
        return mArrayListTasks;
    }
    // get incompletetasks
    @Override
    public ArrayList<Task> getIncompleteTasks() {
        mArrayListTasks = mTaskDatabaseAdapter.getIncompleteTasks();
        notifyDataSetChanged();
        return mArrayListTasks;
    }
 // get important tasks
    @Override
    public ArrayList<Task> getImportantTasks() {
        mArrayListTasks = mTaskDatabaseAdapter.getImportantTask();
        notifyDataSetChanged();
        return mArrayListTasks;
    }

    @Override
    public ArrayList<Task> sortDeadlineDayTasks() {
        mArrayListTasks = mTaskDatabaseAdapter.sortDeadLineDayTask();
        notifyDataSetChanged();
        return mArrayListTasks;
    }

    @Override
    public ArrayList<Task> sortPiorityTasks() {
        mArrayListTasks = mTaskDatabaseAdapter.sortPiorityTask();
        notifyDataSetChanged();
        return mArrayListTasks;
    }

    @Override
    public ArrayList<Task> sortStartDayTasks() {
        mArrayListTasks = mTaskDatabaseAdapter.sortStartDateTask();
        notifyDataSetChanged();
        return mArrayListTasks;
    }

    // class ViewHolder
    class ViewHolder {
        ImageView imageViewMarkDoneTask;
        ImageView imageViewMarkTaskImportant;
        ImageView imageViewTurnOnAlarm;
       // ImageView imageViewDeleteTask;
        TextView textViewSubjectTask;
        TextView textViewDateStartTask;
        TextView textViewTimeStartTask;
        TextView textViewDateEndTask;
        TextView textViewTimeEndTask;
    }
//getView
    @Override
    public View getView(final int pos, View viewcontainer, ViewGroup viewGroup) {
         int checkAlarmTask =0;
         mTaskDatabaseAdapter = new TaskDatabaseAdapter(mContext);
        mTaskDatabaseAdapter.openDB();
        final ViewHolder viewHolder;
        if (viewcontainer == null) {
            viewcontainer = mLayoutInflater.inflate(R.layout.item_list_view_task_fragmnet, null);
            viewHolder = new ViewHolder();
            viewHolder.imageViewTurnOnAlarm = (ImageView) viewcontainer.findViewById(R.id.imageView_turn_reminder_task);
            viewHolder.imageViewMarkTaskImportant = (ImageView) viewcontainer.findViewById(R.id.imageView_mark_task_important);
            viewHolder.imageViewMarkDoneTask = (ImageView) viewcontainer.findViewById(R.id.imageView_mark_task_done);
            viewHolder.textViewSubjectTask = (TextView) viewcontainer.findViewById(R.id.textView_subject_task);
            viewHolder.textViewDateStartTask = (TextView) viewcontainer.findViewById(R.id.textView_date_start_task);
            viewHolder.textViewTimeStartTask = (TextView) viewcontainer.findViewById(R.id.textView_time_start_task);
            viewHolder.textViewDateEndTask = (TextView) viewcontainer.findViewById(R.id.textView_date_end_task);
            viewHolder.textViewTimeEndTask = (TextView) viewcontainer.findViewById(R.id.textView_time_end_task);
            //viewHolder.imageViewDeleteTask = (ImageView) viewcontainer.findViewById(R.id.imageView_delete_item_task);
            viewcontainer.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) viewcontainer.getTag();
        }
        viewHolder.textViewSubjectTask.setText(mArrayListTasks.get(pos).getSubject_task());
        viewHolder.textViewDateStartTask.setText(format.format(mArrayListTasks.get(pos).getDate_start_task()));
        viewHolder.textViewDateEndTask.setText(format.format(mArrayListTasks.get(pos).getDate_end_task()));
        viewHolder.textViewTimeStartTask.setText(mArrayListTasks.get(pos).getTime_start_task());
        viewHolder.textViewTimeEndTask.setText(mArrayListTasks.get(pos).getTime_end_task());
        // check task done or not and set icon on listview
        if(mArrayListTasks.get(pos).getStatus_task() == 1){
            viewHolder.textViewSubjectTask.setPaintFlags(viewHolder.textViewSubjectTask.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
                  viewHolder.textViewDateStartTask.setPaintFlags(viewHolder.textViewDateStartTask.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
              viewHolder.textViewDateEndTask.setPaintFlags(viewHolder.textViewDateEndTask.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textViewTimeStartTask.setPaintFlags(viewHolder.textViewTimeStartTask.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textViewTimeEndTask.setPaintFlags(viewHolder.textViewTimeEndTask.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            viewHolder.textViewSubjectTask.setPaintFlags(viewHolder.textViewSubjectTask.getPaintFlags()
                    & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.textViewDateStartTask.setPaintFlags(viewHolder.textViewDateStartTask.getPaintFlags()
                    & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.textViewTimeStartTask.setPaintFlags(viewHolder.textViewTimeStartTask.getPaintFlags()
                    & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.textViewDateEndTask.setPaintFlags(viewHolder.textViewDateEndTask.getPaintFlags()
                    & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.textViewTimeEndTask.setPaintFlags(viewHolder.textViewTimeEndTask.getPaintFlags()
                    & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        // handle click imageViewMarkDoneTask
        switch(mArrayListTasks.get(pos).getStatus_task())
        {
            case 0:  checkAlarmTask = 0;
                viewHolder.imageViewMarkDoneTask.setTag(R.drawable.ic_done_black_18dp);

                break;
            case 1: checkAlarmTask = 1 ;

                viewHolder.imageViewMarkDoneTask.setTag(R.drawable.ic_clear_black_18dp);
                break;
        }
        viewHolder.imageViewMarkDoneTask.setImageResource(mImageDoneTask[checkAlarmTask]);
        viewHolder.imageViewMarkDoneTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Integer)view.getTag()== R.drawable.ic_done_black_18dp){
                    mTaskDatabaseAdapter.upDateStatusDoneTask(1 , mArrayListTasks.get(pos).getId_task());
                    viewHolder.imageViewMarkDoneTask.setImageResource(R.drawable.ic_clear_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), R.string.mark_done_task, Toast.LENGTH_SHORT).show();
                    view.setTag(R.drawable.ic_clear_black_18dp);
                    viewHolder.textViewSubjectTask.setPaintFlags(viewHolder.textViewSubjectTask.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textViewDateStartTask.setPaintFlags(viewHolder.textViewDateStartTask.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textViewDateEndTask.setPaintFlags(viewHolder.textViewDateEndTask.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textViewTimeStartTask.setPaintFlags(viewHolder.textViewTimeStartTask.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textViewTimeEndTask.setPaintFlags(viewHolder.textViewTimeEndTask.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else if((Integer)view.getTag()== R.drawable.ic_clear_black_18dp) {
                    mTaskDatabaseAdapter.upDateStatusDoneTask(0, mArrayListTasks.get(pos).getId_task());
                    viewHolder.imageViewMarkDoneTask.setImageResource(R.drawable.ic_done_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), R.string.cancle_mark_done_task,Toast.LENGTH_SHORT).show();
                    view.setTag(R.drawable.ic_done_black_18dp);
                    viewHolder.textViewSubjectTask.setPaintFlags(viewHolder.textViewSubjectTask.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    viewHolder.textViewDateStartTask.setPaintFlags(viewHolder.textViewDateStartTask.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    viewHolder.textViewTimeStartTask.setPaintFlags(viewHolder.textViewTimeStartTask.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    viewHolder.textViewDateEndTask.setPaintFlags(viewHolder.textViewDateEndTask.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    viewHolder.textViewTimeEndTask.setPaintFlags(viewHolder.textViewTimeEndTask.getPaintFlags()
                            & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
   });

  //////// Handle click imageViewTurnOnAlarm
        switch(mArrayListTasks.get(pos).getAlarm_task())
        {
            case 0:  checkAlarmTask = 0;
                viewHolder.imageViewTurnOnAlarm.setTag(R.drawable.ic_alarm_off_black_18dp);
                break;
            case 1: checkAlarmTask = 1;
                viewHolder.imageViewTurnOnAlarm.setTag(R.drawable.ic_alarm_on_black_18dp);
            ;break;
        }
        viewHolder.imageViewTurnOnAlarm.setImageResource(mImageAlarm[checkAlarmTask]);
        viewHolder.imageViewTurnOnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Integer)view.getTag()== R.drawable.ic_alarm_off_black_18dp){
                    turnOnAlarm(mArrayListTasks.get(pos));
                    mTaskDatabaseAdapter.upDateStatus(1 , mArrayListTasks.get(pos).getId_task());
                    viewHolder.imageViewTurnOnAlarm.setImageResource(R.drawable.ic_alarm_on_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), R.string.turn_on_alarm, Toast.LENGTH_SHORT).show();
                    view.setTag(R.drawable.ic_alarm_on_black_18dp);
                }
                else if((Integer)view.getTag()== R.drawable.ic_alarm_on_black_18dp) {
                   cancelAlarm(mArrayListTasks.get(pos).getId_task());
                    mTaskDatabaseAdapter.upDateStatus(0, mArrayListTasks.get(pos).getId_task());
                    viewHolder.imageViewTurnOnAlarm.setImageResource(R.drawable.ic_alarm_off_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), R.string.turn_off_alarm ,Toast.LENGTH_SHORT).show();
                    view.setTag(R.drawable.ic_alarm_off_black_18dp);
                }
            }
        });
        //handle turn on alarm

        // set On listview icon of piority task
        switch(mArrayListTasks.get(pos).getPiority_task())
        {
            case 0:  checkAlarmTask = 0;break;
            case 1: checkAlarmTask = 1 ;break;
        }
        viewHolder.imageViewMarkTaskImportant.setImageResource(mImagePiorityTask[checkAlarmTask]);
       // mTaskDatabaseAdapter.closeDB();
        return viewcontainer;
    }
// handle turn on alarm

    private void turnOnAlarm(Task task) {
        Date testDate = null;
        try {
            testDate = task.getDate_start_task();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String newFormat = formatter.format(testDate);

        String date[] = newFormat.toString().split("/");
        String time[] = task.getTime_start_task().toString().split(":");

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();
        calSet.set(Calendar.YEAR, Integer.parseInt(date[2]));
        calSet.set(Calendar.MONTH, Integer.parseInt(date[1])-1);
        calSet.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
        calSet.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calSet.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {
            // Today Set time passed, count to tomorrow
            calSet.add(Calendar.DATE, 1);
        }
        System.out.println("Da chuyen qua ONTIMESET");

        setAlarm(calSet, task);
    }
    // set alarm method
    public void setAlarm(Calendar calendar , Task task){

        Intent intent = new Intent(mContext, OnAlarmReceiver.class);
        intent.putExtra("alarm", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) mContext
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 6 * 1000, pendingIntent);
    }
// cancel alarm
public void cancelAlarm(int i) {
    Toast.makeText(mContext, "Đã hủy nhắc nhở", Toast.LENGTH_SHORT)
            .show();
    Intent intent = new Intent(mContext, OnAlarmReceiver.class);
    PendingIntent pi = PendingIntent
            .getService(mContext, i, intent, 0);
    AlarmManager alarmManager = (AlarmManager) mContext
            .getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(pi);
}

    // extensive for api 21 to check image
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public boolean checkImageResource(Context ctx, ImageView imageView,
                                      int imageResource) {
        boolean result = false;

        if (ctx != null && imageView != null && imageView.getDrawable() != null) {
            Drawable.ConstantState constantState;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                constantState = ctx.getResources()
                        .getDrawable(imageResource, ctx.getTheme())
                        .getConstantState();
            } else {
                constantState = ctx.getResources().getDrawable(imageResource)
                        .getConstantState();
            }

            if (imageView.getDrawable().getConstantState() == constantState) {
                result = true;
            }
        }
        return result;
    }
}