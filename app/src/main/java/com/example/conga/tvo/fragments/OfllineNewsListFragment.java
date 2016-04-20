package com.example.conga.tvo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conga.tvo.R;

/**
 * Created by ConGa on 19/04/2016.
 */
public class OfllineNewsListFragment extends Fragment {
    public static final String ITEM_NAME = "item";
    private static String TAG = OfllineNewsListFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainlistofflinenewactivitylayout, container, false);
        Fragment myf = new OfflineNewsListDetailFragmnet();

        FragmentTransaction transaction =  getActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content_frg, myf);
        transaction.commit();
        return view;
    }
}
