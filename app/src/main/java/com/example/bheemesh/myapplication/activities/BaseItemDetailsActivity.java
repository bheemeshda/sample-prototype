package com.example.bheemesh.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.adapters.DetailsFragmentAdapter;
import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.BaseItemContainer;

/**
 * Created by bheemesh on 2/1/16.
 */
public class BaseItemDetailsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BaseItemContainer itemList;
    private BaseItem itemSelected;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_place_details_view);
        Toolbar toolbarBookDetails = (Toolbar) findViewById(R.id.toolbar_book_details);
        toolbarBookDetails.setNavigationIcon(R.drawable.arrow_white);
        initBundle(getIntent());
        initFragments();
        scrollToNewsItem(getItemPosition());
    }

    private void initBundle(Intent intent) {
        if (intent != null) {
            itemList = (BaseItemContainer) intent.getSerializableExtra(Constants.BUNDLE_ITEM_COLLECTION);
            itemSelected = (BaseItem) intent.getSerializableExtra(Constants.BUNDLE_ITEM);
        }
    }

    private void initFragments() {
        viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        DetailsFragmentAdapter adapter = new DetailsFragmentAdapter(getFragmentManager());
        adapter.setPlaceCollection(itemList);
        viewPager.setAdapter(adapter);
    }

    private void scrollToNewsItem(int position) {
        viewPager.setCurrentItem(position, true);
    }

    private int getItemPosition() {
        BaseItem item = null;
        int position = 0;
        for (int i = 0; i < itemList.getBaseItems().size(); i++) {
            item = itemList.getBaseItems().get(i);
            if (itemSelected.equals(item.getId())) {
                position = i;
                break;
            }
        }
        return position;
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
}
