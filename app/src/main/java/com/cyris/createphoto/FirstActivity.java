package com.cyris.createphoto;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyris.createphoto.Adapters.SavedViewPagerAdapter;
import com.cyris.createphoto.Adapters.ViewPagerAdapter;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.theartofdev.edmodo.cropper.CropImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.gms.ads.InterstitialAd;

public class FirstActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 55;
    private AppBarConfiguration mAppBarConfiguration;
    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter mainAdapter;
    Dialog exitDialog;
    Button cancelButton,exitButton;
    SavedViewPagerAdapter savedViewPagerAdapter;
    ImageView addImageInLowerSection,homeInLowerSection,bookmarkInLowerSection;
    AdLoader adLoader;
    FrameLayout adFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();


        this.findViewById(R.id.mainFrameLayout).setBackground(new ColorDrawable(Color.TRANSPARENT));
        tabLayout = findViewById(R.id.tabLayoutInFirstActivity);
        viewPager =findViewById(R.id.viewPagerInFirstActivity);
        mainAdapter =new ViewPagerAdapter(getSupportFragmentManager());
        savedViewPagerAdapter = new SavedViewPagerAdapter(getSupportFragmentManager());
        addImageInLowerSection = findViewById(R.id.addImageInLowerSection);
        homeInLowerSection = findViewById(R.id.homeImageInLowerSection);
        bookmarkInLowerSection = findViewById(R.id.savedImageInLowerSection);
        onLowerSectionClick();
        exitDialog = new Dialog(this);
        exitDialog.setContentView(R.layout.exit_dialog);
        TextView textView = exitDialog.findViewById(R.id.exitDialogTextExit);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"TheGodfather-v2.ttf");
        textView.setTypeface(typeface);
        cancelButton = exitDialog.findViewById(R.id.cancelTextInExitDialog);
        exitButton = exitDialog.findViewById(R.id.exitTextInExitDialog);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstActivity.this.finishAffinity();
            }
        });
        adFrameLayout = exitDialog.findViewById(R.id.adSectionExitFrameLayout);

       viewPager.setAdapter(mainAdapter);
       tabLayout.setupWithViewPager(viewPager);
        exitAdLoading();



    }


 
    private void onLowerSectionClick() {

        addImageInLowerSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,22);

            }
        });

        homeInLowerSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInLowerSection.setImageResource(R.drawable.home_svg_dark);
                bookmarkInLowerSection.setImageResource(R.drawable.bookmark_svg);
                if (viewPager.getAdapter()!=mainAdapter)
                {
                    viewPager.setAdapter(mainAdapter);
                    tabLayout.setVisibility(View.VISIBLE);
                    getSupportActionBar().hide();
                }

            }
        });

        bookmarkInLowerSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(FirstActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_STORAGE);


                } else {
                    homeInLowerSection.setImageResource(R.drawable.home_svg);
                    bookmarkInLowerSection.setImageResource(R.drawable.bookmark_svg_dark);
                    if (viewPager.getAdapter()!=savedViewPagerAdapter)
                    {
                        viewPager.setAdapter(savedViewPagerAdapter);
                        tabLayout.setVisibility(View.GONE);
                        getSupportActionBar().show();
                    }
                }

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==22)
        {
            if(data!=null)
            { CropImage.activity(data.getData())
                    .start(this);
            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Intent intent = new Intent(FirstActivity.this,MainActivity.class);
            intent.putExtra(getString(R.string.intent_meme_home),result.getUri().toString());
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

        if(exitDialog.isShowing())
            exitDialog.dismiss();
        exitDialog.show();
        adLoader.loadAd(new AdRequest.Builder().build());

    

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if (viewPager.getAdapter()!=savedViewPagerAdapter)
            {
                viewPager.setAdapter(savedViewPagerAdapter);
                tabLayout.setVisibility(View.GONE);
                getSupportActionBar().show();
            }
        }
        if(requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE && grantResults[0]==PackageManager.PERMISSION_DENIED)
        {
            Snackbar.make(viewPager,"Permission Denied",Snackbar.LENGTH_SHORT).show();
        }

        if(requestCode == 11 && grantResults[0]==PackageManager.PERMISSION_DENIED)
        {
            Snackbar.make(viewPager,"Permission Denied",Snackbar.LENGTH_SHORT).show();
        }
         if(requestCode == 12 && grantResults[0]==PackageManager.PERMISSION_DENIED)
        {
            Snackbar.make(viewPager,"Permission Denied",Snackbar.LENGTH_SHORT).show();
        }
        if(requestCode == 13 && grantResults[0]==PackageManager.PERMISSION_DENIED)
        {
            Snackbar.make(viewPager,"Permission Denied",Snackbar.LENGTH_SHORT).show();
        }
        if(requestCode == 11 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Snackbar.make(viewPager,"Please Click Again",Snackbar.LENGTH_SHORT).show();
        }
         if(requestCode == 12 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Snackbar.make(viewPager,"Please Click Again",Snackbar.LENGTH_SHORT).show();
                }
         if(requestCode == 13 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Snackbar.make(viewPager,"Please Click Again",Snackbar.LENGTH_SHORT).show();
                }


    }


    public void exitAdLoading()
    {
         adLoader = new AdLoader.Builder(this, "")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();

                        TemplateView template = exitDialog.findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();
    }


}
