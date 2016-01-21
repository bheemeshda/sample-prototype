package com.example.bheemesh.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.Location;
import com.example.bheemesh.myapplication.dao.DBItemInfoSQLDao;
import com.example.bheemesh.myapplication.dao.DBLocationInfoSQLDao;
import com.example.bheemesh.myapplication.singleton.SSO;

import java.util.ArrayList;

/**
 * Created by bheemesh on 3/1/16.
 */
public class SplashViewActivity extends AppCompatActivity {
    DBItemInfoSQLDao sqlNewsDao;
    DBLocationInfoSQLDao sqlLocationDao;

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sqlNewsDao = new DBItemInfoSQLDao(getApplicationContext());
        sqlNewsDao.open();
        sqlLocationDao = new DBLocationInfoSQLDao(getApplicationContext());
        sqlLocationDao.open();
        loadNewsInDB();
        SSO.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        callHomeActivity();
    }

    private void callHomeActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashViewActivity.this, SignOnActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constants.SPLASH_TIME);
    }

    private void loadNewsInDB() {
        ArrayList<BaseItem> placeArrayList = new ArrayList<>();
        String[] name = getResources().getStringArray(R.array.news_name);
        String[] subName = getResources().getStringArray(R.array.news_sub_name);
        String[] description = getResources().getStringArray(R.array.news_description);

        BaseItem baseItem = null;
        for (int i = 0; i < name.length; i++) {
            baseItem = new BaseItem();
            baseItem.setId("" + i);
            baseItem.setName(name[i]);
            baseItem.setSubName(subName[i]);
            baseItem.setDescription(description[i]);

            baseItem.setImageUrl(Constants.ImageUrl);
            baseItem.setLocation(getLocation());
            baseItem.setRating(Constants.district);
            baseItem.setTimeAccess(0);
            baseItem.setIsFavorite(false);

            placeArrayList.add(baseItem);
        }
        sqlNewsDao.updateNewsItems("1", placeArrayList);
    }

    private Location getLocation(){
        Location location = new Location();
        location.setCountry("India");
        location.setState("Karnataka");
        location.setDistrict("Shimoga");
        location.setTaluk("Honnali");
        location.setLatitude("12.5345");
        location.setLongitude("76.3432");
        location.setLandMark("Near tunga bridge");
        return location;
    }
}
