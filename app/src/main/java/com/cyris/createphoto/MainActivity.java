package com.cyris.createphoto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cyris.createphoto.Adapters.ColorAdapter;
import com.cyris.createphoto.Adapters.EmojiAdapter;
import com.cyris.createphoto.Adapters.ItemAdapter;
import com.cyris.createphoto.Adapters.MemesAdapter;
import com.cyris.createphoto.Adapters.StickerAdapter;
import com.cyris.createphoto.ItemType.ItemType;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.AdMetadataListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;
import ja.burhanrashid52.photoeditor.ViewType;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ItemDetailInterface,
        ColorAdapter.colorSelection, EmojiAdapter.EmojiSelect, StickerAdapter.StickerSelected,
        AdapterView.OnItemSelectedListener {

    PhotoEditorView photoEditorView;
    private InterstitialAd interstitialAd;
    ImageView saveImageButton,crossImageButton,undoButton,redoButton,
            shareInSavedImageDialog,whatsappInSavedImageDialog,imageInSavedDialog,okImageInSavedImageDialog,
            howToImageInMain;
    private PhotoEditor mPhotoEditor;
    RecyclerView itemSelectionRecyclerView,textColorRecyclerview,brushColorRecyclerView,emojiRecyclerview,stickerRecyclerView;
    Dialog dialog,brushDialog,emojiDialog,stickerDialog,savedImageDialog,howToDialog;
    AlertDialog saveCheckDialog,discardCheckDialog;
    AlertDialog.Builder builder1,builder2;
    int textR=0,textG=0,textB=0;
    ImageView imageOkButton,imageCancelButton;
    EditText textEditext;
    SwitchCompat textSwitch;
    int finalTextColor,textBackgroudColor;
    SeekBar brushSizeSeekBar,brushOpacitySeekBar;
    Bitmap bitmap;
    String imageLink,imageLinkHome;
    boolean checkSavedIntent,isSaved;
    Spinner typefaceSpinner;
    Typeface typeface;
    ProgressBar progressBar;
    private boolean isworking =false;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initialize();
        FetchImage fetchImage = new FetchImage();
        mPhotoEditor = new PhotoEditor.Builder(this,photoEditorView).build();
        imageLink =getIntent().getStringExtra(getString(R.string.intent_meme));
        imageLinkHome = getIntent().getStringExtra(getString(R.string.intent_meme_home));
        checkSavedIntent = getIntent().getBooleanExtra(getString(R.string.check_saved_intent),false);
        progressBar = findViewById(R.id.progrssBarInMain);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("");
        interstitialAd.loadAd(new AdRequest.Builder().build());




        if(imageLink!=null) {
            fetchImage.execute(imageLink);
     /*   if (android.os.Build.VERSION.SDK_INT >= 21) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);


            }
        }*/

        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            if(checkSavedIntent)
            {
                File file = new File(imageLinkHome);
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                InputStream stream=null;
                try {
                    Log.i("workTest","Work");
                    stream = getContentResolver().openInputStream(Uri.parse(imageLinkHome));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if(stream!=null)
                {
                    bitmap = BitmapFactory.decodeStream(stream);

                }



              //  photoEditorView.getSource().setImageBitmap(bitmap);
            }
            Log.i("HeightWidth",String.valueOf(bitmap.getWidth()+" "+bitmap.getHeight()));
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight()+200,bitmap.getConfig());
            Canvas canvas = new Canvas(bitmap1);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap,0,200,null);
            photoEditorView.getSource().setImageBitmap(bitmap1);

        }
  //      Bitmap.Config conf = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap_object = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight()+100, conf);


        getSupportActionBar().hide();


        mPhotoEditor.setOnPhotoEditorListener(new OnPhotoEditorListener() {
            @Override
            public void onEditTextChangeListener(final View rootView, String text, final int colorCode) {
                typefaceSpinner.setVisibility(View.INVISIBLE);
                textOperation(text,finalTextColor,rootView,textBackgroudColor);
                rootView.setBackgroundColor(textBackgroudColor);
            }

            @Override
            public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

            }

            @Override
            public void onStartViewChangeListener(ViewType viewType) {

            }

            @Override
            public void onStopViewChangeListener(ViewType viewType) {

            }
        });

    }



    private void initialize() {
        photoEditorView = findViewById(R.id.photoEditorView);
        saveImageButton = findViewById(R.id.saveImageButton);
        undoButton = findViewById(R.id.undoButton);
        redoButton = findViewById(R.id.redoButton);
        crossImageButton = findViewById(R.id.crossImageButton);
        itemSelectionRecyclerView = findViewById(R.id.itemSelectionRecyclerVeiw);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        itemSelectionRecyclerView.setLayoutManager(manager);
        ItemAdapter itemAdapter = new ItemAdapter(this);
        itemSelectionRecyclerView.setAdapter(itemAdapter);
        itemSelectionRecyclerView.setHasFixedSize(true);
        dialog = new Dialog(this,R.style.Theme_AppCompat_Light_DialogWhenLarge);
        howToImageInMain = findViewById(R.id.howToImageButton);


        howToDialog = new Dialog(this);
        howToDialog.setContentView(R.layout.how_to_download_dialog);


        savedImageDialog = new Dialog(this);
        savedImageDialog.setContentView(R.layout.show_saved_image_dialog);
        savedImageDialog.setCancelable(false);
        shareInSavedImageDialog = savedImageDialog.findViewById(R.id.shareInSavedImageDialog);
        shareInSavedImageDialog.setImageResource(R.drawable.share1);
        whatsappInSavedImageDialog = savedImageDialog.findViewById(R.id.whatsappInSavedImageDialog);
        whatsappInSavedImageDialog.setImageResource(R.drawable.whatsapp);
        imageInSavedDialog = savedImageDialog.findViewById(R.id.imageInSavedDialog);
        okImageInSavedImageDialog = savedImageDialog.findViewById(R.id.okInSavedImageDialog);
        okImageInSavedImageDialog.setImageResource(R.drawable.ok);






       saveImageExent();



      //  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.text_detail_dialog);
        textColorRecyclerview = dialog.findViewById(R.id.selectColorRecyclerviewInText);
        textColorRecyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        textColorRecyclerview.setAdapter(new ColorAdapter(this,this,getResources().getString(R.string.text_color)));
        textSwitch = dialog.findViewById(R.id.switchTypeText);
        imageOkButton = dialog.findViewById(R.id.okInDialog);
        imageCancelButton = dialog.findViewById(R.id.cancelInDialog);


        brushDialog = new Dialog(this);
        brushDialog.setContentView(R.layout.brush_dialog_layout);
        brushColorRecyclerView = brushDialog.findViewById(R.id.selectColorRecyclerviewInBrush);
        brushDialog.getWindow().setGravity(Gravity.BOTTOM);
        brushDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        brushOpacitySeekBar =brushDialog.findViewById(R.id.brushOpacitySeekBar);
        brushSizeSeekBar = brushDialog.findViewById(R.id.brushSizeSeekBar);


        emojiDialog = new Dialog(this);
        emojiDialog.setContentView(R.layout.emoji_dialog);
        emojiRecyclerview = emojiDialog.findViewById(R.id.emojiRecyclerView);
        emojiDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        emojiRecyclerview.setLayoutManager(new GridLayoutManager(this,4));
        emojiRecyclerview.setAdapter(new EmojiAdapter(this,this));


        stickerDialog = new Dialog(this);
        stickerDialog.setContentView(R.layout.sticker_dialog);
        stickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        stickerDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        stickerRecyclerView = stickerDialog.findViewById(R.id.stickerRecyclerView);
        stickerRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        stickerRecyclerView.setAdapter(new StickerAdapter(this,this));


        saveDiscardOperation();
        undoRedoOperation();
        AddSpinnerData();

    }

    private void saveImageExent() {

        builder1 = new AlertDialog.Builder(this);
        builder1.create();
        builder1.setTitle("Do you want to save");
        builder1.setMessage("Once you save the image you cant edit it.");
        builder1.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveCheckDialog.dismiss();


            }
        });
        builder1.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            private String path;

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    mPhotoEditor.saveAsFile(DownloadFile.DownloadFile1(MainActivity.this), new PhotoEditor.OnSaveListener() {
                        @Override
                        public void onSuccess(@NonNull final String imagePath) {
                            File file = new File(imagePath);
                            try {
                                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            try {
                                path = MediaStore.Images.Media.insertImage(getContentResolver(),file.toString(),"","");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            imageInSavedDialog.setImageBitmap(bitmap);
                            Toast.makeText(MainActivity.this,"Image Saved successfully",Toast.LENGTH_SHORT).show();
                            shareInSavedImageDialog.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    switch (motionEvent.getAction())
                                    {
                                        case MotionEvent.ACTION_DOWN: shareInSavedImageDialog.setImageResource(R.drawable.share_dark);
                                            break;
                                        case MotionEvent.ACTION_UP:
                                            shareInSavedImageDialog.setImageResource(R.drawable.share1);
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            shareIntent.setType("image/jpeg");
                                            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                                            startActivity(Intent.createChooser(shareIntent, "Share image"));
                                            break;
                                    }
                                    return true;
                                }
                            });

                            whatsappInSavedImageDialog.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    switch (motionEvent.getAction())
                                    {
                                        case MotionEvent.ACTION_DOWN: whatsappInSavedImageDialog.setImageResource(R.drawable.whatsapp_dark);
                                            break;
                                        case MotionEvent.ACTION_UP: whatsappInSavedImageDialog.setImageResource(R.drawable.whatsapp);
                                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                            shareIntent.setType("image/jpg");
                                            shareIntent.setPackage("com.whatsapp");
                                            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath));
                                            startActivity(shareIntent);
                                            break;
                                    }
                                    return true;
                                }
                            });

                            okImageInSavedImageDialog.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    switch (motionEvent.getAction())
                                    {
                                        case MotionEvent.ACTION_DOWN: okImageInSavedImageDialog.setImageResource(R.drawable.ok_dark);
                                            break;
                                        case MotionEvent.ACTION_UP:
                                                savedImageDialog.dismiss();
                                                if(interstitialAd.isLoaded())
                                                {
                                                    interstitialAd.show();
                                                    savedImageDialog.dismiss();
                                                }
                                                else
                                                {
                                                    MainActivity.this.finish();
                                                }

                                                isworking=true;

                                            okImageInSavedImageDialog.setImageResource(R.drawable.ok);
                                            break;
                                    }
                                    return true;
                                }
                            });


                            isSaved = true;
                                savedImageDialog.show();


                        }

                        @Override
                        public void onFailure(@NonNull Exception exception) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        saveCheckDialog = builder1.create();




        builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Do you want to Discard");
        builder2.setMessage("Once you Discard you cant edit it more.");
        builder2.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                discardCheckDialog.dismiss();
            }
        });
        builder2.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });
        discardCheckDialog = builder2.create();
    }

    private void undoRedoOperation() {
        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoEditor.redo();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoEditor.undo();
            }
        });
    }

    private void saveDiscardOperation() {

        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCheckDialog.show();
                /*mPhotoEditor.saveAsFile(filePath, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        Log.e("PhotoEditor","Image Saved Successfully");
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("PhotoEditor","Failed to save Image");
                    }
                });*/
            }
        });
        crossImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discardCheckDialog.show();
            }
        });

        howToImageInMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howToDialog.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==22)
        {

                if(data!=null)
                { CropImage.activity(data.getData())
                                .start(this);
                }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            InputStream stream=null;

                try {
                    Log.i("workTest","Work");
                    stream = getContentResolver().openInputStream(result.getUri());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if(stream!=null)
                {
                    bitmap = BitmapFactory.decodeStream(stream);
                    mPhotoEditor.addImage(bitmap);
                }

        }

        if(requestCode ==33)
        {
            InputStream stream=null;
            Uri uri;

            Log.i("workTest2", "Work0");
            if(data!=null) {

                uri=data.getData();
                try {
                    Log.i("workTest2", "Work");
                    stream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (stream != null) {
                    bitmap = BitmapFactory.decodeStream(stream);
                    mPhotoEditor.addImage(bitmap);
                }
            }
        }
    }

    @Override
    public void ItemsInfo(final ItemAdapter.ItemValues values) {
        switch (values.type)
        {
            case BRUSH:  brushOperation();
                                break;

            case TEXT: finalTextColor = Color.rgb(255,255,255);
                        typefaceSpinner.setVisibility(View.VISIBLE);
                textOperation("",finalTextColor,null,Color.parseColor("#aa000000"));
                        break;

            case STICKER: stickerDialog.show();
                         break;

            case EMOJI: emojiDialog.show();
                                break;

            case IMAGE:  Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setData(
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,22);
                                break;
            case ERASER: mPhotoEditor.brushEraser();

        }

    }



    private void brushOperation() {

        mPhotoEditor.setBrushDrawingMode(true);
        brushDialog.show();
        brushColorRecyclerView.getRootView().setVisibility(View.VISIBLE);
        brushColorRecyclerView.setAdapter(new ColorAdapter(this,this,getResources().getString(R.string.paint_color)));
        brushColorRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        brushSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    mPhotoEditor.setBrushSize((float)i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brushOpacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    mPhotoEditor.setOpacity(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mPhotoEditor.setBrushColor(getResources().getColor(R.color.black));
    }


    private void textOperation(String text, final int color, final View  rootView, int backgrounColor) {
        dialog.show();

        textEditext= dialog.findViewById(R.id.textInDialog);
        textEditext.setTextColor(color);
        textEditext.setText(text);
        textEditext.setBackgroundColor(backgrounColor);

        final SeekBar redSeekbar = dialog.findViewById(R.id.redSeekBarInDialog);
        SeekBar greenSeekbar = dialog.findViewById(R.id.greenSeekBarInDialog);
        SeekBar blueSeekbar = dialog.findViewById(R.id.blueSeekBarInDialog);
       // seekBar.invalidate();



        redSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                redSeekbar.setOutlineAmbientShadowColor(Color.rgb(i,0,0));
                textR =i;
                if(!textSwitch.isChecked())
                {
                    textEditext.setTextColor(Color.rgb(textR,textG,textB));
                    finalTextColor=Color.rgb(textR,textG,textB);
                }
                if(textSwitch.isChecked())
                {
                    textEditext.setBackgroundColor(Color.rgb(textR,textG,textB));
                    textBackgroudColor =Color.rgb(textR,textG,textB);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textG =i;
                if(!textSwitch.isChecked())
                {
                    textEditext.setTextColor(Color.rgb(textR,textG,textB));
                    finalTextColor=Color.rgb(textR,textG,textB);
                }
                if(textSwitch.isChecked())
                {
                    textEditext.setBackgroundColor(Color.rgb(textR,textG,textB));
                    textBackgroudColor =Color.rgb(textR,textG,textB);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blueSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textB =i;
                if(!textSwitch.isChecked())
                {
                    textEditext.setTextColor(Color.rgb(textR,textG,textB));
                    finalTextColor=Color.rgb(textR,textG,textB);
                }
                if(textSwitch.isChecked())
                {
                    textEditext.setBackgroundColor(Color.rgb(textR,textG,textB));
                    textBackgroudColor =Color.rgb(textR,textG,textB);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        imageOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textEditext.getText().toString().equals(""))
                {
                    if(rootView==null)
                    {

                        if(typeface!=null)
                        {
                            mPhotoEditor.addText(typeface,textEditext.getText().toString(),finalTextColor);
                            typeface=null;
                        }
                        else
                        {
                            mPhotoEditor.addText(textEditext.getText().toString(),finalTextColor);
                        }

                        Snackbar.make(itemSelectionRecyclerView,"Press Long on Text To add Background Color",Snackbar.LENGTH_LONG).show();
                    }
                    else
                    {
                        mPhotoEditor.editText(rootView,textEditext.getText().toString(),finalTextColor);
                        rootView.setBackgroundColor(textBackgroudColor);
                    }

                }

                textEditext.setText("");
                dialog.dismiss();
            }
        });

        imageCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




    }




    @Override
    public void colorSelected(int colorId, String type) {

        if(type.equals(getResources().getString(R.string.text_color)))
        {
            if(!textSwitch.isChecked())
            {
                textEditext.setTextColor(colorId);
                finalTextColor =colorId;
            }

            if(textSwitch.isChecked())
            {
                textEditext.setBackgroundColor(colorId);
                textBackgroudColor =colorId;
            }

        }
        if(type.equals(getResources().getString(R.string.paint_color)))
        {
            mPhotoEditor.setBrushColor(colorId);
            brushDialog.cancel();
        }
    }


    @Override
    public void EmojiSelected(String emoji) {
        mPhotoEditor.addEmoji(emoji);
        emojiDialog.dismiss();
    }

    @Override
    public void StickerSelecteId(int rawId) {
        InputStream stream = null;
        stream = getResources().openRawResource(rawId);
        if(stream!=null) {
            Bitmap bitmap = BitmapFactory.decodeStream(stream);
            mPhotoEditor.addImage(bitmap);
        }
        stickerDialog.cancel();
    }


    class FetchImage extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Log.i("HeightWidth",bitmap.getHeight()+" "+bitmap.getWidth());
            Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight()+250,bitmap.getConfig());
            Canvas canvas = new Canvas(bitmap1);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap,0,250,null);
            photoEditorView.getSource().setImageBitmap(bitmap1);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSaved)
            finish();
    }

    @Override
    public void onBackPressed() {
        if(isworking)
            MainActivity.this.finish();
        if(!discardCheckDialog.isShowing())
            discardCheckDialog.show();
        else
            discardCheckDialog.dismiss();
    }

    public void AddSpinnerData()
    {
        typefaceSpinner = dialog.findViewById(R.id.spinnerDropDownTypeface);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeface_data, R.layout.textview_for_typeface);
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        typefaceSpinner.setAdapter(adapter);
        typefaceSpinner.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        typeface = Typeface.createFromAsset(getAssets(),String.valueOf(adapterView.getItemAtPosition(i)));
        textEditext.setTypeface(typeface);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
