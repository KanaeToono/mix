package com.example.conga.tvo.adapters.recycleradapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.databases.RssItemHelper;
import com.example.conga.tvo.models.ContentRss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by ConGa on 4/04/2016.
 */
public class ListNewsAdapter extends BaseAdapter  {
    private static String TAG = ListNewsAdapter.class.getSimpleName();
    private ArrayList<ContentRss> mArrayListNews;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private RssItemHelper mRssItemHelper;



    public ListNewsAdapter(Context mContext, ArrayList<ContentRss> mArrayListTasks) {
        this.mArrayListNews = mArrayListTasks;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
       // return mArrayListNews.size();
        return mArrayListNews == null ? 0 : mArrayListNews.size();
    }

    @Override
    public Object getItem(int pos) {
        return mArrayListNews.get(pos);
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }


    // class ViewHolder
    class ViewHolder {
        TextView textViewTitleNews;
        TextView textViewPubDate;
    }
//getView
    @Override
    public View getView(final int pos, View viewcontainer, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (viewcontainer == null) {
            viewcontainer = mLayoutInflater.inflate(R.layout.customlisviewnewsofflinefrg, null);
            viewHolder = new ViewHolder();

            viewHolder.textViewTitleNews = (TextView) viewcontainer.findViewById(R.id.textViewTitleNews);
            viewHolder.textViewPubDate= (TextView) viewcontainer.findViewById(R.id.textViewPubDate);

            //viewHolder.imageViewDeleteTask = (ImageView) viewcontainer.findViewById(R.id.imageView_delete_item_task);
            viewcontainer.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) viewcontainer.getTag();
        }

        viewHolder.textViewTitleNews.setText(mArrayListNews.get(pos).getTitle());
       viewHolder.textViewPubDate.setText(mArrayListNews.get(pos).getImage());

        return viewcontainer;
    }
}