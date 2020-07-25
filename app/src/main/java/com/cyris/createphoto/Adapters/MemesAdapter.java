package com.cyris.createphoto.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.createphoto.CheckConnection;
import com.cyris.createphoto.DownloadImageFile;
import com.cyris.createphoto.FirstActivity;
import com.cyris.createphoto.MainActivity;
import com.cyris.createphoto.R;
import com.cyris.createphoto.ShareFile;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MemesAdapter extends RecyclerView.Adapter<MemesAdapter.MemesViewHolder> {

    private InterstitialAd interstitialAd;
    ArrayList<String> memesList;
    Context context;
    Dialog dialog;
    ImageView imageInDialog,shareImage,editImage,downloadImage;

    public MemesAdapter(Context ctx,ArrayList<String> meme)
    {
        if(ctx!=null)
        {
            context = ctx;
            memesList = meme;
            dialogOperation();
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId("");
            interstitialAd.loadAd(new AdRequest.Builder().build());
        }


    }
    private void dialogOperation() {
        dialog = new Dialog(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);

        dialog.setContentView(R.layout.memes_dialog);
        imageInDialog = dialog.findViewById(R.id.imageInMemesDialog);
        shareImage = dialog.findViewById(R.id.shareInMemesDialog);
        editImage = dialog.findViewById(R.id.editInMemesDialog);
        downloadImage = dialog.findViewById(R.id.downloadInMemesDialog);

    }

    @NonNull
    @Override
    public MemesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.memes_holder,parent,false);
        return new MemesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemesViewHolder holder, int position) {
        holder.MemeImageShow();
        holder.OnMemeClick();
    }

    @Override
    public int getItemCount() {
        return memesList.size();
    }

    class MemesViewHolder extends RecyclerView.ViewHolder {
        ImageView memeView;
        public MemesViewHolder(@NonNull View itemView) {
            super(itemView);
            memeView = itemView.findViewById(R.id.imageInMemes);
        }

        public void MemeImageShow()
        {
            Picasso.get().load(memesList.get(getLayoutPosition())).centerCrop().resize(250,200).into(memeView);
        }

        public void OnMemeClick()
        {

            downloadImage.setImageResource(R.drawable.download);
            editImage.setImageResource(R.drawable.edit);
            shareImage.setImageResource(R.drawable.share1);
            memeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Picasso.get().load(memesList.get(getLayoutPosition())).into(imageInDialog);
                    dialog.show();

                    editImage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction())
                            {
                                case MotionEvent.ACTION_DOWN : editImage.setImageResource(R.drawable.edit_dark);
                                                                break;
                                case MotionEvent.ACTION_UP :  editPermission(getLayoutPosition());
                                    editImage.setImageResource(R.drawable.edit);
                                    break;
                            }

                            return true;
                        }
                    });

                  shareImage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction())
                            {
                                case MotionEvent.ACTION_DOWN : shareImage.setImageResource(R.drawable.share_dark);
                                                                break;
                                case MotionEvent.ACTION_UP :  sharePermission(getLayoutPosition());
                                    shareImage.setImageResource(R.drawable.share1);
                                    break;
                            }

                            return true;
                        }
                    });

                  downloadImage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction())
                            {
                                case MotionEvent.ACTION_DOWN : downloadImage.setImageResource(R.drawable.download_dark);
                                                                break;
                                case MotionEvent.ACTION_UP :
                                    downloadPermission(getLayoutPosition());
                                    downloadImage.setImageResource(R.drawable.download);
                                    if (interstitialAd.isLoaded()) {
                                        interstitialAd.show();
                                    }
                                        break;
                            }

                            return true;
                        }
                    });

                

                }
            });
        }
    }

    private void sharePermission(int layoutPosition) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    13);

        } else {
            if(CheckConnection.checkInternet(context))
            {
                Snackbar.make(downloadImage.getRootView(),"Please wait..",Snackbar.LENGTH_LONG).show();
                ShareFile shareFile = new ShareFile(context);
                shareFile.execute(memesList.get(layoutPosition));
            }
            else
            {
                Snackbar.make(downloadImage.getRootView(),"Internet Not Connected",Snackbar.LENGTH_SHORT).show();
            }

        }

    }

    private void editPermission(int layoutPosition) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    12);

        } else {
            if(CheckConnection.checkInternet(context))
            {
                Snackbar.make(downloadImage.getRootView(),"Please wait..",Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(context.getString(R.string.intent_meme),memesList.get(layoutPosition));
                context.startActivity(intent);
            }
            else
            {
                Snackbar.make(downloadImage.getRootView(),"Internet Not Connected",Snackbar.LENGTH_SHORT).show();
            }

        }

    }

    public void downloadPermission(int pos)
    {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    11);

        } else {
            if(CheckConnection.checkInternet(context))
            {
                Snackbar.make(downloadImage.getRootView(),"Download Started...",Snackbar.LENGTH_LONG).show();
                DownloadImageFile downloadImageFile = new DownloadImageFile(context);
                downloadImageFile.execute(memesList.get(pos));
            }
            else
            {
                Snackbar.make(downloadImage.getRootView(),"Internet Not Connected",Snackbar.LENGTH_SHORT).show();
            }

        }
    }
}
