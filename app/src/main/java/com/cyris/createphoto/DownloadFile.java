package com.cyris.createphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class DownloadFile {


    private static ArrayList<String> imageSourceList = new ArrayList<>();

    static public String DownloadFile1(final Context context) throws IOException {
        if (Environment.getExternalStorageDirectory() != null) {

            File destination = new File(Environment.getExternalStorageDirectory() + "/Meme Creator");
            if (!destination.exists()) {
                destination.mkdir();
            }


            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
           /* if(!currentFileName.exists()) {


                FileInputStream stream = new FileInputStream(source);
                FileOutputStream outputStream = new FileOutputStream(currentFileName);
                // InputStreamReader reader = new InputStreamReader(stream);
                FileChannel input = null;
                FileChannel output = null;

                input = stream.getChannel();
                output = outputStream.getChannel();
                output.transferFrom(input, 0, input.size());
                // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destination)));
                MediaScannerConnection.scanFile(context, new String[]{currentFileName.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {

                    }

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Toast.makeText(context, "File Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i("download", "iamworking2");

                input.close();
                output.close();
            }
            else
            {
                Toast.makeText(context,"File Already Exist", Toast.LENGTH_SHORT).show();
            }*/
            return currentFileName.toString();

        } else {

            File destination = new File(Environment.getDataDirectory() + "/Meme Creator");
            if (!destination.exists()) {
                destination.mkdir();
            }

            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
           /* if (!currentFileName.exists()) {
                FileInputStream stream = new FileInputStream(source);
                FileOutputStream outputStream = new FileOutputStream(currentFileName);
                // InputStreamReader reader = new InputStreamReader(stream);
                FileChannel input = null;
                FileChannel output = null;

                input = stream.getChannel();
                output = outputStream.getChannel();
                output.transferFrom(input, 0, input.size());
                // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destination)));
                MediaScannerConnection.scanFile(context, new String[]{currentFileName.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {

                    }

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Toast.makeText(context, "File Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i("download", "iamworking2");

                input.close();
                output.close();
            } else {
                Toast.makeText(context, "File Already Exist", Toast.LENGTH_SHORT).show();
            }

        }*/
            return currentFileName.toString();
        }
    }





    static public String DownloadFile2(String source, final Context context) throws IOException {
        if (Environment.getExternalStorageDirectory() != null) {

            File destination = new File(Environment.getExternalStorageDirectory() + "/Meme Creator");
            if (!destination.exists()) {
                destination.mkdir();
            }


            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
            if(!currentFileName.exists()) {


                FileInputStream stream = new FileInputStream(source);
                FileOutputStream outputStream = new FileOutputStream(currentFileName);
                // InputStreamReader reader = new InputStreamReader(stream);
                FileChannel input = null;
                FileChannel output = null;

                input = stream.getChannel();
                output = outputStream.getChannel();
                output.transferFrom(input, 0, input.size());
                // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destination)));
                MediaScannerConnection.scanFile(context, new String[]{currentFileName.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {

                    }

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Toast.makeText(context, "File Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i("download", "iamworking2");

                input.close();
                output.close();
            }
            else
            {
                Toast.makeText(context,"File Already Exist", Toast.LENGTH_SHORT).show();
            }
            return currentFileName.toString();

        } else {

            File destination = new File(Environment.getDataDirectory() + "/Meme Creator");
            if (!destination.exists()) {
                destination.mkdir();
            }

            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
           /* if (!currentFileName.exists()) {
                FileInputStream stream = new FileInputStream(source);
                FileOutputStream outputStream = new FileOutputStream(currentFileName);
                // InputStreamReader reader = new InputStreamReader(stream);
                FileChannel input = null;
                FileChannel output = null;

                input = stream.getChannel();
                output = outputStream.getChannel();
                output.transferFrom(input, 0, input.size());
                // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(destination)));
                MediaScannerConnection.scanFile(context, new String[]{currentFileName.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {

                    }

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Toast.makeText(context, "File Saved", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i("download", "iamworking2");

                input.close();
                output.close();
            } else {
                Toast.makeText(context, "File Already Exist", Toast.LENGTH_SHORT).show();
            }

        }*/
            return currentFileName.toString();
        }
    }



    static public void DownloadFile3(Bitmap bitmap, final Context context) throws IOException {
        if (Environment.getExternalStorageDirectory() != null) {

            File destination = new File(Environment.getExternalStorageDirectory() + "/Meme Creator");
            if (!destination.exists()) {
                destination.mkdir();
            }


            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
            FileOutputStream fos = new FileOutputStream(currentFileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            MediaStore.Images.Media.insertImage(context.getContentResolver(),currentFileName.toString(),"","");
            fos.close();
        } else {
            File destination = new File(Environment.getDataDirectory() + "/Meme Creator");
            if (!destination.exists()) {
                destination.mkdir();
            }


            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
            FileOutputStream fos = new FileOutputStream(currentFileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            MediaStore.Images.Media.insertImage(context.getContentResolver(),currentFileName.toString(),"","");
            fos.close();
        }
    }


    static public String DownloadFile4(Bitmap bitmap, final Context context) throws IOException {
        if (Environment.getExternalStorageDirectory() != null) {

            File destination = new File(Environment.getExternalStorageDirectory() + "/Meme Creator"+"/SharedMemes");
            if (!destination.exists()) {
                destination.mkdir();
            }


            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
            FileOutputStream fos = new FileOutputStream(currentFileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
           /* MediaScannerConnection.scanFile(context, new String[]{currentFileName.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                  //  Toast.makeText(context, "File Saved", Toast.LENGTH_SHORT).show();
                }
            }); */
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),currentFileName.toString(),"","");
            fos.close();
            return path;
        } else {
            File destination = new File(Environment.getDataDirectory() + "/Meme Creator"+"/SharedMemes");
            if (!destination.exists()) {
                destination.mkdir();
            }


            File currentFileName = new File(destination, System.currentTimeMillis() + ".jpeg");
            FileOutputStream fos = new FileOutputStream(currentFileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
           /* MediaScannerConnection.scanFile(context, new String[]{currentFileName.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Toast.makeText(context, "File Saved", Toast.LENGTH_SHORT).show();
                }
            }); */
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),"/Meme Shared"+currentFileName.toString(),"","");
            fos.close();

            return path;
        }
    }

    public static ArrayList<String> getImageSourceList()
    {

        if (Environment.getExternalStorageDirectory() != null) {
            imageSourceList.clear();
            File imageFile = new File(Environment.getExternalStorageDirectory(), "Meme Creator");
            if (imageFile.isDirectory()) {
                if (imageFile.isDirectory()) {
                    File[] allData = imageFile.listFiles();
                    if (allData.length > 0) {

                        for (int i = 0; i < allData.length; i++) {
                            if (allData[i].getAbsolutePath().endsWith(".jpeg"))
                                imageSourceList.add(allData[i].getAbsolutePath());

                        }
                    }
                }
            }
        } else {
            imageSourceList.clear();
            File imageFile = new File(Environment.getDataDirectory(), "Meme Creator");
            if (imageFile.isDirectory()) {
                if (imageFile.isDirectory()) {
                    File[] allData = imageFile.listFiles();
                    if (allData.length > 0) {
                        for (int i = 0; i < allData.length; i++) {
                            if (allData[i].getAbsolutePath().endsWith(".jpeg"))
                                imageSourceList.add(allData[i].getAbsolutePath());
                        }
                    }
                }
            }
        }

        return imageSourceList;
    }




}
