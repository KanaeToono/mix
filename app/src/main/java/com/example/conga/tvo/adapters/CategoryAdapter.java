package com.example.conga.tvo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.conga.tvo.R;

/**
 * Created by ConGa on 12/04/2016.
 */
public class CategoryAdapter extends BaseAdapter {
    private static String TAG = CategoryAdapter.class.getSimpleName();
    private String[] mListPayers;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int[] imageBackground;


    public CategoryAdapter(Context context, int [] imageBackground , String[] dataListPayers) {
        this.mListPayers = dataListPayers;
        this.imageBackground =imageBackground;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mListPayers.length;
    }

    @Override
    public Object getItem(int pos) {
        return mListPayers[pos];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customcategoryactivity, null);
//            viewHolder.imageViewActionRowForward = (ImageView) convertView.findViewById(R.id.imageViewController);
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.title_article);
          viewHolder.mFrameLayout = (FrameLayout) convertView.findViewById(R.id.frame_item_rss_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewTitle.setText(mListPayers[position]);
       viewHolder.mFrameLayout.setBackgroundResource(imageBackground[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView imageViewActionRowForward;
        TextView textViewTitle;
        FrameLayout mFrameLayout;
    }
}
