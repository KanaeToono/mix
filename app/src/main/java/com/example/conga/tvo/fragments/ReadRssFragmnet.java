package com.example.conga.tvo.fragments;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.conga.tvo.R;
import com.example.conga.tvo.constants.ConstantRssItem;
import com.example.conga.tvo.utils.NetworkUtils;

public class ReadRssFragmnet extends Fragment {
    private static String TAG = ReadRssFragmnet.class.getSimpleName();
    private WebView webView;
    private ProgressDialog mProgressDialog;
    private View mCustomView;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    protected FrameLayout mFullscreenContainer;
    private String mLink;
    private NetworkUtils mNetworkUtils;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on ctreate Read webpage");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.readrssfavoritesitemlayout, container, false);
        // new network
        mNetworkUtils = new NetworkUtils(getActivity());
        // nhan data
        mLink = getArguments().getString("link");
        //check data null or not
        try {
            if (mLink == null) {
                Log.d(TAG, "mLink is being null");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

     // lay id wwebview
        webView = (WebView) view.findViewById(R.id.webView);
        if(mNetworkUtils.isConnectingToInternet()) {
            setUpWebViewDefaults(webView);

            //load webview
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(mLink);
                }
            });
        }
        else {
            final AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
            b.setTitle(R.string.error);
            b.setMessage(R.string.network_unvalable);
            b.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        dialog.dismiss();
                        webView.getSettings().setJavaScriptEnabled(true);
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               webView.loadUrl(mLink);
                           }
                       });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            b.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();
        }
        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }

        });
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onShowCustomView(View view,
                                             WebChromeClient.CustomViewCallback callback) {
                    // if a view already exists then immediately terminate the new one
                    if (mCustomView != null) {
                        onHideCustomView();
                        return;
                    }

                    // 1. Stash the current state
                    mCustomView = view;
                    mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                    mOriginalOrientation = getActivity().getRequestedOrientation();

                    // 2. Stash the custom view callback
                    mCustomViewCallback = callback;

                    // 3. Add the custom view to the view hierarchy
                    FrameLayout decor = (FrameLayout) getActivity().getWindow().getDecorView();
                    decor.addView(mCustomView, new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));


                    // 4. Change the state of the window
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                                    View.SYSTEM_UI_FLAG_IMMERSIVE);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }

                @Override
                public void onHideCustomView() {
                    // 1. Remove the custom view
                    FrameLayout decor = (FrameLayout) getActivity().getWindow().getDecorView();
                    decor.removeView(mCustomView);
                    mCustomView = null;

                    // 2. Restore the state to it's original form
                    getActivity().getWindow().getDecorView()
                            .setSystemUiVisibility(mOriginalSystemUiVisibility);
                    getActivity().setRequestedOrientation(mOriginalOrientation);

                    // 3. Call the custom view callback
                    mCustomViewCallback.onCustomViewHidden();
                    mCustomViewCallback = null;

                }


                //
//permission request API in WebChromeClient:
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    Log.d(TAG, "onPermissionRequest");
                    getActivity().runOnUiThread(new Runnable() {
                        @TargetApi(Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            if (request.getOrigin().toString().equals(mLink)) {
                                request.grant(request.getResources());
                            } else {
                                request.deny();
                            }
                        }
                    });
                }

            });


        return view;
    }

    private void setUpWebViewDefaults(WebView webView) {
        webView.getSettings().setUserAgentString(ConstantRssItem.USER_AGENT_WEB);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        WebSettings settings = webView.getSettings();
        webView.getSettings().setSupportZoom(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);
        settings.setSupportMultipleWindows(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.setWebViewClient(new WebViewClient());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            mProgressDialog = ProgressDialog.show(getActivity(), "", "loading");
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            Log.d("finish", url);
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
    }
    // press back
    private void webViewGoBack(){
        webView.goBack();
    }
}

