package com.example.color_my_world;

import java.net.URL;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class FitEarring extends Activity {

    ImageView iv_hat;
    LinearLayout ll_hat;
    SharedPreferences sh;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_earring);


        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        iv_hat = findViewById(R.id.iv_hat);
        ll_hat = findViewById(R.id.ll_hat);
        BitmapFactory.Options bitmapFatoryOptions = new BitmapFactory.Options();
        bitmapFatoryOptions.inPreferredConfig = Config.RGB_565;
        Bitmap bm1 = PHOTO.image;
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bm1);
        ll_hat.setBackgroundDrawable(bitmapDrawable);
        try {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String ornamnce = sh.getString("images", "") ;
            String path = "http://" + sh.getString("ip", "") + "/";
            URL ur1 = new URL(path.concat(ornamnce));
            Bitmap bm2 = BitmapFactory.decodeStream(ur1.openStream(), null, bitmapFatoryOptions);
            iv_hat.setImageBitmap(bm2);

        } catch (Exception e) {
            System.out.println("error in try catch " + e.toString());
        }
        iv_hat.setOnTouchListener(new Touch());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //	getMenuInflater().inflate(R.menu.fit_earring, menu);
        return true;
    }
}