package com.example.conga.tvo.htmltextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Base64;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class URLImageParser implements ImageGetter {
	Context context;
	TextView container;

	public URLImageParser(TextView container, Context context) {
		this.context = context;
		this.container = container;
	}

	public Drawable getDrawable(String source) {
		if (source.matches("data:image.*base64.*")) {
			String base_64_source = source.replaceAll("data:image.*base64", "");
			byte[] data = Base64.decode(base_64_source, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			Drawable image = new BitmapDrawable(context.getResources(), bitmap);
			image.setBounds(0, 0, 0 + image.getIntrinsicWidth(),
					0 + image.getIntrinsicHeight());
			return image;
		} else {
			URLDrawable urlDrawable = new URLDrawable();
			ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(
					urlDrawable);
			asyncTask.execute(source);
			return urlDrawable; // return reference to URLDrawable where We will
								// change with actual image from the src tag
		}
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		URLDrawable urlDrawable;

		public ImageGetterAsyncTask(URLDrawable d) {
			this.urlDrawable = d;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			String source = params[0];
			return fetchDrawable(source);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			if (result != null) {
				int width = (int) (result.getIntrinsicWidth());
				int height = (int) (result.getIntrinsicHeight());
				float scale = context.getResources().getDisplayMetrics().density;
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
				URLImageParser.this.container.invalidate(); // redraw the image
															// by invalidating
															// the container
				URLImageParser.this.container
						.setHeight((URLImageParser.this.container.getHeight() + height));
			}
		}

		public Drawable fetchDrawable(String urlString) {
			try {
				InputStream is = (InputStream) new URL(urlString).getContent();
				if (is.available() > 0) {
					Drawable drawable = Drawable.createFromStream(is, "src");
					int width = (int) (drawable.getIntrinsicWidth());
					int height = (int) (drawable.getIntrinsicHeight());
					float scale = context.getResources().getDisplayMetrics().density;
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
	}
}
