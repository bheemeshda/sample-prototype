package com.example.bheemesh.myapplication.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.adapters.NewsTabFragmentAdapter;
import com.example.bheemesh.myapplication.listeners.ActivityEventListener;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NewsListActivity extends AppCompatActivity implements ActivityEventListener {
    private ViewPager viewPager;
    private NewsTabFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);
    }

    private void setupViewPager(ViewPager viewPager) {
        String tabTitles[] = new String[]{Constants.TAB_LOCATION, Constants.TAB_INTEREST, Constants
                .TAB_BY_MARK};
        adapter = new NewsTabFragmentAdapter(getFragmentManager());
        adapter.setTabs(tabTitles);
        viewPager.setAdapter(adapter);
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
