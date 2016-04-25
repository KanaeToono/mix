package com.example.conga.tvo.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.ListTasksAdapter1;
import com.example.conga.tvo.databases.databaseadapters.TaskDatabaseAdapter;
import com.example.conga.tvo.interfaces.ICallBackFragment;
import com.example.conga.tvo.models.Task;

import java.util.ArrayList;

public class ListViewTaskFragment extends Fragment implements ICallBackFragment, View.OnClickListener {
    private static String TAG = ListViewTaskFragment.class.getSimpleName();
    public static String ITEM_NAME = "item";
    //private ListTasksAdapter mListTasksAdapter;
    private ArrayList<Task> mArrayList;
    private Toolbar mToolbar;
    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;
    private static final boolean TOOLBAR_IS_STICKY = false;
    private Integer[] img_id_alarm = {R.drawable.ic_alarm_off_black_18dp, R.drawable.ic_alarm_on_black_18dp};
    private Integer[] img_id_mark_important_task = {R.drawable.star_off, R.drawable.star_on};
    private Integer[] img_id_mark_done_task = {R.drawable.ic_done_black_18dp, R.drawable.ic_clear_black_18dp};
    private ImageView imageViewAddNewTask;
    private ImageView imageViewSortTask;
    private ImageView imageViewShowTaskToday;
  //  private ImageView imageViewSearchTask;
    private SwipeMenuListView swipeMenuListView;
    private TaskDatabaseAdapter mTaskDatabaseAdapter;
    private ListTasksAdapter1 mListTasksAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " CREATE LIST TASK FRAG");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_view_task_fragment, container, false);
        swipeMenuListView = (SwipeMenuListView) view.findViewById(R.id.listView);
        mTaskDatabaseAdapter = new TaskDatabaseAdapter(getActivity());
        imageViewAddNewTask = (ImageView) view.findViewById(R.id.imageView_add_new_task);
        imageViewShowTaskToday = (ImageView) view.findViewById(R.id.imageView_tasks_today);
        imageViewSortTask = (ImageView) view.findViewById(R.id.imageView_sort_tasks_list);
        //imageViewSearchTask = (ImageView) view.findViewById(R.id.imageView_search_task_list);

        //show all task from database
      mTaskDatabaseAdapter = new TaskDatabaseAdapter(getActivity());
        mTaskDatabaseAdapter.openDB();
        mArrayList = new ArrayList<Task>();
       mArrayList = mTaskDatabaseAdapter.getAllTask();
       // mArrayList = mTaskDatabaseAdapter.getIncompleteTasks();
      //  mListTasksAdapter.notifyDataSetChanged();
        mListTasksAdapter= new ListTasksAdapter1(getActivity(), mArrayList, img_id_alarm, img_id_mark_important_task, img_id_mark_done_task);
        swipeMenuListView.setAdapter(mListTasksAdapter);
        mListTasksAdapter.notifyDataSetChanged();

        // handle navigationIcon for toolbar
//        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callBackFragment();
//            }
//        });


        // create menu Creator swipe menu to deleitem
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem openToEditTask = new SwipeMenuItem(getActivity());
                openToEditTask.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openToEditTask.setWidth(dp2px(90));
                openToEditTask.setIcon(R.drawable.ic_open_in_new_black_18dp);
                swipeMenu.addMenuItem(openToEditTask);
                SwipeMenuItem deleteTask = new SwipeMenuItem(getActivity());
                deleteTask.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                deleteTask.setWidth(dp2px(90));
                deleteTask.setIcon(R.drawable.delete1);
                swipeMenu.addMenuItem(deleteTask);
            }
        };

        swipeMenuListView.setMenuCreator(swipeMenuCreator);
        //set creator
        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int pos, SwipeMenu swipeMenu, int index) {
                switch (index) {
                    case 0:
                        // open task to edit
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("Task", mArrayList.get(pos));
                        Fragment toFragment = new EditTaskFragment();
                        toFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content, toFragment, "Task")
                                .addToBackStack("Task").commit();
                        break;
                    case 1:
                        //delete  task
                        final AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                        b.setTitle(R.string.question);
                        b.setMessage(R.string.messageCon);
                        b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    mTaskDatabaseAdapter.deleteTask(mArrayList.get(pos).getId_task());
                                    // mTaskDatabaseAdapter.closeDB();
                                    mArrayList.remove(pos);
                                    mListTasksAdapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity().getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        });
                        b.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        b.create().show();
                }
                return false;
            }
        });

        // set SwipeListener
        swipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        swipeMenuListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });

        // handle click add new task
        imageViewAddNewTask.setOnClickListener(this);
        // handle click show task today
        imageViewShowTaskToday.setOnClickListener(this);
        //handle click sort tasks list
        imageViewSortTask.setOnClickListener(this);
        //handle click search tasks list
        //imageViewSearchTask.setOnClickListener(this);
        return view;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getActivity().getResources().getDisplayMetrics());
    }

    // handle press back button

    // handle drag  item


    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    callBackFragment();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void callBackFragment() {

    }


    // handle cliick imageView
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imageView_add_new_task:
                Fragment addNewTaskFragmment = new AddNewTaskFragment();
                FragmentManager fragmentManager = getActivity()
                        .getSupportFragmentManager();
                ;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.main_content, addNewTaskFragmment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.imageView_tasks_today:
                try {
                    PopupMenu popup = new PopupMenu(getActivity(), imageViewShowTaskToday);
                    popup.getMenuInflater().inflate(R.menu.task_today_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_show_task_today:
                                    mListTasksAdapter.getTasksToday();
                                    break;
                                case R.id.action_show_complete_task:
                                    mListTasksAdapter.getCompleteTasks();
                                    break;
                                case R.id.action_show_incomplete_task:
                                   mListTasksAdapter.getIncompleteTasks();
                                    break;
                                case R.id.action_important_task:
                                    mListTasksAdapter.getImportantTasks();
                                    break;
                                case R.id.action_default:
                                    mListTasksAdapter.getAllTasks();
                                    break;
                                default:
                                    break;

                            }
                            return true;
                        }
                    });
                    popup.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imageView_sort_tasks_list:
                try {
                    PopupMenu popup = new PopupMenu(getActivity(), imageViewSortTask);
                    popup.getMenuInflater().inflate(R.menu.sort_task_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_sort_start_day_tasks:
                                    mListTasksAdapter.sortStartDayTasks();
                                    break;
                                case R.id.action_sort_deadline_day_tasks:
                                    mListTasksAdapter.sortDeadlineDayTasks();
                                    break;
                                case R.id.action_sort_piority_tasks:
                                    mListTasksAdapter.sortPiorityTasks();
                                    break;
                                case R.id.action_default:
                                    mListTasksAdapter.getAllTasks();
                                    break;
                                default:
                                    mListTasksAdapter.getAllTasks();
                                    break;


                            }
                            return true;
                        }
                    });
                    popup.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                mListTasksAdapter.getAllTasks();
                break;
        }
    }
}
