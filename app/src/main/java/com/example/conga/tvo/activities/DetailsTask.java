package com.example.conga.tvo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.ListTasksAdapter1;
import com.example.conga.tvo.databases.databaseadapters.TaskDatabaseAdapter;
import com.example.conga.tvo.models.Task;


/**
 * Created by ConGa on 11/04/2016.
 */
public class DetailsTask extends Activity {
    private static String TAG = DetailsTask.class.getSimpleName();
    private TextView textViewSubjectTask;
    private TextView textViewDateStartTask;
    private TextView textViewTimeStartTask;
    private TextView textViewDateEndTask;
    private TextView textViewTimeEndTask;
    private TextView textViewDesTask;
    private Button btn_ok;
    private ListTasksAdapter1 mListTasksAdapter1;
    Task task = new Task();
    private TaskDatabaseAdapter mTaskDatabaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailstask);
        textViewSubjectTask = (TextView) findViewById(R.id.textViewSubject);
        textViewDateStartTask = (TextView) findViewById(R.id.textViewDateStartTask);
        textViewDateEndTask = (TextView) findViewById(R.id.textViewDateEndTask);
        textViewTimeEndTask = (TextView) findViewById(R.id.textViewTimeEndTask);
        textViewTimeStartTask = (TextView) findViewById(R.id.textViewTimeStartTask);
        textViewDesTask = (TextView) findViewById(R.id.textViewDesTask);
        btn_ok= (Button) findViewById(R.id.btn_ok);
        Bundle extras = getIntent().getExtras();

        int NID;
        if (extras != null) {
            task = extras.getParcelable("show");
            textViewSubjectTask.setText(task.getSubject_task());
            textViewDateStartTask.setText(task.getDate_start_task().toString());
            textViewTimeStartTask.setText(task.getTime_start_task());
            textViewDateEndTask.setText(task.getDate_end_task().toString());
            textViewTimeEndTask.setText(task.getTime_end_task());
            textViewDesTask.setText(task.getDescription_task());
            getIntent().removeExtra("show");
        }
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mTaskDatabaseAdapter = new TaskDatabaseAdapter(getApplicationContext());
                mTaskDatabaseAdapter.openDB();
                mTaskDatabaseAdapter.upDateStatus(0, task.getId_task());
//                Fragment fragment = null;
//                fragment = new ListViewTaskFragment();
//               // FragmentManager fragmentManager =getFragmentManager();
//                ;
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                fragmentTransaction.replace(R.id.main_content, fragment);
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                finish();
            }
        });
    }


}
