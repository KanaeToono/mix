package com.example.conga.tvo.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.databases.databaseadapters.TaskDatabaseAdapter;
import com.example.conga.tvo.interfaces.ICallBackFragment;
import com.example.conga.tvo.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import services.AlarmReceiver;

/**
 * Created by ConGa on 28/03/2016.
 */
public class EditTaskFragment extends Fragment implements ICallBackFragment {
    private static String TAG = EditTaskFragment.class.getSimpleName();
    private static String mPiorityTask[] = {"Normal", "Important"};
    Integer[] mImg_id = {R.drawable.ic_star_border_black_18dp, R.drawable.ic_star_black_18dp};
    private int mChoose_level_piority = 0;
    private EditText editTextSubjectTask;
    private EditText editTextDescriptionTask;
    private Button btn_date_start_task;
    private Button btn_date_end_task;
    private Button btn_time_start_task;
    private Button btn_time_end_task;
    private ImageButton img_btn_cancel;
    private ImageButton img_btn_ok;
    private ImageView img_piority_task;
    private int hourStart;
    private int minuteStart;
    private int yearStart;
    private int monthStart;
    private int dayStart;
    private int hourEnd;
    private int minuteEnd;
    private int yearEnd;
    private int monthEnd;
    private int dayEnd;
    private Spinner mSpinner;
    private TextView textViewTitle;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Create add item task fragmnet");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.add_new_task_fragment, container, false);
        //////////////////////////
        Bundle bundle = getArguments();
        final Task task = (Task) bundle
                .getParcelable("Task");
        if (task == null) {
            Toast.makeText(getActivity().getApplicationContext(), "cmmn", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity().getApplicationContext(), "" + task.getId_task(), Toast.LENGTH_SHORT).show();
        ///////////////////
        textViewTitle = (TextView) view.findViewById(R.id.tvTitle);
        textViewTitle.setText("Edit Task");
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mPiorityTask);
        mSpinner = (Spinner) view.findViewById(R.id.spinner_piority_task);
        mSpinner.setAdapter(spinAdapter);
        editTextSubjectTask = (EditText) view.findViewById(R.id.editText_subject_task);
        editTextSubjectTask.setText(task.getSubject_task());
        editTextDescriptionTask = (EditText) view.findViewById(R.id.editText_description);
        editTextDescriptionTask.setText(task.getDescription_task());
        btn_date_end_task = (Button) view.findViewById(R.id.btn_date_end);
        btn_date_end_task.setText(format.format(task.getDate_end_task()));
        btn_date_start_task = (Button) view.findViewById(R.id.btn_date_start);
        btn_date_start_task.setText(format.format(task.getDate_start_task()));
        btn_time_end_task = (Button) view.findViewById(R.id.btn_time_end);
        btn_time_end_task.setText(task.getTime_end_task());
        btn_time_start_task = (Button) view.findViewById(R.id.btn_time_start);
        btn_time_start_task.setText(task.getTime_start_task());
        img_btn_cancel = (ImageButton) view.findViewById(R.id.imageButton_cancel);
        img_btn_ok = (ImageButton) view.findViewById(R.id.imageButton_ok);
        img_piority_task = (ImageView) view.findViewById(R.id.imageView_btn_piority_task);
//        editTextSubjectTask.setVisibility(View.GONE);
//        editTextDescriptionTask.setVisibility(View.GONE);

        // Start date picker dialog
        btn_date_start_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                yearStart = calendar.get(Calendar.YEAR);
                monthStart = calendar.get(Calendar.MONTH);
                dayStart = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int myear,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                btn_date_start_task.setText(+dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + myear);

                            }
                        }, yearStart, monthStart, dayStart);
                datePickerDialog.show();

            }
        });

        /// end day picker dialog
        btn_date_end_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                yearEnd = c.get(Calendar.YEAR);
                monthEnd = c.get(Calendar.MONTH);
                dayEnd = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int myear,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                btn_date_end_task.setText(+dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + myear);

                            }
                        }, yearEnd, monthEnd, dayEnd);
                datePickerDialog.show();
            }
        });

        //Time start dialog
        btn_time_start_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar d = Calendar.getInstance();
                hourStart = d.get(Calendar.HOUR_OF_DAY);
                minuteStart = d.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int mminute) {
                                // TODO Auto-generated method stub
                                btn_time_start_task.setText(hourOfDay + ":" + mminute);
                            }
                        }, hourStart, minuteStart, false);
                tpd.show();

            }

        });

        //Time end dialog
        btn_time_end_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar d = Calendar.getInstance();
                hourEnd = d.get(Calendar.HOUR_OF_DAY);
                minuteEnd = d.get(Calendar.MINUTE);
                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int mminute) {
                                // TODO Auto-generated method stub
                                btn_time_end_task.setText(hourOfDay + ":" + mminute);
                            }
                        }, hourEnd, minuteEnd, false);
                tpd.show();
            }
        });

        // handle cancel button
        img_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBackFragment();
            }
        });

        // handle spinner
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mChoose_level_piority = mSpinner.getSelectedItemPosition();
                switch (mChoose_level_piority) {
                    case 0:
                        img_piority_task.setImageResource(mImg_id[0]);
                        break;
                    case 1:
                        img_piority_task.setImageResource(mImg_id[1]);
                        break;
                }
                Toast.makeText(getActivity().getApplicationContext(), "Piority Task " + mPiorityTask[mChoose_level_piority], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // save to database
        img_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  cancelAlarm(task.getId_task());
                Date start_date = new Date();
                Date end_date = new Date();
                try {
                    start_date = format.parse(btn_date_start_task.getText().toString());
                    end_date =format.parse(btn_date_end_task.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Task _task = new Task(task.getId_task(), task.getSubject_task(),
                        start_date, btn_time_start_task.getText().toString(),
                        end_date, btn_time_end_task.getText().toString(), task.getDescription_task(),
                        mChoose_level_piority, 0, 0);
                TaskDatabaseAdapter mDbHelper = new TaskDatabaseAdapter(getActivity());
                mDbHelper.openDB();
                mDbHelper.editTask(_task);
                callBackFragment();

            }
        });
        return view;
    }

    void cancelAlarm(int i) {
        Toast.makeText(getActivity(), "Cancel Alarm ", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getService(getActivity(), i, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity()
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
    }

    @Override
    public void callBackFragment() {
        Fragment fragment = null;
        fragment = new ListViewTaskFragment();
        FragmentManager taskmanager = getFragmentManager();
        taskmanager.beginTransaction()
                .replace(R.id.main_content, fragment).addToBackStack(null).commit();
    }
}
