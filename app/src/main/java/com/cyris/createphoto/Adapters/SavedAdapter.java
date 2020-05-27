package com.cyris.createphoto.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cyris.createphoto.DownloadFile;
import com.cyris.createphoto.MainActivity;
import com.cyris.createphoto.R;
import com.cyris.createphoto.ShareFile;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.SavedViewHolder> {
    Context context;
    ArrayList<String> savedList;
    Dialog dialog;
    CardView noSavedFile;
    ImageView mainImage,shareImage,editImage,deleteImage;
    private String path;
    public SavedAdapter(Context ctx,CardView cardView)
    {
        context =ctx;
        savedList = DownloadFile.getImageSourceList();
        noSavedFile = cardView;
        createDialog();

    }

    private void createDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.saved_dialog);
        mainImage = dialog.findViewById(R.id.imageInBookmarkDialog);
        shareImage = dialog.findViewById(R.id.shareInBookmarkDialog);
        deleteImage = dialog.findViewById(R.id.deleteInBookmarkDialog);
        editImage = dialog.findViewById(R.id.editInBookmarkDialog);

    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.memes_holder,parent,false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        holder.LoadImage();
        holder.OnImageClick(position);
    }

    @Override
    public int getItemCount() {
        if(savedList.size()<=0)
            noSavedFile.setVisibility(View.VISIBLE);
        return savedList.size();
    }

    public class SavedViewHolder extends RecyclerView.ViewHolder {
        ImageView savedImage;
        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);
            savedImage = itemView.findViewById(R.id.imageInMemes);
        }

        public void LoadImage()
        {
            File file = new File(savedList.get(getLayoutPosition()));
            Picasso.get().load(file).centerCrop().resize(250,200).into(savedImage);
        }

        public void OnImageClick(final int pos)
        {
            shareImage.setImageResource(R.drawable.whatsapp);
            deleteImage.setImageResource(R.drawable.delete);
            editImage.setImageResource(R.drawable.edit);
            savedImage.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    final File file = new File(savedList.get(getLayoutPosition()));
                    Picasso.get().load(file).into(mainImage);
                    dialog.show();

                    shareImage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction())
                            {
                                case MotionEvent.ACTION_DOWN: shareImage.setImageResource(R.drawable.whatsapp_dark);
                                    break;
                                case MotionEvent.ACTION_UP: shareImage.setImageResource(R.drawable.whatsapp);
                                    Snackbar.make(shareImage.getRootView(),"Please Wait",Snackbar.LENGTH_SHORT).show();
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("image/*");
                                    shareIntent.setPackage("com.whatsapp");
                                    shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(savedList.get(pos)));
                                    context.startActivity(shareIntent);
                                    break;
                            }

                            return true;
                        }
                    });

                     deleteImage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction())
                            {
                                case MotionEvent.ACTION_DOWN: deleteImage.setImageResource(R.drawable.delete_dark);
                                    break;
                                case MotionEvent.ACTION_UP: deleteImage.setImageResource(R.drawable.delete);
                                    Toast.makeText(context,"File Deleted",Toast.LENGTH_SHORT).show();
                                    File file = new File(savedList.get(getLayoutPosition()));
                                    file.delete();
                                    savedList.clear();
                                    savedList = DownloadFile.getImageSourceList();
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    break;
                            }

                            return true;
                        }
                    });

                     editImage.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            switch (motionEvent.getAction())
                            {
                                case MotionEvent.ACTION_DOWN:  editImage.setImageResource(R.drawable.edit_dark);

                                    break;
                                case MotionEvent.ACTION_UP:
                                    editImage.setImageResource(R.drawable.edit_dark);
                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.putExtra(context.getString(R.string.intent_meme_home),savedList.get(pos));
                                    intent.putExtra(context.getString(R.string.check_saved_intent),true);
                                    context.startActivity(intent);
                                    dialog.dismiss();

                                    break;
                            }

                            return true;
                        }
                    });

                }
            });


        }
    }
}
