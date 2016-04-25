package com.example.conga.tvo.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.conga.tvo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ConGa on 17/04/2016.
 */
public class ArticlesCategoryFragmnet extends Fragment {
    public static final String ITEM_NAME = "item";
    private static String TAG = ArticlesCategoryFragmnet.class.getSimpleName();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Boolean exit =false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " ON CREATE ARTICLESCATEGORYFRAGMENT");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.arrticlesfrag, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }
// Thiet lap su kien viewpayer
private void setupViewPager(ViewPager viewPager) {
    ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
    String daoTao =  getString(R.string.daoTao);
    String khoaTin= getString(R.string.khoatin);
    adapter.addFrag(new TinTucDaoTaoFragment(), daoTao);
    adapter.addFrag(new TinTucKhoaTinFragment(), khoaTin);
    viewPager.setAdapter(adapter);
}

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }


        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragment_added", true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, " start");

    }

    @Override
    public void onResume() {
        super.onResume();
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    getActivity().finish();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "PAUSE ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "STOP ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "on destroy view");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DESTROY ");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "ATTACH");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "ON DETACH ");
    }
}
