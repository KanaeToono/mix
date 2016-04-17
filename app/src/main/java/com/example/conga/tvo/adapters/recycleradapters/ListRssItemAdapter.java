package com.example.conga.tvo.adapters.recycleradapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conga.tvo.R;
import com.example.conga.tvo.fragments.ListLinksRssItemFragmnet;
import com.example.conga.tvo.adapters.viewholders.ListLinksViewHolder;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.models.RssItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ListRssItemAdapter  extends RecyclerView.Adapter<ListLinksViewHolder> {
    private static String TAG = ListLinksRssItemFragmnet.class.getSimpleName();
    private List<RssItem> mArrayListRssItems;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;



    public ListRssItemAdapter(Context mContext, List<RssItem> mArrayListRssItems,OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.mArrayListRssItems = mArrayListRssItems;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.onItemClickCallback=onItemClickCallback;
    }


    @Override
    public ListLinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlistlinksrsslayout, parent, false);
        ListLinksViewHolder myViewHolder = new ListLinksViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ListLinksViewHolder holder, int position) {
        holder.textViewTitleRss.setText(mArrayListRssItems.get(position).getTitle());
        holder.textViewPubDate.setText(mArrayListRssItems.get(position).getPubDate());

        ImageLoader.getInstance().displayImage(mArrayListRssItems.get(position).getImage(), holder.imageViewImageTitle, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
               holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });
        holder.mView.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
    }


    @Override
    public int getItemCount() {
        return mArrayListRssItems.size();
    }


}
