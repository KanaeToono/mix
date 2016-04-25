package com.example.conga.tvo.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.conga.tvo.R;

/**
 * Created by ConGa on 16/04/2016.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = CategoryViewHolder.class.getSimpleName();
    public TextView textViewTitle;
    public final View mView;
    public FrameLayout mFrameLayout;
    public Context mContext;
    public CategoryViewHolder(View view) {

        super(view);
        mView = view;
        textViewTitle = (TextView) view.findViewById(R.id.title_article);
        mFrameLayout= (FrameLayout) view.findViewById(R.id.frame_item_rss_layout);

    }

}
