package com.example.conga.tvo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.conga.tvo.R;
import com.example.conga.tvo.fragments.ArticlesCategoryFragmnet;


public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bắt đầu fragment activity chứa các trang báo : dân trí , vietnamnet , vnexpress, offline , yêu thích , vnexpress
        Fragment myf = new ArticlesCategoryFragmnet();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, myf).addToBackStack(null);
        transaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
}
