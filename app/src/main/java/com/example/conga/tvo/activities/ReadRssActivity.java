package com.example.conga.tvo.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.Values;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by ConGa on 12/04/2016.
 */
public class ReadRssActivity extends AppCompatActivity {
    private static String TAG = ReadRssActivity.class.getSimpleName();
    private WebView webView;
    private ProgressDialog mProgressDialog;
    private String link;
    public static Activity mActivity;
    public static Context mContext;
    final Activity context= this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readrssitemlayout);
     //  this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        mProgressDialog = ProgressDialog.show(this, "" ,"loading");
        int key = getIntent().getExtras().getInt(Values.key);
        int position = getIntent().getExtras().getInt(Values.position);
        RssItem item = Values.MAP.get(key).get(position);
        setTitle(item.getTitle());
    //    link = item.getLink();
        link =item.getLinkTag();
        Toast.makeText(getApplicationContext(), ""+link, Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJsoupWebPage().execute();
            }
        });

//        mContext = getApplicationContext();
//        mActivity = ReadRssActivity.this;
//        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.setScrollbarFadingEnabled(false);
//        webView.setScrollBarStyle(webView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setInitialScale(1);
//        webView.getSettings().setLightTouchEnabled(true);
//        webView.getSettings().setSupportMultipleWindows(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.setWebViewClient(new MyWebViewClient());
//        webView.setWebChromeClient(new MyWebChromeClient());
//
////        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
////            webView.getSettings().setDisplayZoomControls(false);
////        }
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            WebView.setWebContentsDebuggingEnabled(true);
////        }
//
//
//        webView.post(new Runnable() {
//            @Override
//            public void run() {
//                webView.loadUrl(link);
//            }
//        });
//
//    }
//
//    class MyTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            webView.loadUrl(link);
//            return null;
//        }
//
//    }
//
//    class MyWebViewClient extends WebViewClient {
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//            mProgressDialog = ProgressDialog.show(ReadRssActivity.this, "" ,"loading");
//            super.onPageStarted(view, url, favicon);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                mProgressDialog.dismiss();
//            }
//            Log.d("finish", url);
//            super.onPageFinished(view, url);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//             view.loadUrl(url);
//            return true;
//        }
//
//
//    }
//
//    private class MyWebChromeClient extends WebChromeClient {
//
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);
//          //  mProgressDialog = ProgressDialog.show(ReadRssActivity.this, "" ,"loading");
////            mActivity.setProgress(newProgress * 1000);
////
//            if (newProgress == 100 && mProgressDialog.isShowing()) {
//                mProgressDialog.dismiss();
//            }
//        }
//
//        @Override
//        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//
//            Log.d("finish", url);
//               return super.onJsAlert(view, url, message, result);
//
//        }
 }

    public class ReadJsoupWebPage  extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document  document = Jsoup.connect(link).get();
                Toast.makeText(getApplicationContext(), "" +document.title(), Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getApplicationContext(), ""+document.title(), Toast.LENGTH_SHORT).show();
                Log.d("hahha", document.title());
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
