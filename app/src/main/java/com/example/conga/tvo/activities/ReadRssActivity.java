package com.example.conga.tvo.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.conga.tvo.R;
import com.example.conga.tvo.htmltextview.HtmlTextView;
import com.example.conga.tvo.models.RssItem;
import com.example.conga.tvo.models.Values;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

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
    final Activity context = this;
    private Button button;
    HtmlTextView text;
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readrssitemlayout);
        int key = getIntent().getExtras().getInt(Values.key);
        int position = getIntent().getExtras().getInt(Values.position);
        RssItem item = Values.MAP.get(key).get(position);
        text = (HtmlTextView) findViewById(R.id.html_text);
        // text.setRemoveFromHtmlSpace(true);

        setTitle(item.getTitle());
        //  mFloatingActionButton = (FloatingActionButton) findViewById(R.id.overview_floating_action_button);
        // button = (Button) findViewById(R.id.btn_ok);
        link = item.getLink();
//        linkTag =item.getLinkTag();
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new SaveContentRssAsyncTask().execute();
//            }
//        });
        webView = (WebView) findViewById(R.id.webView);
        setUpWebViewDefaults(webView);
//        mContext = getApplicationContext();
//        mActivity = ReadRssActivity.this;

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.setScrollbarFadingEnabled(false);
//        webView.setScrollBarStyle(webView.SCROLLBARS_OUTSIDE_OVERLAY);
//        webView.setInitialScale(1);
//        webView.getSettings().setLightTouchEnabled(true);
//        webView.getSettings().setSupportMultipleWindows(true);
//     //   webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//      //  webView.setWebViewClient(new MyWebViewClient());
//        webView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.4; Nexus 5 Build/_BuildID_) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/30.0.0.0 Mobile Safari/537.36");
//
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
//            webView.getSettings().setDisplayZoomControls(false);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(link);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
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

    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
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

                mProgressDialog = ProgressDialog.show(ReadRssActivity.this, "", "loading");
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

            private static final String APP_SCHEME = "example-app:";

//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        if (url.startsWith(APP_SCHEME)) {
//            String urlData=null;
//            try {
//                urlData = URLDecoder.decode(url.substring(APP_SCHEME.length()), "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            respondToData(urlData);
//            return true;
//        }
//        return false;
//    }


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
//        public boolean onJsAlert(WebView view, String url, String mes"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623sage, JsResult result) {
//
//            Log.d("finish", url);
//               return super.onJsAlert(view, url, message, result);
//
//        }
        }

        //
        private class SaveContentRssAsyncTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (linkTag.contains("vnexpress.net")) {
                        Document document = Jsoup.connect(linkTag).
                                userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36").
                                get();
                        Elements elements = document.select("div [class= fck_detail width_common]");
                        result = elements.toString();
                    }
                    if (linkTag.contains("www.24h.com")) {
                        Document document = Jsoup.connect(linkTag).
                                userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36").get();
                        Elements elements = document.select("div.text-conent");
                        result = elements.toString();
                    }
                    if (linkTag.contains("dantri.com.vn")) {
                        Document document = Jsoup.connect(linkTag).
                                userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36").get();
                        Elements elements = document.select("div.VCSortableInPreviewMode");
                        result = elements.toString();
                    }
                    if (linkTag.contains("vietnamnet.vn")) {

                        Document document = Jsoup.connect(linkTag).
                                userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36").get();
                        Elements elements = document.select("div.ArticleDetail");
                        result = elements.toString();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                // text.setHtmlFromString(result, new com.example.conga.tvo.htmltextview.HtmlTextView.RemoteImageGetter(null));
                text.setHtmlFromString(result, new HtmlTextView.RemoteImageGetter());
                Toast.makeText(getApplicationContext(), "" + linkTag, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "" + text, Toast.LENGTH_SHORT).show();
                Log.d("error", text + "");
            }
        }
    }
