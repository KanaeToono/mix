package com.example.conga.tvo.interfaces;


import com.example.conga.tvo.models.Task;

import java.util.ArrayList;

/**
 * Created by ConGa on 26/03/2016.
 */
public interface ITaskListenerInterface  {
    public void addNewTask(Task task);
    public void editTask(Task task);
    public ArrayList<Task> getAllTask();
    public ArrayList<Task> sortAllTaskDeadlineDay(Task task);
    public void deleteTask(int id_task);
    public boolean updateTask(Task task);
    public void upDateStatus(int status , int id_task);
    public void upDateStatusDoneTask(int status, int id_task);
    public ArrayList<Task> getTaskToday();
    public ArrayList<Task> getCompleteTasks();
    public ArrayList<Task> getIncompleteTasks();
    public ArrayList<Task> getImportantTask();
    public ArrayList<Task>  sortPiorityTask();
    public ArrayList<Task>  sortDeadLineDayTask();
    public ArrayList<Task> sortStartDateTask();



}
