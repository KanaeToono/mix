package com.example.conga.tvo.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.CategoryAdapter;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.RssParser;
import com.example.conga.tvo.models.Values;
import com.example.conga.tvo.utils.NetworkUtils;

import java.util.List;


/**
 * Created by ConGa on 12/04/2016.
 */
public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static String TAG = CategoryActivity.class.getSimpleName();
    private ListView mListView;
    private Toolbar mToolbar;
    private int mPayer;
    private CategoryAdapter mCategoryAdapter;
    private NetworkUtils mNetworkUtils;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoryactivity);
        mNetworkUtils = new NetworkUtils(this);
        mListView = (ListView) findViewById(R.id.listpayers);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMainActivity = new Intent(CategoryActivity.this, MainActivity.class);
                startActivity(intentMainActivity);
            }
        });
        getSupportActionBar().setTitle(Values.PAYERS[mPayer]);
        Intent intent = getIntent();
        mPayer = intent.getExtras().getInt(Values.paper);
        if(intent==null){
            Log.d(TAG, "khong nhan dc");
        }
        mToolbar.setTitle(Values.PAYERS[mPayer]);
        Log.d(TAG, Values.PAYERS[mPayer]);
        if(mPayer==0) {
            try {
                mCategoryAdapter = new CategoryAdapter(this, Values.BACKGROUND_PAYERS
                        , Values.CATEGORIES[mPayer]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
     else  if(mPayer ==1){
            mCategoryAdapter = new CategoryAdapter(this, Values.BACKGROUND_24H_PAYER
                    , Values.CATEGORIES[mPayer]);

        }
            else  if(mPayer ==2){
            mCategoryAdapter = new CategoryAdapter(this, Values.BACKGROUNDS_DANTRI
                    , Values.CATEGORIES[mPayer]);

        }
            else  if(mPayer ==3){
            mCategoryAdapter = new CategoryAdapter(this, Values.BACKGROUNDS_VIETNAMNET
                    , Values.CATEGORIES[mPayer]);

        }

        mListView.setAdapter(mCategoryAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        if (mNetworkUtils.isConnectingToInternet()) {
            int key = 1000 * mPayer + pos;
            if (Values.MAP.containsKey(key)) {
                Intent intent = new Intent(this, ListLinksRssItem.class);
                intent.putExtra(Values.paper, mPayer);
                intent.putExtra(Values.category, pos);
                intent.putExtra(Values.key, key);
                startActivity(intent);
            } else {
                mProgressDialog = ProgressDialog.show(this, "", "Loading...");
                new RssTask().execute(pos);
            }
        }
    }

    private class RssTask extends AsyncTask<Integer, Void, Void> {
        private int position;
        private int key;
        private  Context mContext;
        private String mMessage;

//        private RssTask(Context mContext , String message ) {
//           this.mContext =mContext;
//           this.mMessage =message;
//
//        }

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
            Intent intent = new Intent(CategoryActivity.this,
                    ListLinksRssItem.class);
            intent.putExtra(Values.paper, mPayer);
            intent.putExtra(Values.category, position);
            intent.putExtra(Values.key, key);
            startActivity(intent);
            super.onPostExecute(result);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
//            Intent intent6 = new Intent(CategoryActivity.this, MainActivity.class);
//            startActivity(intent6);
            return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

}
