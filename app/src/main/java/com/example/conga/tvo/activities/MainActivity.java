package com.example.conga.tvo.activities;

import android.content.res.Configuration;
import android.os.Bundle;
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

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.NavDrawerListAdapter;
import com.example.conga.tvo.fragments.ArticlesCategoryFragmnet;
import com.example.conga.tvo.fragments.FavoritesNewsFragment;
import com.example.conga.tvo.fragments.OfllineNewsListFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mDataList.add(new NavDrawerItem(getString(R.string.favorites), R.drawable.star_on));
        mDataList.add(new NavDrawerItem(getString(R.string.offline), R.drawable.ic_file_download_black_24dp));
        mDataList.add(new NavDrawerItem(getString(R.string.settings), R.drawable.ic_settings_black_24dp));
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
            case 1:
                fragment = new FavoritesNewsFragment();
                args.putString(FavoritesNewsFragment.ITEM_NAME, mDataList.get(possition)
                        .getTitle());

                break;
            case 2:
                fragment = new OfllineNewsListFragment();
                args.putString(OfllineNewsListFragment.ITEM_NAME, mDataList.get(possition)
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        Log.d(TAG, "ON DESTROY MAIN ACTIVITY");
    }

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
//        Intent intent = new Intent(MainActivity.this, CategoryPayersActivity.class);
//        intent.putExtra(Values.paper, pos);
//        startActivity(intent);
//    }
}
