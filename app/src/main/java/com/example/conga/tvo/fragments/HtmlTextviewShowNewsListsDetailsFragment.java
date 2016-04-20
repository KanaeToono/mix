package com.example.conga.tvo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.models.ContentRss;

/**
 * Created by ConGa on 19/04/2016.
 */
public class HtmlTextviewShowNewsListsDetailsFragment extends Fragment {
    private static String TAG = HtmlTextviewShowNewsListsDetailsFragment.class.getSimpleName();
    private TextView text;
    private TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.textviewhtmldetaillistofflinenewsfrg, container, false);
        Bundle bundle = getArguments();
        final ContentRss contentRss = (ContentRss) bundle
                .getParcelable("Item");
        if (contentRss == null) {
            Log.d(TAG, " content rss is being null");
        }
        Log.d(TAG, contentRss.getTitle());
        Log.d(TAG, contentRss.getContent());
        //PARSE BY HTML
        text = (TextView) view.findViewById(R.id.html_text);
        title = (TextView) view.findViewById(R.id.textViewTitleNews);

        text.setText(contentRss.getContent());
        title.setText(contentRss.getTitle());

        return view;
    }
}
