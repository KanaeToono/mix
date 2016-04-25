package com.example.conga.tvo.interfaces;


import com.example.conga.tvo.models.Task;

import java.util.ArrayList;

/**
 * Created by ConGa on 9/04/2016.
 */
public interface IListTaskAdapter {
    public ArrayList<Task> getTasksToday();
    public ArrayList<Task> getAllTasks();
    public ArrayList<Task> getCompleteTasks();
    public ArrayList<Task> getIncompleteTasks();
    public ArrayList<Task> getImportantTasks();
    public ArrayList<Task> sortDeadlineDayTasks();
    public ArrayList<Task> sortPiorityTasks();
    public ArrayList<Task> sortStartDayTasks();

}
