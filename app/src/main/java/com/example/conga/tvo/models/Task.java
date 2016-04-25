package com.example.conga.tvo.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

/**
 * Created by ConGa on 26/03/2016.
 */
public class Task implements Parcelable {

    private static String TAG = Task.class.getSimpleName();
    private int id_task;
    private String subject_task;
    private Date date_start_task;
    private String time_start_task;
    private Date date_end_task;
    private String time_end_task;
    private String description_task;
    private int piority_task;
    private int status_task;
    private int alarm_task;

    public Task() {
        Log.d(TAG, "CREATE TASK OBJECT");
    }

    public Task(String subject_task, Date date_start_task,
                String time_start_task, Date date_end_task, String time_end_task,
                String description_task, int piority_task, int status_task, int alarm_task
    ) {
        this.subject_task = subject_task;
        this.date_start_task = date_start_task;
        this.time_start_task = time_start_task;
        this.date_end_task = date_end_task;
        this.time_end_task = time_end_task;
        this.description_task = description_task;
        this.piority_task = piority_task;
        this.status_task = status_task;
        this.alarm_task =alarm_task;


    }

    public Task(int id_task, String subject_task, Date date_start_task,
                String time_start_task, Date date_end_task, String time_end_task,
                String description_task, int piority_task, int status_task, int alarm_task
    ) {
        this.id_task = id_task;
        this.subject_task = subject_task;
        this.date_start_task = date_start_task;
        this.time_start_task = time_start_task;
        this.date_end_task = date_end_task;
        this.time_end_task = time_end_task;
        this.description_task = description_task;
        this.piority_task = piority_task;
        this.status_task = status_task;
        this.alarm_task =alarm_task;


    }

    public int getAlarm_task() {
        return alarm_task;
    }

    public void setAlarm_task(int alarm_task) {
        this.alarm_task = alarm_task;
    }

    public int getPiority_task() {
        return piority_task;
    }

    public void setPiority_task(int piority_task) {
        this.piority_task = piority_task;
    }

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        Task.TAG = TAG;
    }

    public int getId_task() {
        return id_task;
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public String getSubject_task() {
        return subject_task;
    }

    public void setSubject_task(String subject_task) {
        this.subject_task = subject_task;
    }

    public Date getDate_start_task() {
        return date_start_task;
    }

    public void setDate_start_task(Date date_start_task) {
        this.date_start_task = date_start_task;
    }

    public String getTime_start_task() {
        return time_start_task;
    }

    public void setTime_start_task(String time_start_task) {
        this.time_start_task = time_start_task;
    }

    public Date getDate_end_task() {
        return date_end_task;
    }

    public void setDate_end_task(Date date_end_task) {
        this.date_end_task = date_end_task;
    }

    public String getTime_end_task() {
        return time_end_task;
    }

    public void setTime_end_task(String time_end_task) {
        this.time_end_task = time_end_task;
    }

    public String getDescription_task() {
        return description_task;
    }

    public void setDescription_task(String description_task) {
        this.description_task = description_task;
    }

    //  public int getPiority_task() {
    //     return piority_task;
    // }

//    public void setPiority_task(int piority_task) {
//        this.piority_task = piority_task;
//    }

    public int getStatus_task() {
        return status_task;
    }

    public void setStatus_task(int status_task) {
        this.status_task = status_task;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id_task=" + id_task +
                ", subject_task='" + subject_task + '\'' +
                ", date_start_task=" + date_start_task +
                ", time_start_task='" + time_start_task + '\'' +
                ", date_end_task=" + date_end_task +
                ", time_end_task='" + time_end_task + '\'' +
                ", description_task='" + description_task + '\'' +
                ", piority_task=" + piority_task +
                ", status_task=" + status_task +
                ", alarm_task=" + alarm_task +
                '}';
    }

    public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>() {
        public Task createFromParcel(Parcel source) {
            Task mTask = new Task();
            mTask.id_task = source.readInt();
            mTask.subject_task = source.readString();
          //  mTask.date_start_task = source.readString();
            mTask.date_start_task= new Date(source.readLong());
            mTask.time_end_task = source.readString();
          //  mTask.date_end_task = source.readString();
            mTask.date_end_task= new Date(source.readLong());
            mTask.time_end_task = source.readString();
            mTask.description_task = source.readString();
            mTask.piority_task = source.readInt();
            mTask.status_task = source.readInt();
            mTask.alarm_task=source.readInt();
            return mTask;
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id_task);
        parcel.writeString(subject_task);
        //parcel.writeString(date_start_task);
        parcel.writeLong(date_start_task.getTime());
        parcel.writeString(time_start_task);
       // parcel.writeString(date_end_task);
        parcel.writeLong(date_end_task.getTime());
        parcel.writeString(time_end_task);
        parcel.writeInt(piority_task);
        parcel.writeInt(status_task);
        parcel.writeInt(alarm_task);
    }
}
