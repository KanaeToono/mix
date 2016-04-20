package com.example.conga.tvo.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.conga.tvo.R;
import com.example.conga.tvo.htmltextview.HtmlTextView;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.variables.Values;

import static android.os.Build.VERSION_CODES;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ReadRssActivity extends AppCompatActivity {
    private static String TAG = ReadRssActivity.class.getSimpleName();
    private WebView webView;
    private ProgressDialog mProgressDialog;
    private String link;
    private String linkTag;
    public static Activity mActivity;
    public static Context mContext;
    private Button button;
    HtmlTextView text;
    String result;
    private View mCustomView;
    private int mOriginalSystemUiVisibility;
    private int mOriginalOrientation;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    protected FrameLayout mFullscreenContainer;
    private Handler mHandler;
    private  int mKey;
    private int mPosition;
    private Bundle webViewBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on ctreate Read webpage");
        setContentView(R.layout.readrssitemlayout);
       // nhan data
        mKey = getIntent().getExtras().getInt(Values.key);
        mPosition = getIntent().getExtras().getInt(Values.position);
        RssItem item = Values.MAP.get(mKey).get(mPosition);
        //PARSE BY HTML
        text = (HtmlTextView) findViewById(R.id.html_text);
        setTitle(item.getTitle());
        link = item.getLink();
        webView = (WebView) findViewById(R.id.webView);
        setUpWebViewDefaults(webView);
        // restore lai history
        if(savedInstanceState!=null){
            webView.restoreState(savedInstanceState);
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(link);
                }
            });
        }

//
        webView.setWebViewClient(new MyWebViewClient());

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
                mOriginalSystemUiVisibility =getWindow().getDecorView().getSystemUiVisibility();
                mOriginalOrientation = getRequestedOrientation();

                // 2. Stash the custom view callback
                mCustomViewCallback = callback;

                // 3. Add the custom view to the view hierarchy
                FrameLayout decor = (FrameLayout)getWindow().getDecorView();
                decor.addView(mCustomView, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));


                // 4. Change the state of the window
               getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_IMMERSIVE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @Override
            public void onHideCustomView() {
                // 1. Remove the custom view
                FrameLayout decor = (FrameLayout)getWindow().getDecorView();
                decor.removeView(mCustomView);
                mCustomView = null;

                // 2. Restore the state to it's original form
                getWindow().getDecorView()
                        .setSystemUiVisibility(mOriginalSystemUiVisibility);
                setRequestedOrientation(mOriginalOrientation);

                // 3. Call the custom view callback
                mCustomViewCallback.onCustomViewHidden();
                mCustomViewCallback = null;

            }
//permission request API in WebChromeClient:
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d(TAG, "onPermissionRequest");
                runOnUiThread(new Runnable() {
                    @TargetApi(VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        if (request.getOrigin().toString().equals(link)) {
                            request.grant(request.getResources());
                        } else {
                            request.deny();
                        }
                    }
                });
            }

        });



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    private void setUpWebViewDefaults(WebView webView) {
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        WebSettings settings = webView.getSettings();
        webView.getSettings().setSupportZoom(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        // Enable Javascript
        webView.getSettings().setSupportZoom(true);
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);
                settings.setSupportMultipleWindows(true);

        if (Build.VERSION.SDK_INT > VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.setWebViewClient(new WebViewClient());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie( true);
    }

        class MyWebViewClient extends WebViewClient {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

           //     mProgressDialog = ProgressDialog.show(ReadRssActivity.this, "", "loading...");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                }
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

        //


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "START READRSSACTIVITY");

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "PAUSE READRSSACTIVITY");

        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "STOP READRSSACTIVITY");
        webView.stopLoading();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "RESTART READRSSACTIVITY");
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (webView.inCustomView()) {
//                webView.onHideCustomView();
//                //  mWebView.goBack();
//                //mWebView.goBack();
//                return true;
//            }
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DESTROY READRSSACTIVITY");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!webView.canGoBack()) {

        }

        webView.goBack();

    }
    // HANDLE PHẦN BACK LẠI , TRỞ VỀ TRANG BÁO TRƯỚC ĐÓ , KHÔNG PHẢI LÀ THOÁT LUÔN
    // HANDLE LẠI PHẦN VIDEO , CÓ VẤN ĐỀ Ở ĐÂY : NHẤN BACK , XOAY POTRAIT LÀ TRỞ VỀ TRẠNG THÁI BAN ĐẦU ,
    // HANDLE PHẦN CHECK MẠNG Ở ĐÂY , ĐANG ĐỌC MÀ MẤT MẠNG

}
