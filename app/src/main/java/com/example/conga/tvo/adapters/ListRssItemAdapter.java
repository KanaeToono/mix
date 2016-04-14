package com.example.conga.tvo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.activities.ListLinksRssItem;
import com.example.conga.tvo.models.RssItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ListRssItemAdapter  extends BaseAdapter {
    private static String TAG = ListLinksRssItem.class.getSimpleName();
    private List<RssItem> mArrayListRssItems;
    private LayoutInflater mLayoutInflater;
    private Context mContext;



    public ListRssItemAdapter(Context mContext, List<RssItem> mArrayListRssItems) {
        this.mArrayListRssItems = mArrayListRssItems;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mArrayListRssItems.size();
    }

    @Override
    public Object getItem(int pos) {
        return mArrayListRssItems.get(pos);
    }



    @Override
    public long getItemId(int pos) {
        return pos;
    }


    // class ViewHolder
    class ViewHolder {
        ImageView imageViewImageTitle;
        TextView textViewTitleRss;
        TextView textViewPubDate;

    }

    //getView
    @Override
    public View getView(final int pos, View viewcontainer, ViewGroup viewGroup) {
        int checkAlarmTask = 0;
        final ViewHolder viewHolder;
        if (viewcontainer == null) {
            viewcontainer = mLayoutInflater.inflate(R.layout.customlistlinksrsslayout, null);
            viewHolder = new ViewHolder();
            viewHolder.imageViewImageTitle = (ImageView) viewcontainer.findViewById(R.id.imageView_title_icon);
           viewHolder.textViewPubDate = (TextView) viewcontainer.findViewById(R.id.textViewPubdate);
            viewHolder.textViewTitleRss = (TextView) viewcontainer.findViewById(R.id.textView_title_news);

            viewcontainer.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) viewcontainer.getTag();
        }

        viewHolder.textViewTitleRss.setText(mArrayListRssItems.get(pos).getTitle());
        viewHolder.textViewPubDate.setText(mArrayListRssItems.get(pos).getPubDate());
        final ProgressBar progressBar = (ProgressBar) viewcontainer.findViewById(R.id.progressBar);
        ImageLoader.getInstance().displayImage(mArrayListRssItems.get(pos).getImage(), viewHolder.imageViewImageTitle, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });
        return viewcontainer;
    }
// handle turn on alarm


}
