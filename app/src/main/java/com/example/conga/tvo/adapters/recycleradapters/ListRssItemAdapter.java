package com.example.conga.tvo.adapters.recycleradapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.viewholders.ListLinksViewHolder;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.databases.RssItemHelper;
import com.example.conga.tvo.models.ContentRss;
import com.example.conga.tvo.models.RssItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    // constructor

    public ListRssItemAdapter(Activity mContext, List<RssItem> mArrayListRssItems,
                              OnItemClickListener.OnItemClickCallback onItemClickCallback
    ) {
        this.mArrayListRssItems = mArrayListRssItems;
        this.mActivity = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.onItemClickCallback = onItemClickCallback;
        prefs = mContext.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);


    }


    @Override
    public ListLinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlistlinksrsslayout, parent, false);
        ListLinksViewHolder myViewHolder = new ListLinksViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ListLinksViewHolder holder, final int position) {

        holder.textViewTitleRss.setText(mArrayListRssItems.get(position).getTitle());
        holder.textViewPubDate.setText(mArrayListRssItems.get(position).getPubDate());
        holder.imageViewDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "123456", Toast.LENGTH_SHORT).show();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String linkTag = mArrayListRssItems.get(position).getLinkTag();
                        new SaveContentRssAsyncTask().execute();
                    }
                });
//                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialog) {
//                        SaveContentRssAsyncTask.cancel(true);
//                    }
//                });

            }

            class SaveContentRssAsyncTask extends AsyncTask<Void, Void, Void> {
                String respondString = null;
                static final String USER_AGENT_BROWER = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36";


                @Override
                protected Void doInBackground(Void... params) {

                    Connection.Response response= null;
                    try {
                        response = Jsoup.connect(mArrayListRssItems.get(position).getLinkTag()).timeout(100*10000)
                                        .method(Connection.Method.POST)
                                        .userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
                                        .ignoreHttpErrors(true).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Map<String , String> cookies =response.cookies();
                    Document document = null;
                    try {
                        document = Jsoup.connect(mArrayListRssItems.get(position).getLinkTag()).timeout(100*100000).
                                userAgent("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30").
                                ignoreHttpErrors(true).method(Connection.Method.POST).cookies(cookies).
                                get();
                      Elements elements = document.select("div [class= fck_detail width_common]");
                       // Elements elements = document.select("html");

                        respondString = elements.text();

                        Log.d("hahah", respondString);
                        // lay ve cai title cua bai bao
                        String title = document.title();
                        mRssItemDatabase = new RssItemHelper(mActivity);
                        try {
                            mRssItemDatabase.open();

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        ContentRss contentRss = new ContentRss(title, respondString, "");
                        mRssItemDatabase.addNewItemContent(contentRss);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Elements elements = document.select("div [class= fck_detail width_common]");

                   // ContentRss contentRss= new ContentRss(title, result,"" );
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);


                }
            }

        });
        //
        int key = prefs.getInt("STATUS_KEY", 0);
        int pos = prefs.getInt("POSITION", 0);
        if (key == 1 && pos == position) {
            holder.mLinearLayout.setBackgroundResource(R.color.colorWhite);
        }
        //
        if (mArrayListRssItems.get(position).getImage() == null) {
            holder.imageViewImageTitle.setBackgroundResource(R.drawable.blue);
        } else {
            ImageLoader.getInstance().displayImage(mArrayListRssItems.
                            get(position).getImage(), holder.imageViewImageTitle,
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
        return mArrayListRssItems.size();
    }


}
