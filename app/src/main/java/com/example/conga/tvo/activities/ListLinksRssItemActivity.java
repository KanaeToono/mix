package com.example.conga.tvo.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.ListRssItemAdapter;
import com.example.conga.tvo.adapters.recycleradapters.ListRssItemVietNamNetAdapter;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.RssItemVietNamNet;
import com.example.conga.tvo.variables.Values;
import com.example.conga.tvo.utils.NetworkUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ListLinksRssItemActivity extends AppCompatActivity {
    private static String TAG = ListLinksRssItemActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private int mPayer;
    private int mCategory;
    private int mKey;
    private ListRssItemAdapter mListRssItemAdapter;
    private ListRssItemVietNamNetAdapter mListRssItemVietNamNetAdapter;
    private NetworkUtils mNetworkUtils;
    private RecyclerView mRecyclerView;
    public static final String MyPREFERENCES = "StatusLayoutItem" ;
    public static final String STATUS_KEY = "statusKry";
    public SharedPreferences sharedpreferences;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create listlinks");
        setContentView(R.layout.detailslistlinksrsslayout);
        //create sharepreference
        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
// config dowload image
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)

                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        // handle toobar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCategory = new Intent(ListLinksRssItemActivity.this, CategoryPayersActivity.class);
                startActivity(intentCategory);
            }
        });

        // receive data from category activity
        Bundle bundle = getIntent().getExtras();
        mPayer = bundle.getInt(Values.paper);
        mCategory = bundle.getInt(Values.category);
        mKey = bundle.getInt(Values.key);
        Log.d(TAG, mPayer + "");
        Log.d(TAG, mCategory + "");
        Log.d(TAG, mKey + "");
        if (bundle == null) {
            Log.d(TAG, "KHONG NHAN DC");
        }
        // set title for toolbar
        mToolbar.setTitle(Values.PAYERS[mPayer] + " - "
                + Values.CATEGORIES[mPayer][mCategory]);
        List<RssItem> items = Values.MAP.get(mKey);
        List<RssItemVietNamNet> itemsVietNamNet = Values.MAP_VIET_NAM_NET.get(mKey);
        Log.d(TAG, items + "");
        // recycler view show items
        mRecyclerView = (RecyclerView) findViewById(R.id.list_links);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set adapter
        if(mPayer == 3) {
            mListRssItemVietNamNetAdapter = new ListRssItemVietNamNetAdapter(this, itemsVietNamNet, onItemClickCallback);
            mRecyclerView.setAdapter(mListRssItemVietNamNetAdapter);
        }
        else {
            mListRssItemAdapter = new ListRssItemAdapter(this, items, onItemClickCallback);
            mRecyclerView.setAdapter(mListRssItemAdapter);
        }


        mNetworkUtils = new NetworkUtils(this);

    }

    // xu li khi nhan len item on recyclerview
    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            if (mNetworkUtils.isConnectingToInternet()) {
                Intent intent = new Intent(ListLinksRssItemActivity.this, ReadRssActivity.class);
                intent.putExtra(Values.key, mKey);
                intent.putExtra(Values.position, position);
                startActivity(intent);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("POSITION", position
                );
                editor.putInt("STATUS_KEY", 1);

                editor.commit();
            }
            else
            {
                Toast.makeText(getApplicationContext(), R.string.message, Toast.LENGTH_SHORT).show();
            }
        }


    };
// HANDLE PHẦN ĐANG LƯỚT MÀ MẤT MẠNG

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "START LISTLINKSRSSITEMACTIVITY");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "PAUSE LISTLINKSRSSITEMACTIVITY");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "STOP LISTLINKSRSSITEMACTIVITY");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "RESTART CATEGORYPAYERSACTIVITY");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DESTROY LISTLINKSRSSITEMACTIVITY");

    }


}
