package com.example.conga.tvo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.ListRssItemAdapter;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.Values;
import com.example.conga.tvo.utils.NetworkUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ListLinksRssItem extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static String TAG = ListLinksRssItem.class.getSimpleName();
    private ListView mListView;
    private Toolbar mToolbar;
    private int mPayer;
    private int mCategory;
    private int mKey;
    private ListRssItemAdapter mListRssItemAdapter;
    private NetworkUtils mNetworkUtils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)

                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        setContentView(R.layout.listlinksrsslayout);
        mListView = (ListView) findViewById(R.id.listrsslinks);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCategory = new Intent(ListLinksRssItem.this, MainActivity.class);
                startActivity(intentCategory);
            }
        });
        // receive data from category activity
        Bundle bundle = getIntent().getExtras();
        mPayer = bundle.getInt(Values.paper);
        mCategory = bundle.getInt(Values.category);
        mKey = bundle.getInt(Values.key);
        if(bundle ==null){
            Log.d(TAG, "KHONG NHAN DC");
        }
        getSupportActionBar().setTitle(Values.PAYERS[mPayer] + " - "
                + Values.CATEGORIES[mPayer][mCategory]);
        List<RssItem> items = Values.MAP.get(mKey);
        Log.d(TAG, items+"");
        mListRssItemAdapter = new ListRssItemAdapter(this, items);
        mListView.setAdapter(mListRssItemAdapter);
        mListView.setOnItemClickListener(this);
        mNetworkUtils = new NetworkUtils(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        if (mNetworkUtils.isConnectingToInternet()) {
            Intent intent = new Intent(ListLinksRssItem.this, ReadRssActivity.class);
            intent.putExtra(Values.key, mKey);
            intent.putExtra(Values.position, pos);
            startActivity(intent);
        }
        }

}
