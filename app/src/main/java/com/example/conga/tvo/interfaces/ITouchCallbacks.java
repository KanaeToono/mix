package com.example.conga.tvo.interfaces;

import android.widget.ListView;

/**
 * Created by ConGa on 6/04/2016.
 */
public interface ITouchCallbacks {
    void FullSwipeListView(int position);
    void HalfSwipeListView(int position);
    void OnClickListView(int position);
    void LoadDataForScroll(int count);
    void onDismiss(ListView listView, int[] reverseSortedPositions);
}
