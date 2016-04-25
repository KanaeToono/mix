package com.example.conga.tvo.adapters.recycleradapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.viewholders.ListLinksViewHolder;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.databases.RssItemHelper;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.utils.NetworkUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ListRssItemAdapter extends RecyclerView.Adapter<ListLinksViewHolder> {
    private static String TAG = ListRssItemAdapter.class.getSimpleName();
    private List<RssItem> mArrayListRssItems;
    private LayoutInflater mLayoutInflater;
    //  private Context mContext;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    public SharedPreferences prefs;
    private RssItemHelper mRssItemDatabase;
    private Activity mActivity;
    private static final int HTTP_OK = 200;
    private ProgressDialog mProgressDialog;
    private NetworkUtils mNetworkUtils;
    private SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
    // constructor

    public ListRssItemAdapter(Activity mContext, List<RssItem> mArrayListRssItems,OnItemClickListener.OnItemClickCallback onItemClickCallback
    ) {
        this.mArrayListRssItems = mArrayListRssItems;
        this.mActivity = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.onItemClickCallback = onItemClickCallback;
        prefs = mContext.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        mNetworkUtils = new NetworkUtils(mContext);


    }


    @Override
    public ListLinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlayouttintucdaotaofragment, parent, false);
        ListLinksViewHolder myViewHolder = new ListLinksViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ListLinksViewHolder holder, final int position) {

        holder.textViewTitleRss.setText(mArrayListRssItems.get(position).getTitle());
        holder.textViewPubDate.setText(format.format(mArrayListRssItems.get(position).getPubDate()));

        if (mArrayListRssItems.get(position).getImageUrl() == null) {
            holder.imageViewImageTitle.setBackgroundResource(R.drawable.blue);
        } else {
            ImageLoader.getInstance().displayImage(mArrayListRssItems.
                            get(position).getImageUrl(), holder.imageViewImageTitle,
                    new ImageLoadingListener() {
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
        }
        holder.mView.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
    }


    @Override
    public int getItemCount() {

        if (mArrayListRssItems == null) {

           // Toast.makeText(mActivity, R.string.respond_server_empty, Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            return mArrayListRssItems.size();
        }
//        return mArrayListRssItems == null ? 0 : mArrayListRssItems.size();
    }


}
