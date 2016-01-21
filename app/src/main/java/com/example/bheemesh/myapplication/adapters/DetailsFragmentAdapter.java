package com.example.bheemesh.myapplication.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.BaseItemContainer;
import com.example.bheemesh.myapplication.fragments.DetailsFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/17/2015.
 */
public class DetailsFragmentAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;
    private ArrayList<BaseItem> baseItemsList;
    private BaseItemContainer itemListContainer;

    public DetailsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setPlaceCollection(BaseItemContainer itemList){
        this.itemListContainer = itemList;
        this.baseItemsList = itemList.getBaseItems();
    }

    @Override
    public Fragment getItem(int index) {
        BaseItem item = baseItemsList.get(index);
        return DetailsFragment.newInstance(item);
    }

    @Override
    public int getCount() {
        return baseItemsList.size();
    }
}
