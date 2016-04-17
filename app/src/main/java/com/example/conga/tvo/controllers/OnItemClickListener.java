package com.example.conga.tvo.controllers;

import android.view.View;

/**
 * Created by ConGa on 17/04/2016.
 */
public class OnItemClickListener implements View.OnClickListener {
    private static String TAG = OnItemClickListener.class.getSimpleName();
    private int position;
    private OnItemClickCallback onItemClickCallback;

    public OnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position);
    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
