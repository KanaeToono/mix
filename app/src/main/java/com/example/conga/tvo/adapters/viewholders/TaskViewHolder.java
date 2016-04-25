package com.example.conga.tvo.adapters.viewholders;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.conga.tvo.R;


/**
 * Created by ConGa on 3/04/2016.
 */
public class TaskViewHolder extends RecyclerView.ViewHolder implements IItemTouchHelperViewHolder {
    CardView cardView;
    public TextView tvSubject_task;
    public TextView tvDate_start_task;
    public TextView tvTime_start_task;
    public TextView tvDate_end_task;
    public TextView tvTime_end_task;
    public ImageView imageView_title_icon;
    ImageView imageView_mark_task_important;
    ImageView imageView_mark_task_done;
    public ImageView imageView_turn_reminder_task;


    public TaskViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        tvSubject_task = (TextView) itemView.findViewById(R.id.textView_subject_task);
        tvDate_start_task = (TextView) itemView.findViewById(R.id.textView_date_start_task);
        tvTime_start_task = (TextView) itemView.findViewById(R.id.textView_time_start_task);
        tvDate_end_task = (TextView) itemView.findViewById(R.id.textView_date_end_task);
        tvTime_end_task = (TextView) itemView.findViewById(R.id.textView_time_end_task);
        imageView_title_icon = (ImageView) itemView.findViewById(R.id.imageView_title_icon);
        imageView_mark_task_important = (ImageView) itemView.findViewById(R.id.imageView_mark_task_important);
        imageView_mark_task_done = (ImageView) itemView.findViewById(R.id.imageView_mark_task_done);
        imageView_turn_reminder_task = (ImageView) itemView.findViewById(R.id.imageView_turn_reminder_task);


    }

//    public void bind(final Task item, final IOnItemClickListener listener) {
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                listener.onItemClick(item);
////            }
//       });
//    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}


