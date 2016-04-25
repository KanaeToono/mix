package com.example.conga.tvo.adapters.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.conga.tvo.R;

/**
 * Created by ConGa on 17/04/2016.
 */
public class ListLinksViewHolder extends RecyclerView.ViewHolder {
    private static String TAG = CategoryViewHolder.class.getSimpleName();
    public ImageView imageViewImageTitle;
    public TextView textViewTitleRss;
    public TextView textViewPubDate;
    public Context mContext;
    public final View mView;
    public ProgressBar progressBar ;
  //  public LinearLayout mLinearLayout;
    public ImageView imageViewChoose;
   // public  ImageView imageViewLike;
    public ListLinksViewHolder(View view) {
        super(view);
        mView = view;
       imageViewImageTitle = (ImageView) view.findViewById(R.id.imageView_title_icon);
       textViewPubDate = (TextView) view.findViewById(R.id.textViewPubdate);
       textViewTitleRss = (TextView) view.findViewById(R.id.textView_title_news);
       progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        imageViewChoose = (ImageView) view.findViewById(R.id.imageViewChoose);

    }
}
