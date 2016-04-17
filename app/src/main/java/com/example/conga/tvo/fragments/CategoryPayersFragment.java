package com.example.conga.tvo.fragments;

/**
 * Created by ConGa on 17/04/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.activities.MainActivity;
import com.example.conga.tvo.adapters.recycleradapters.CategoryRecyclerPayersAdapter;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.interfaces.ICallbackFragmnet;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.RssParser;
import com.example.conga.tvo.models.Values;
import com.example.conga.tvo.utils.NetworkUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class CategoryPayersFragment extends Fragment implements ICallbackFragmnet{
    private static String TAG = CategoryPayersFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private int mPayer;
    private NetworkUtils mNetworkUtils;
    private ProgressDialog mProgressDialog;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private CategoryRecyclerPayersAdapter mCategoryRecyclerPayersAdapter;
    private ImageView mImageView;
    private Timer mTimer;
    private TimerTask mTimerTask;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    private final Handler timerHandler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ON CREATE CATEGORY FRAGMNET");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.categoryactivity, container, false);
        mNetworkUtils = new NetworkUtils(getActivity());

        // handle toolbar
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMainActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(intentMainActivity);
            }
        });
        mImageView = (ImageView) view.findViewById(R.id.image);

        // collasing
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        // recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.category_list_items);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //nhan
        mPayer = getArguments().getInt(Values.paper);
        mCollapsingToolbarLayout.setTitle(Values.PAYERS[mPayer]);

        // mToolbar.setTitle(Values.PAYERS[mPayer]);
        Log.d(TAG, Values.PAYERS[mPayer]);
        if (mPayer == 0) {
            try {
                mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(getActivity(), Values.BACKGROUND_PAYERS
                        , Values.CATEGORIES[mPayer], onItemClickCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (mPayer == 1) {
            mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(getActivity(), Values.BACKGROUND_24H_PAYER
                    , Values.CATEGORIES[mPayer], onItemClickCallback);

        } else if (mPayer == 2) {
            mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(getActivity(), Values.BACKGROUNDS_DANTRI
                    , Values.CATEGORIES[mPayer],onItemClickCallback);

        } else if (mPayer == 3) {
            mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(getActivity(), Values.BACKGROUNDS_VIETNAMNET
                    , Values.CATEGORIES[mPayer],onItemClickCallback);

        }

        mRecyclerView.setAdapter(mCategoryRecyclerPayersAdapter);
         mTimer = new Timer();
        initializeTimerTask();
        mTimer.schedule(mTimerTask, 0, 10000);

        return view;
    }


    // sau 10s là zoom ảnh 1 lần
    public void stoptimertask() {

        if (mTimer != null) {

            mTimer.cancel();

            mTimer = null;

        }

    }

    public void initializeTimerTask() {
        mTimerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom);
                        mImageView.startAnimation(animation1);
                    }
                });
            }
        };
    }

private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
    @Override
    public void onItemClicked(View view, int position) {
        if (mNetworkUtils.isConnectingToInternet()) {
            int key = 1000 * mPayer + position;
            if (Values.MAP.containsKey(key)) {
                Bundle args = new Bundle();
                args.putInt(Values.paper, mPayer);
                args.putInt(Values.category, position);
                args.putInt(Values.key, key);
                Fragment fragment = new ListLinksRssItemFragmnet();
                fragment.setArguments(args);
                FragmentManager frgManager = getActivity().getSupportFragmentManager();
                frgManager.beginTransaction().replace(R.id.frame_container, fragment)
                        .addToBackStack(null).commit();
            } else {
                mProgressDialog = ProgressDialog.show(getActivity(), "", "Loading...");
                new RssTask().execute(position);
            }
        }
        else {
            //delete  task
            Toast.makeText(getActivity(), R.string.message, Toast.LENGTH_SHORT).show();
        }
    }
};

    @Override
    public void callBackFragment() {
        Fragment articlesFrag = new ArticlesCategoryFragmnet();
        FragmentManager fragmentManager = getActivity()
                .getSupportFragmentManager();
        ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, articlesFrag);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class RssTask extends AsyncTask<Integer, Void, Void> {
        private int position;
        private int key;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//          mProgressDialog = new ProgressDialog(mContext);
//            mProgressDialog.setMessage(mMessage);
//            mProgressDialog.show() ;
        }

        @Override
        protected Void doInBackground(Integer... params) {
            position = params[0];
            key = 1000 * mPayer + position;
            RssParser rssParser = new RssParser();
            List<RssItem> items = rssParser
                    .parser(Values.LINKS[mPayer][position]);
            Values.MAP.put(key, items);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            Bundle args = new Bundle();
            args.putInt(Values.paper, mPayer);
            args.putInt(Values.category, position);
            args.putInt(Values.key, key);
            Fragment fragment = new ListLinksRssItemFragmnet();
            fragment.setArguments(args);
            FragmentManager frgManager = getActivity().getSupportFragmentManager();
            frgManager.beginTransaction().replace(R.id.frame_container, fragment)
                    .addToBackStack(null).commit();
            super.onPostExecute(result);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, " start");

    }

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
       // stoptimertask();

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
     //   stoptimertask();
    }
}
