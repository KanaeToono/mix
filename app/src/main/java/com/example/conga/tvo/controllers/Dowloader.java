package com.example.conga.tvo.controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ConGa on 11/04/2016.
 */
public class Dowloader {
    private static String TAG = Dowloader.class.getSimpleName();
    static  final int POST_PROGRESS=1;
    public  static  void downloadFromUrl (String url ,FileOutputStream fos){
        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
            byte data[] = new byte[1024];
            int  count ;
            while ((count=bufferedInputStream.read(data))!=-1){
                bufferedOutputStream.write(data, 0, count);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
