package com.example.conga.tvo.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.conga.tvo.R;
import com.example.conga.tvo.adapters.recycleradapters.CustomAdapter;
import com.example.conga.tvo.models.Values;

/**
 * Created by ConGa on 17/04/2016.
 */
public class ArticlesCategoryFragmnet extends Fragment implements AdapterView.OnItemClickListener {
    private static String TAG = ArticlesCategoryFragmnet.class.getSimpleName();
    private GridView mGridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " ON CREATE ARTICLESCATEGORYFRAGMENT");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gridviewcategoryarticles, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        mGridView.setAdapter(new CustomAdapter(getActivity(), Values.PAYERS, Values.ICON_PAYER));
        mGridView.setOnItemClickListener(this);
        return view;
    }
// Xử lí sự kiện khi nhấn các items trên gridview
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Bundle args = new Bundle();
        args.putInt(Values.paper, pos);
        // bắt đầu CategoryPayersFragment chứa các danh mục của mỗi trang : trang chủ , ....
        Fragment fragment = new CategoryPayersFragment();
        fragment.setArguments(args);
        FragmentManager frgManager = getActivity().getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.frame_container, fragment)
                .addToBackStack(null).commit();
    }
    // Xử lí khi nhấn back press

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, " start");

    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
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
