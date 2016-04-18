package com.example.conga.tvo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.CustomAdapter;
import com.example.conga.tvo.variables.Values;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static String TAG = MainActivity.class.getSimpleName();
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bắt đầu  gridview chứa các trang báo : dân trí , vietnamnet , vnexpress, offline , yêu thích , vnexpress
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(new CustomAdapter(this, Values.PAYERS, Values.ICON_PAYER));
        mGridView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Intent intent = new Intent(MainActivity.this, CategoryPayersActivity.class);
        intent.putExtra(Values.paper, pos);
        startActivity(intent);
    }
}
