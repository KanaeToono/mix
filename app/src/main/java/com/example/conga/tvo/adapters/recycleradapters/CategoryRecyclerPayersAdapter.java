package com.example.conga.tvo.adapters.recycleradapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.viewholders.CategoryViewHolder;
import com.example.conga.tvo.controllers.OnItemClickListener;

/**
 * Created by ConGa on 16/04/2016.
 */
public class CategoryRecyclerPayersAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private static String TAG = CategoryRecyclerPayersAdapter.class.getSimpleName();
    private String[] mListPayers;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int[] imageBackground;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    public CategoryRecyclerPayersAdapter(Context context, int[] imageBackground, String[] dataListPayers, OnItemClickListener.OnItemClickCallback onItemClickCallback){
        this.mListPayers = dataListPayers;
        this.imageBackground =imageBackground;
        this.mContext = context;
        this.onItemClickCallback =onItemClickCallback;
    }


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customcategoryactivity, parent, false);
        CategoryViewHolder myViewHolder = new CategoryViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.textViewTitle.setText(mListPayers[position]);
        holder.mFrameLayout.setBackgroundResource(imageBackground[position]);
        holder.mView.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));

    }

    @Override
    public int getItemCount() {
        return mListPayers.length;
    }
}
