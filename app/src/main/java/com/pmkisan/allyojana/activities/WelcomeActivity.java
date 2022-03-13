package com.pmkisan.allyojana.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.pmkisan.allyojana.R;
import com.pmkisan.allyojana.databinding.ActivityWelcomeBinding;
import com.pmkisan.allyojana.utils.CommonMethod;

import java.io.UnsupportedEncodingException;

public class WelcomeActivity extends AppCompatActivity {

    public static int count = 1;
    ActivityWelcomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MobileAds.initialize(this);
        CommonMethod.interstitialAds(WelcomeActivity.this);
        CommonMethod.getBannerAds(this, binding.adView);

        binding.startBtn.setOnClickListener(v -> {
            binding.lottieFlower.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.lottieFlower.setVisibility(View.GONE);

                    if (CommonMethod.mInterstitialAd != null) {
                        CommonMethod.mInterstitialAd.show(WelcomeActivity.this);
                        CommonMethod.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                finish();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                CommonMethod.mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    } else {
                        CommonMethod.interstitialAds(WelcomeActivity.this);
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        finish();
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }

                    // new AppOpenManager(MyApp.mInstance, Paper.book().read(Prevalent.openAppAds),getApplicationContext());

                }
            },3000);
        });

        binding.shareBtn.setOnClickListener(v -> CommonMethod.shareApp(this));

        binding.contactBtn.setOnClickListener(v -> {

            try {
                CommonMethod.whatsApp(this);
            } catch (UnsupportedEncodingException | PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        moveTaskToBack(true);
        System.exit(0);
    }


}