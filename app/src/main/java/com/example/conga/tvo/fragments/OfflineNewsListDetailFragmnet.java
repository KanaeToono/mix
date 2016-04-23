package com.example.conga.tvo.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.ListNewsAdapter;
import com.example.conga.tvo.databases.RssItemHelper;
import com.example.conga.tvo.models.ContentRss;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ConGa on 19/04/2016.
 */
public class OfflineNewsListDetailFragmnet extends Fragment {
    private static String TAG = OfflineNewsListDetailFragmnet.class.getSimpleName();
    private SwipeMenuListView swipeMenuListView;
    private RssItemHelper mRssItemHelper;
    private ListNewsAdapter mListNewsAdapter;
    public static final String ITEM_NAME = "item" ;
    private ArrayList<ContentRss> mArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listofflinenewsfragmnet, container, false);
        swipeMenuListView = (SwipeMenuListView) view.findViewById(R.id.listView);
        mRssItemHelper = new RssItemHelper(getActivity());
        try {
            mRssItemHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mArrayList = new ArrayList<ContentRss>();
        mArrayList = mRssItemHelper.getAllItemsContent();
        mListNewsAdapter= new ListNewsAdapter(getActivity(), mArrayList);
        swipeMenuListView.setAdapter(mListNewsAdapter);
        mListNewsAdapter.notifyDataSetChanged();
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
                        // open
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("Item", mArrayList.get(pos));
                        Fragment toFragment = new HtmlTextviewShowNewsListsDetailsFragment();
                        toFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_content, toFragment)
                                .addToBackStack(null).commit();
                        break;
                    case 1:
                        //delete
                        final AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                        b.setTitle(R.string.question);
                        b.setMessage(R.string.messageCon);
                                b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    mRssItemHelper.deleteItemContent(mArrayList.get(pos).getId_content());
                                    // mTaskDatabaseAdapter.closeDB();
                                    mArrayList.remove(pos);
                                    mListNewsAdapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity().getApplicationContext(), R.string.delete, Toast.LENGTH_SHORT).show();

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



        return view;
    }
    private int dp2px (int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getActivity().getResources().getDisplayMetrics());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, " start");

    }

    @Override
    public void onResume() {
        super.onResume();
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    callBackFragment();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    private void callBackFragment() {
//        Fragment notyficationFrag = new ArticlesCategoryFragmnet();
//        FragmentManager fragmentManager = getActivity()
//                .getSupportFragmentManager();
//        ;
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.replace(R.id.main_content, notyficationFrag);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "PAUSE ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "STOP ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "on destroy view");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DESTROY ");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "ATTACH");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "ON DETACH ");
    }
}
