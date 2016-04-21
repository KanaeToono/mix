package com.example.conga.tvo.activities;

/**
 * Created by ConGa on 17/04/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.CategoryRecyclerPayersAdapter;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.RssParser;
import com.example.conga.tvo.utils.NetworkUtils;
import com.example.conga.tvo.variables.Values;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class CategoryPayersActivity extends AppCompatActivity {
    private static String TAG = CategoryPayersActivity.class.getSimpleName();
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
    public static final String MyPREFERENCES = "PubDate";
    public static final String PUB_DATE = "dateKey";
    public static final String LINK = "linkKey";
    SharedPreferences sharedpreferences;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    private final Handler timerHandler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ON CREATE CATEGORY ACTIVITY");
        setContentView(R.layout.categoryactivity);
        //create sharepreference
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // networking
        mNetworkUtils = new NetworkUtils(this);
        // handle toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMainActivity = new Intent(CategoryPayersActivity.this, MainActivity.class);
                startActivity(intentMainActivity);
            }
        });
        mImageView = (ImageView) findViewById(R.id.image);

        // collasing
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        //nhan du lieu tu main activity
        Intent intent = getIntent();
        mPayer = intent.getExtras().getInt(Values.paper);
        Log.d(TAG, mPayer + "");

        //handle recycler
        mRecyclerView = (RecyclerView) findViewById(R.id.category_list_items);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // kiem tra xem co du lieu o goi intent khong
        if (intent == null) {
            Log.d(TAG, "khong nhan dc");
        }
        // collassing toolbar
        mCollapsingToolbarLayout.setTitle(Values.PAYERS[mPayer]);
//            getSupportActionBar().setTitle(Values.PAYERS[mPayer]);
        //           mToolbar.setTitle(Values.PAYERS[mPayer]);
        Log.d(TAG, Values.PAYERS[mPayer]);

        // set adapter tuy thuco vao tung page

        // Vnexpress
        if (mPayer == 0) {
            try {
                mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(this, Values.BACKGROUND_PAYERS
                        , Values.CATEGORIES[mPayer], onItemClickCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 24h.com.vn
        else if (mPayer == 1) {
            mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(this, Values.BACKGROUND_24H_PAYER
                    , Values.CATEGORIES[mPayer], onItemClickCallback);

        }
        // dantri.com.vn
        else if (mPayer == 2) {
            mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(this, Values.BACKGROUNDS_DANTRI
                    , Values.CATEGORIES[mPayer], onItemClickCallback);

        }
        //vietnamnet.vn
        else if (mPayer == 3) {
            mCategoryRecyclerPayersAdapter = new CategoryRecyclerPayersAdapter(this, Values.BACKGROUNDS_VIETNAMNET
                    , Values.CATEGORIES[mPayer], onItemClickCallback);

        }

        // set adapter cho recycler view
        mRecyclerView.setAdapter(mCategoryRecyclerPayersAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        // bat dau timer , cho zoom background
        startTimer();
    }

    // bat dau timer
    public void startTimer() {
        //set a new Timer
        mTimer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        mTimer.schedule(mTimerTask, 0, 10000); //
    }

    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
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
                        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
                        mImageView.startAnimation(animation1);

                    }
                });
            }
        };
    }

// xu li khi click vao item tung trang
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            Log.d(TAG, "CREATE LISTITEM AGAIN");
            if (mNetworkUtils.isConnectingToInternet()) {
                int key = 1000 * mPayer + position;
                if (Values.MAP.containsKey(key)) {
                    Intent intent = new Intent(CategoryPayersActivity.this, ListLinksRssItemActivity.class);
                    intent.putExtra(Values.paper, mPayer);
                    intent.putExtra(Values.category, position);
                    intent.putExtra(Values.key, key);
                    startActivity(intent);
                } else {
                    mProgressDialog = ProgressDialog.show(CategoryPayersActivity.this, "", getString(R.string.loading));
                    new RssTask().execute(position);
                }


            } else {
                //xuat ra message neu khong co mang
                Toast.makeText(getApplicationContext(), R.string.message, Toast.LENGTH_SHORT).show();
            }
        }
    };

// RssTask dung parse rss
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
//            if (Values.PAYERS[mPayer].equalsIgnoreCase("VIETNAMNET")){
//                RssParserVietNamNet rssParserVietNamNet
//                        = new RssParserVietNamNet();
//                List<RssItemVietNamNet> vietNamNetList = rssParserVietNamNet.parser(Values.LINKS[mPayer][position]);
//                Values.MAP_VIET_NAM_NET.put(key, vietNamNetList);
//            }
//            else {
            RssParser rssParser = new RssParser(CategoryPayersActivity.this);
            List<RssItem> items = rssParser
                    .parser(Values.LINKS[mPayer][position]);
            Values.MAP.put(key, items);
            // }

            // save lastpubdate in here, convert to date
            // lastPubDate = list.get(0).getDate();
            //  saveToSharedPreferences(Values.LINKS[mPayer][position],items.get(0).getPubDate());
            //    Log.d(TAG, items.get(0).getPubDate().getTime()+"");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            Intent intent = new Intent(CategoryPayersActivity.this,
                    ListLinksRssItemActivity.class);
            intent.putExtra(Values.paper, mPayer);
            intent.putExtra(Values.category, position);
            intent.putExtra(Values.key, key);
            startActivity(intent);
            super.onPostExecute(result);

        }
    }

    private void saveToSharedPreferences(String link, String pubDate) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LINK, link);
        editor.putString(PUB_DATE, pubDate);
        editor.commit();
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
        Log.d(TAG, "START CATEGORYPAYERSACTIVITY");

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "PAUSE CATEGORYPAYERSACTIVITY");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "STOP CATEGORYPAYERSACTIVITY");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "RESTART CATEGORYPAYERSACTIVITY");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DESTROY CATEGORYPAYERSACTIVITY");

    }


}
