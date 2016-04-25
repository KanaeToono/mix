package com.example.conga.tvo.adapters.recycleradapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.databases.databaseadapters.TaskDatabaseAdapter;
import com.example.conga.tvo.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ConGa on 4/04/2016.
 */
public class ListTasksAdapter extends BaseAdapter {
    private static String TAG = ListTasksAdapter.class.getSimpleName();
    private ArrayList<Task> mArrayListTasks;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private TaskDatabaseAdapter mTaskDatabaseAdapter;
    private ArrayList<Task> mArrayListTasksGetFromDatabase;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private Integer[] mImageAlarm;
    private Integer[] mImagePiorityTask;
    private Integer[] mImageDoneTask;


    public ListTasksAdapter(Context mContext, ArrayList<Task> mArrayListTasks, Integer[] mImageAlarm,
                            Integer[] mImagePiorityTask, Integer[] mImageDoneTask) {
        this.mArrayListTasks = mArrayListTasks;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mImageAlarm = mImageAlarm;
        this.mImagePiorityTask = mImagePiorityTask;
        this.mImageDoneTask = mImageDoneTask;
    }

    @Override
    public int getCount() {
        return mArrayListTasks.size();
    }

    @Override
    public Object getItem(int pos) {
        return mArrayListTasks.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    class ViewHolder {
        ImageView imageViewMarkDoneTask;
        ImageView imageViewMarkTaskImportant;
        ImageView imageViewTurnOnAlarm;
        ImageView imageViewDeleteTask;
        TextView textViewSubjectTask;
        TextView textViewDateStartTask;
        TextView textViewTimeStartTask;
        TextView textViewDateEndTask;
        TextView textViewTimeEndTask;
    }

    @Override
    public View getView(final int pos, View viewcontainer, ViewGroup viewGroup) {
        int checkAlarmTask = 0;
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
            // viewHolder.imageViewDeleteTask = (ImageView) viewcontainer.findViewById(R.id.imageView_delete_item_task);
            viewcontainer.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) viewcontainer.getTag();
        }
        viewHolder.textViewSubjectTask.setText(mArrayListTasks.get(pos).getSubject_task());
        viewHolder.textViewDateStartTask.setText(format.format(mArrayListTasks.get(pos).getDate_start_task()));
        viewHolder.textViewDateEndTask.setText(format.format(mArrayListTasks.get(pos).getDate_end_task()));
        viewHolder.textViewTimeStartTask.setText(mArrayListTasks.get(pos).getTime_start_task());
        viewHolder.textViewTimeEndTask.setText(mArrayListTasks.get(pos).getTime_end_task());
        // delete task / handle imageViewDeleteTask
        viewHolder.imageViewDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mTaskDatabaseAdapter.deleteTask(mArrayListTasks.get(pos).getId_task());

                    mArrayListTasks.remove(pos);
                    notifyDataSetChanged();
                    Toast.makeText(mContext.getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // check task done or not and set icon on listview
        if (mArrayListTasks.get(pos).getStatus_task() == 1) {
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
        } else {
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
        switch (mArrayListTasks.get(pos).getStatus_task()) {
            case 0:
                checkAlarmTask = 0;
                viewHolder.imageViewMarkDoneTask.setTag(R.drawable.ic_done_black_18dp);

                break;
            case 1:
                checkAlarmTask = 1;

                viewHolder.imageViewMarkDoneTask.setTag(R.drawable.ic_clear_black_18dp);
                break;
        }
        viewHolder.imageViewMarkDoneTask.setImageResource(mImageDoneTask[checkAlarmTask]);
        viewHolder.imageViewMarkDoneTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Integer) view.getTag() == R.drawable.ic_done_black_18dp) {
                    mTaskDatabaseAdapter.upDateStatusDoneTask(1, mArrayListTasks.get(pos).getId_task());
                    mTaskDatabaseAdapter.closeDB();
                    viewHolder.imageViewMarkDoneTask.setImageResource(R.drawable.ic_clear_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), "mark done task " + mArrayListTasks.get(pos), Toast.LENGTH_SHORT).show();
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
                } else if ((Integer) view.getTag() == R.drawable.ic_clear_black_18dp) {
                    mTaskDatabaseAdapter.upDateStatusDoneTask(0, mArrayListTasks.get(pos).getId_task());
                    mTaskDatabaseAdapter.closeDB();
                    viewHolder.imageViewMarkDoneTask.setImageResource(R.drawable.ic_done_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), "cancel done task " + mArrayListTasks.get(pos), Toast.LENGTH_SHORT).show();
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
        switch (mArrayListTasks.get(pos).getAlarm_task()) {
            case 0:
                checkAlarmTask = 0;
                viewHolder.imageViewTurnOnAlarm.setTag(R.drawable.ic_alarm_off_black_18dp);
                break;
            case 1:
                checkAlarmTask = 1;
                viewHolder.imageViewTurnOnAlarm.setTag(R.drawable.ic_alarm_on_black_18dp);
                ;
                break;
        }
        viewHolder.imageViewTurnOnAlarm.setImageResource(mImageAlarm[checkAlarmTask]);
        viewHolder.imageViewTurnOnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Integer) view.getTag() == R.drawable.ic_alarm_off_black_18dp) {
                    mTaskDatabaseAdapter.upDateStatus(1, mArrayListTasks.get(pos).getId_task());
                    viewHolder.imageViewTurnOnAlarm.setImageResource(R.drawable.ic_alarm_on_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), "turn on alarm " + mArrayListTasks.get(pos), Toast.LENGTH_SHORT).show();
                    view.setTag(R.drawable.ic_alarm_on_black_18dp);
                } else if ((Integer) view.getTag() == R.drawable.ic_alarm_on_black_18dp) {
                    mTaskDatabaseAdapter.upDateStatus(0, mArrayListTasks.get(pos).getId_task());
                    mTaskDatabaseAdapter.closeDB();
                    viewHolder.imageViewTurnOnAlarm.setImageResource(R.drawable.ic_alarm_off_black_18dp);
                    Toast.makeText(mContext.getApplicationContext(), "turn off alarm" + mArrayListTasks.get(pos), Toast.LENGTH_SHORT).show();
                    view.setTag(R.drawable.ic_alarm_off_black_18dp);
                }

            }
        });
        // set On listview icon of piority task
        switch (mArrayListTasks.get(pos).getPiority_task()) {
            case 0:
                checkAlarmTask = 0;
                break;
            case 1:
                checkAlarmTask = 1;
                break;
        }
        viewHolder.imageViewMarkTaskImportant.setImageResource(mImagePiorityTask[checkAlarmTask]);
        return viewcontainer;
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