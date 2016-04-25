package com.example.conga.tvo.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.conga.tvo.adapters.recycleradapters.NavDrawerListAdapter;
import com.example.conga.tvo.fragments.ArticlesCategoryFragmnet;

import com.example.conga.tvo.models.NavDrawerItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private GridView mGridView;
    private TextView mTextView;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private NavDrawerListAdapter mNavDrawerListAdapter;
    private Toolbar mToolbar;
    private ArrayList<NavDrawerItem> mDataList;
    private Boolean exit = false;
    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    private boolean isConnected = false;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //kiem tra network
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

        // bắt đầu  gridview chứa các trang báo : dân trí , vietnamnet , vnexpress, offline , yêu thích , vnexpress
//        mGridView = (GridView) findViewById(R.id.gridView);
//        mGridView.setAdapter(new CustomAdapter(this, Values.PAYERS, Values.ICON_PAYER));
//        mGridView.setOnItemClickListener(this);
//        mTextView = (TextView) findViewById(R.id.text);
//        mTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentOfflineListActivity = new Intent(MainActivity.this,  OfllineNewsListActivity.class);
//                startActivity(intentOfflineListActivity);
//            }
//        });

        mDataList = new ArrayList<NavDrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.lv_sliding_menu);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        // Add Drawer Item to dataList
        mDataList.add(new NavDrawerItem(getString(R.string.articles), R.drawable.articles));
        mDataList.add(new NavDrawerItem("mở rộng", R.drawable.tienich1));
        mDataList.add(new NavDrawerItem(getString(R.string.offline), R.drawable.ic_file_download_black_24dp));
        // mDataList.add(new NavDrawerItem(getString(R.string.settings), R.drawable.ic_settings_black_24dp));
//        mDataList.add(new NavDrawerItem(getString(R.string.title_nav_add_on), R.drawable.tienich1));
//        mDataList.add(new NavDrawerItem(getString(R.string.title_nav_about_app), R.drawable.about1));

        mNavDrawerListAdapter = new NavDrawerListAdapter(this,
                mDataList);

        mDrawerList.setAdapter(mNavDrawerListAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
                syncState();// creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
                syncState();// creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }


    }

    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new ArticlesCategoryFragmnet();
                args.putString(ArticlesCategoryFragmnet.ITEM_NAME, mDataList.get(possition)
                        .getTitle());

                break;

//            case 3:
//                fragment = new Setting();
//                args.putString(Setting.ITEM_NAME, mDataList.get(possition)
//                        .getTitle());
//                break;
//            case 4:
//                fragment = new ListViewTaskFragment();
//                args.putString(ListViewTaskFragment.ITEM_NAME, mDataList.get(possition)
//                        .getTitle());
//                break;
            default:
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.main_content, fragment)
                .commit();

        mDrawerList.setItemChecked(possition, true);
        setTitle(mDataList.get(possition).getTitle());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    // kiem tra network
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, final Intent intent) {

            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isConnectedWifi = networkInfoWifi != null && networkInfoWifi.isConnectedOrConnecting();
            NetworkInfo networkInfoMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            boolean isConnectedMobile = networkInfoMobile != null && networkInfoMobile.isConnectedOrConnecting();
            if (isConnectedWifi || isConnectedMobile) {
                Log.d("NET", "Connected");
               // Toast.makeText(context.getApplicationContext(), R.string.avalable_network, Toast.LENGTH_SHORT).show();
            } else {
                Log.d("NET", " not Connect ");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.error);
                builder.setMessage(R.string.network_unvalable);
                ;
                //  AlertDialog dialog = builder.create();
                builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(Settings.ACTION_SETTINGS));

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
                //dialog.show();
                // Toast.makeText(context.getApplicationContext(), R.string.network_unvalable, Toast.LENGTH_LONG).show();
            }
        }

    }

    //setTitle
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(menuItem)) {
            return true;
        }
        if (menuItem.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(mDrawerList);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
        //return false;
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " ON START MAIN ACTIVITY");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "ON RESUME MAIN ACTIVITY");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "ON RESTART MAIN ACTIVITY");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ON PAUSE MAIN ACTIVITY");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, " ON STOP MAIN ACTIVITY");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Log.d(TAG, "ON DESTROY MAIN ACTIVITY");
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        if (exit) {
            super.onBackPressed();
            return;
        }
        this.exit = true;
        Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                exit = false;
            }
        }, 2000);
    }

    //    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//        Intent intent = new Intent(MainActivity.this, CategoryPayersActivity.class);
//        intent.putExtra(Values.paper, pos);
//        startActivity(intent);
//    }
}
