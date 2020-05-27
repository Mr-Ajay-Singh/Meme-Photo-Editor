package com.cyris.createphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ShareFile extends AsyncTask<String,Void,Bitmap> {

    Bitmap bitmap;
    Context context;
    public ShareFile(Context ctx)
    {
        context = ctx;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            URLConnection conn = url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        try {

            String fileAddress = DownloadFile.DownloadFile4(bitmap,context);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(fileAddress));
           // shareIntent.putExtra(Intent.EXTRA_STREAM, MediaStore.Images.Media.insertImage(context.getContentResolver(),fileAddress,"",""));

            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Share image"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
