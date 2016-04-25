package com.example.conga.tvo.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.ListRssItemAdapter;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.databases.RssItemHelper;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.utils.NetworkUtils;
import com.example.conga.tvo.utils.Tools;

import org.apache.http.conn.ConnectTimeoutException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import services.UpdateNewFeedService;

/**
 * Created by ConGa on 23/04/2016.
 */
public class TinTucKhoaTinFragment extends Fragment {
    private static String TAG = TinTucKhoaTinFragment.class.getSimpleName();
    private ListRssItemAdapter mListRssItemAdapter;
    private RecyclerView mRecyclerView;
    private NetworkUtils mNetworkUtils;
    private RssItemHelper mRssItemHelper;
    private ArrayList<RssItem> mArrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tintuckhoatinfragment, container, false);

        // recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_list_links);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRssItemHelper = new RssItemHelper(getActivity());
        try {
            mRssItemHelper.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        mNetworkUtils = new NetworkUtils(getActivity());
//kiem tra xem co du lieu hay khong
        if(mRssItemHelper.getRssItemCount() ==0 && mNetworkUtils.isConnectingToInternet()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ReadXmlPullParse readXML = new ReadXmlPullParse();
                    readXML.execute("http://it.ued.udn.vn/component/content/category/39?format=feed&type=rss");


                }
            });
        }
        else
        {
            mListRssItemAdapter = new ListRssItemAdapter(getActivity(),mRssItemHelper.getAllItemsRssKhoaTin(),onItemClickCallback );

            mListRssItemAdapter.notifyDataSetChanged();

            mRecyclerView.setAdapter(mListRssItemAdapter);
            getActivity().startService(new Intent(getActivity(), UpdateNewFeedService.class));

        }
        mArrayList = new ArrayList<RssItem>();
        mArrayList= mRssItemHelper.getAllItemsRssKhoaTin();
        return view;
    }
    public class ReadXmlPullParse extends AsyncTask<String, Integer, List<RssItem>> {
        static final String KEY_ITEM = "item";
        static final String KEY_TITLE = "title";
        static final String KEY_LINK = "link";
        static final String KEY_PUB_DATE = "pubDate";
        static final String KEY_DESCRIPTION ="description";
        @Override
        protected List<RssItem> doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
            Tools tools = new Tools();
            //  mRssItemDatabase = new RssItemDatabase(getContext());
            RssItem curItem = new RssItem();
            String curText = "";
            List<RssItem> rssItems;
            rssItems = new ArrayList<RssItem>();
            BufferedReader buffered = null;

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                buffered = new BufferedReader(new InputStreamReader(inputStream));
                xpp.setInput(buffered);
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = xpp.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (tagname.equalsIgnoreCase(KEY_ITEM)) {
                                curItem = new RssItem();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            curText = xpp.getText();

                            break;
                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase(KEY_ITEM)) {
                                //curItem.setImageUrl(getImage(curItem.getDescription()));
                                rssItems.add(curItem);
                            } else if (tagname.equalsIgnoreCase(KEY_TITLE)) {
                                curItem.setTitle(curText);
                                Log.i("", curText);
                            } else if (tagname.equalsIgnoreCase(KEY_LINK)) {
                                curItem.setLink(curText);
                            } else if (tagname.equals(KEY_PUB_DATE)) {
                                curItem.setPubDate(tools.parseXmlPubDate(curText));
                            }
                            else if(tagname.equals(KEY_DESCRIPTION)){
                                curItem.setDescription(curText);
                            }
                            break;
                        default:
                            break;
                    }

                    eventType = xpp.next();
                }

            } catch (MalformedURLException | SocketTimeoutException | ConnectTimeoutException e) {
                e.printStackTrace();
                Log.d(TAG, "time out read rss ");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }


            return rssItems;
        }

        @Override
        public void onPostExecute(List<RssItem> rssItems) {
            super.onPostExecute(rssItems);
            for (int i=0 ; i<rssItems.size() ; i++){
                mRssItemHelper.addNewItemRssKhoaTin(rssItems.get(i));
            }
            Log.d(TAG, mRssItemHelper.getAllItemsRssKhoaTin()+"");
            mListRssItemAdapter = new ListRssItemAdapter(getActivity(),mRssItemHelper.getAllItemsRssKhoaTin(), onItemClickCallback);
            mListRssItemAdapter.notifyDataSetChanged();
            mRecyclerView.setAdapter(mListRssItemAdapter);
        }
//
    }
    ///
    private String getImage(String description) {
        int a = description.indexOf("src=");
        int start = description.indexOf("\"", a);
        int end = description.indexOf("\"", start + 1);
        Log.d("a", a + "");
        Log.d("start", start + "");
        Log.d("end", end + "");
        String image = "";
        Log.d("Image", image);
        return image;
    }

/////

    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            if (mNetworkUtils.isConnectingToInternet()) {
                Bundle args = new Bundle();
                args.putString("link",mArrayList.get(position).getLink() );

                Fragment fragment = new ReadRssFragmnet();
                fragment.setArguments(args);
                FragmentManager frgManager = getActivity().getSupportFragmentManager();
                frgManager.beginTransaction().replace(R.id.main_content, fragment)
                        .addToBackStack(null).commit();
            }
            else {
                //delete  task
                Toast.makeText(getActivity(), R.string.message, Toast.LENGTH_SHORT).show();
            }
        }
    };

}
