package com.example.bheemesh.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.adapters.ProfileFragmentAdapter;
import com.example.bheemesh.myapplication.adapters.TabFragmentAdapter;
import com.example.bheemesh.myapplication.listeners.ActivityEventListener;
import com.example.bheemesh.myapplication.singleton.SSO;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ProfileActivity extends AppCompatActivity implements ActivityEventListener {
    private ViewPager viewPager;
    private ProfileFragmentAdapter adapter;
    private TextView postNews;
    private PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        postNews = (TextView) findViewById(R.id.tv_post);

        SSO.getInstance();
        setupViewPager(viewPager);
        tabs.setViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
        setOnclickListener();
    }

    private void setupViewPager(ViewPager viewPager) {
        String tabTitles[] = new String[]{Constants.TAB_PROFILE_GENERAL, Constants.TAB_PROFILE_ABOUT};
        adapter = new ProfileFragmentAdapter(getFragmentManager());
        adapter.setCategories(tabTitles);
        viewPager.setAdapter(adapter);
    }

    private void setOnclickListener(){
        postNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPostNews();
            }
        });
    }

    private void callPostNews(){
        Intent intent = new Intent(this, PostNews.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateActivity(boolean updateAllFragment) {
        if (adapter != null) {
            adapter.updateAllFragments();
        }
    }

}
