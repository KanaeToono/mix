package com.example.conga.tvo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.ListRssItemAdapter;
import com.example.conga.tvo.controllers.OnItemClickListener;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.Values;
import com.example.conga.tvo.utils.NetworkUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ListLinksRssItemFragmnet extends Fragment {
    private static String TAG = ListLinksRssItemFragmnet.class.getSimpleName();
    private Toolbar mToolbar;
    private int mPayer;
    private int mCategory;
    private int mKey;
    private ListRssItemAdapter mListRssItemAdapter;
    private NetworkUtils mNetworkUtils;
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create listlinks");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailslistlinksrsslayout, container, false);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())

                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCategory = new Intent(getActivity(), CategoryPayersFragment.class);
                startActivity(intentCategory);
            }
        });
        // receive data from category activity
        //   Bundle bundle = getIntent().getExtras();
        mPayer = getArguments().getInt(Values.paper);
        mCategory = getArguments().getInt(Values.category);
        mKey = getArguments().getInt(Values.key);

        activity.getSupportActionBar().setTitle(Values.PAYERS[mPayer] + " - "
                + Values.CATEGORIES[mPayer][mCategory]);
        List<RssItem> items = Values.MAP.get(mKey);
        Log.d(TAG, items + "");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_links);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mListRssItemAdapter = new ListRssItemAdapter(getActivity(), items, onItemClickCallback);
        mRecyclerView.setAdapter(mListRssItemAdapter);
        mNetworkUtils = new NetworkUtils(getActivity());
        return view;
    }

    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            if (mNetworkUtils.isConnectingToInternet()) {

                Bundle args = new Bundle();
                args.putInt(Values.key, mKey);
                args.putInt(Values.position, position);
                Fragment fragment = new ReadRssFragmnet();
                fragment.setArguments(args);
                FragmentManager frgManager = getActivity().getSupportFragmentManager();
                frgManager.beginTransaction().replace(R.id.frame_container, fragment)
                        .addToBackStack(null).commit();
            }
        }

    };


}
