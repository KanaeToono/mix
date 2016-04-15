package com.example.conga.tvo.htmltextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Base64;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
/**
 * Created by Iris Louis on 17/09/2015.
 */

public class HtmlRemoteImageGetter implements ImageGetter {
    Context c;
    TextView container;
    URI baseUri;

    /**
     * Construct the URLImageParser which will execute AsyncTask and refresh the container
     */
    public HtmlRemoteImageGetter(TextView t, Context c, String baseUrl) {
        this.c = c;
        this.container = t;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
    }

//    public Drawable getDrawable(String source) {
//        UrlDrawable urlDrawable = new UrlDrawable();
//
//        // get the actual source
//        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);
//
//        asyncTask.execute(source);
//
//        // return reference to URLDrawable which will asynchronously load the image specified in the src tag
//        return urlDrawable;
//    }

    public Drawable getDrawable(String source) {
        if (source.matches("data:image.*base64.*")) {
            String base_64_source = source.replaceAll("data:image.*base64", "");
            byte[] data = Base64.decode(base_64_source, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Drawable image = new BitmapDrawable(c.getResources(), bitmap);
            image.setBounds(0, 0, 0 + image.getIntrinsicWidth(),
                    0 + image.getIntrinsicHeight());
            return image;
        } else {
            UrlDrawable urlDrawable = new UrlDrawable();
            ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(
                    urlDrawable);
            asyncTask.execute(source);
            return urlDrawable; // return reference to URLDrawable where We will
            // change with actual image from the src tag
        }
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        UrlDrawable urlDrawable;
        String source;

        public ImageGetterAsyncTask(UrlDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
//            if (result == null) {
//                Log.w(HtmlTextView.TAG, "Drawable result is null! (source: " + source + ")");
//                return;
//            }
//            // set the correct bound according to the result from HTTP call
//            urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 + result.getIntrinsicHeight());
//
//            // change the reference of the current drawable to the result from the HTTP call
//            urlDrawable.drawable = result;
//
//            // redraw the image by invalidating the container
//            HtmlRemoteImageGetter.this.container.invalidate();
            if (result != null) {
                int width = (int) (result.getIntrinsicWidth());
                int height = (int) (result.getIntrinsicHeight());
                float scale = c.getResources().getDisplayMetrics().density;
                if (width * scale < container.getWidth()) {
                    width = (int) (result.getIntrinsicWidth() * scale);
                    height = (int) (result.getIntrinsicHeight() * scale);
                }

                urlDrawable.setBounds(0, 0, 0 + width, 0 + height); // set the
                // correct
                // bound
                // according
                // to the
                // result
                // from HTTP
                // call
                urlDrawable.drawable = result; // change the reference of the
                // current drawable to the
                // result from the HTTP call
                HtmlRemoteImageGetter.this.container.invalidate(); // redraw the image
                // by invalidating
                // the container
                HtmlRemoteImageGetter.this.container
                        .setHeight((HtmlRemoteImageGetter.this.container.getHeight() + height));
            }
        }

        /**
         * Get the Drawable from URL
         */
//        public Drawable fetchDrawable(String urlString) {
//            try {
//                InputStream is = fetch(urlString);
//                Drawable drawable = Drawable.createFromStream(is, "src");
//                drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 + drawable.getIntrinsicHeight());
//                return drawable;
//            } catch (Exception e) {
//                return null;
//            }
//        }


        public Drawable fetchDrawable(String urlString) {
            try {
                URL url;
                if (baseUri != null) {
                    url = baseUri.resolve(urlString).toURL();
                } else {
                    url = URI.create(urlString).toURL();
                }
                InputStream is = (InputStream) url.getContent();
                if (is.available() > 0) {
                    Drawable drawable = Drawable.createFromStream(is, "src");
                    int width = (int) (drawable.getIntrinsicWidth());
                    int height = (int) (drawable.getIntrinsicHeight());
                    float scale = c.getResources().getDisplayMetrics().density;
                    if (width * scale < container.getWidth()) {
                        width = (int) (drawable.getIntrinsicWidth() * scale);
                        height = (int) (drawable.getIntrinsicHeight() * scale);
                    }

                    drawable.setBounds(0, 0, 0 + width, 0 + height);
                    return drawable;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws IOException {
            URL url;
            if (baseUri != null) {
                url = baseUri.resolve(urlString).toURL();
            } else {
                url = URI.create(urlString).toURL();
            }

            return (InputStream) url.getContent();
        }
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
}
