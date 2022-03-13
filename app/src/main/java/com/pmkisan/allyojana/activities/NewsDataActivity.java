package com.pmkisan.allyojana.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.pmkisan.allyojana.R;
import com.pmkisan.allyojana.activities.ui.main.PageViewModel;
import com.pmkisan.allyojana.databinding.ActivityNewsDataBinding;
import com.pmkisan.allyojana.models.ModelFactory;
import com.pmkisan.allyojana.models.PreviewModel;
import com.pmkisan.allyojana.utils.AppOpenManager;
import com.pmkisan.allyojana.utils.CommonMethod;
import com.pmkisan.allyojana.utils.Prevalent;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class NewsDataActivity extends AppCompatActivity {
    ImageView newsImg, backIcon;
    TextView newsTitle, newsDesc;
    String id, img, desc, title;
    ActivityNewsDataBinding binding;
    PageViewModel pageViewModel;
    Map<String, String> map = new HashMap<>();
    LottieAnimationView lottieAnimationView;
    InterstitialAd interstitialAd;
    Dialog dialog;
    int pos;

//    String finalEnglishString, finalHindiString;

    /*ads variable*/
    /*ads variable*/

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MobileAds.initialize(NewsDataActivity.this);
        newsImg = binding.newsImg;
        newsTitle = binding.newsTitle;
        newsDesc = binding.newsDesc;
        backIcon = binding.backIcon;
        lottieAnimationView = binding.lottieHome;
        id = getIntent().getStringExtra("id");
        dialog = CommonMethod.getDialog(this);
        dialog.show();
        pos = getIntent().getIntExtra("pos", 0);

        if (pos%2==0){
            new AppOpenManager(this.getApplication(), Paper.book().read(Prevalent.openAppAds),this);
        }
        map.put("previewId", id);

        newsTitle.setVisibility(View.VISIBLE);
        newsImg.setVisibility(View.VISIBLE);
        newsDesc.setVisibility(View.VISIBLE);
        backIcon.setOnClickListener(v -> onBackPressed());
        pageViewModel = new ViewModelProvider(this, new ModelFactory(this.getApplication(), map)).get(PageViewModel.class);
        pageViewModel.getPreviewData().observe(this, previewModelList -> {
            dialog.show();
            if (!previewModelList.getData().isEmpty()) {
                dialog.dismiss();
                title = getIntent().getStringExtra("title");
                img = getIntent().getStringExtra("img");
                Glide.with(this).load("https://gedgetsworld.in/PM_Kisan_Yojana/News_Images/" + img).into(newsImg);
                newsTitle.setText(title);
                CommonMethod.getBannerAds(this, binding.adViewNews);
                for (PreviewModel m : previewModelList.getData()) {
                    if (m.getPreviewId().equals(id)) {
                        String replaceString = m.getDesc().replaceAll("<.*?>", "");

                        newsDesc.setText(replaceString);
                        newsDesc.setVisibility(View.VISIBLE);



                    }
                }

            } else {
                dialog.dismiss();
                newsImg.setVisibility(View.GONE);
                newsTitle.setVisibility(View.GONE);
                newsDesc.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
    }
}