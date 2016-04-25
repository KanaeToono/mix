package com.example.conga.tvo.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.databases.databaseadapters.TaskDatabaseAdapter;
import com.example.conga.tvo.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ConGa on 28/03/2016.
 */
public class AddNewTaskFragment extends Fragment {
    private static String TAG = AddNewTaskFragment.class.getSimpleName();
    private static String mPiorityTask[] ={"Normal" , "Important"};
    Integer[] mImg_id = { R.drawable.ic_star_border_black_18dp, R.drawable.ic_star_black_18dp};
    private int mChoose_level_piority =0;
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
    private LinearLayout mLinearLayout;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private Calendar mCalendar;
    public static Calendar mCalendarE ;

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
      //  mLinearLayout = (LinearLayout) view.findViewById(R.id.);
//        Bundle bundle = getArguments();
//        final Task task = (Task) bundle
//                .getParcelable("Task");
//        if (task == null) {
//            Toast.makeText(getActivity().getApplicationContext(), "cmmn", Toast.LENGTH_SHORT).show();
//        }
//        Toast.makeText(getActivity().getApplicationContext(), "" + task.getId_task(), Toast.LENGTH_SHORT).show();
        mCalendar = Calendar.getInstance();
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, mPiorityTask);
        mSpinner = (Spinner) view.findViewById(R.id.spinner_piority_task);
        mSpinner.setAdapter(spinAdapter);
        editTextSubjectTask = (EditText) view.findViewById(R.id.editText_subject_task);
        editTextDescriptionTask = (EditText) view.findViewById(R.id.editText_description);
        btn_date_end_task = (Button) view.findViewById(R.id.btn_date_end);
        btn_date_start_task = (Button) view.findViewById(R.id.btn_date_start);
        btn_time_end_task = (Button) view.findViewById(R.id.btn_time_end);
        btn_time_start_task = (Button) view.findViewById(R.id.btn_time_start);
        img_btn_cancel = (ImageButton) view.findViewById(R.id.imageButton_cancel);
        img_btn_ok = (ImageButton) view.findViewById(R.id.imageButton_ok);
        img_piority_task= (ImageView) view.findViewById(R.id.imageView_btn_piority_task);
        btn_date_start_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // final Calendar calendar = Calendar.getInstance();
                yearStart = mCalendar.get(Calendar.YEAR);
                monthStart = mCalendar.get(Calendar.MONTH);
                dayStart = mCalendar.get(Calendar.DAY_OF_MONTH);
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
        btn_date_end_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  final Calendar c = Calendar.getInstance();
                yearEnd = mCalendar.get(Calendar.YEAR);
                monthEnd = mCalendar.get(Calendar.MONTH);
                dayEnd = mCalendar.get(Calendar.DAY_OF_MONTH);
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
        btn_time_start_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  final Calendar d = Calendar.getInstance();
                hourStart = mCalendar.get(Calendar.HOUR_OF_DAY);
                minuteStart = mCalendar.get(Calendar.MINUTE);
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
        btn_time_end_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // final Calendar d = Calendar.getInstance();
                hourEnd = mCalendar.get(Calendar.HOUR_OF_DAY);
                minuteEnd = mCalendar.get(Calendar.MINUTE);
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
        img_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment listTaskFragmment = new ListViewTaskFragment();
                FragmentManager fragmentManager = getActivity()
                        .getSupportFragmentManager();
                ;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.main_content, listTaskFragmment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                     mChoose_level_piority=mSpinner.getSelectedItemPosition();
                switch (mChoose_level_piority) {
                    case 0:
                        img_piority_task.setImageResource(mImg_id[0]);
                        break;
                    case 1:
                        img_piority_task.setImageResource(mImg_id[1]);
                        break;
                }
                Toast.makeText(getActivity().getApplicationContext(), "Piority Task " +mPiorityTask[mChoose_level_piority], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
       img_btn_ok.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String subjectTask = editTextSubjectTask.getText().toString();
               String descriptionTask = editTextDescriptionTask.getText().toString();
               String btn_date_start= btn_date_start_task.getText().toString();
               String btn_date_end = btn_date_end_task.getText().toString();
               String btn_time_start = btn_time_start_task.getText().toString();
               String btn_time_end = btn_time_end_task.getText().toString();
               Calendar calendar = Calendar.getInstance();
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
               String dateForButton = sdf.format(calendar.getTime());
               Log.d(TAG, dateForButton);
               Toast.makeText(getActivity().getApplicationContext(), ""+dateForButton, Toast.LENGTH_SHORT).show();
               Toast.makeText(getActivity().getApplicationContext(), ""+btn_date_start, Toast.LENGTH_SHORT).show();
               Date start_date = new Date();
               Date end_date = new Date();
               try {
                   start_date = sdf.parse(btn_date_start);
                   Toast.makeText(getActivity().getApplicationContext(), ""+start_date, Toast.LENGTH_SHORT).show();
                   end_date =sdf.parse(btn_date_end);
               } catch (ParseException e) {
                   e.printStackTrace();
               }
               if(btn_date_start.equals("dd/mm/yyyy")|| btn_time_start.equals("hh:mm")
           || btn_date_end.equals("dd/mm/yyyy")|| btn_time_end.equals("hh:mm")){
//                   Toast.makeText(getActivity().getApplicationContext(), "Fill up fiels", Toast.LENGTH_SHORT).show();


                   Snackbar snackbar = Snackbar
                           .make(view, R.string.empty_fields, Snackbar.LENGTH_LONG)
                           .setAction("Undo",  new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {

                               }
                           });
                   snackbar.setActionTextColor(Color.RED);
                   View snackbarView = snackbar.getView();
                   snackbarView.setBackgroundColor(Color.DKGRAY);
                   TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                   textView.setTextColor(Color.YELLOW);
                   snackbar.show();

               }

               else{
                   try {
                     //  TasksDatabase mTasksDatabase = new TasksDatabase(getActivity());
                       TaskDatabaseAdapter mTaskDatabaseAdapter = new TaskDatabaseAdapter(getActivity());
                       mTaskDatabaseAdapter.openDB();
                       Task task = new Task(subjectTask,
                               start_date, btn_time_start,
                               end_date,
                               btn_time_end,descriptionTask,mChoose_level_piority,0, 0);
                       Log.d(TAG, task+"");
                       mTaskDatabaseAdapter.addNewTask(task);
                       mTaskDatabaseAdapter.closeDB();
                       Toast.makeText(getActivity().getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                       Fragment fragment = null;
                       fragment = new ListViewTaskFragment();
                       FragmentManager taskmanager = getFragmentManager();
                       taskmanager.beginTransaction()
                               .replace(R.id.main_content, fragment).commit();
                   }catch (Exception e){
                       e.printStackTrace();
                   }


               }
               
           }
       });

        return view;
    }
}
