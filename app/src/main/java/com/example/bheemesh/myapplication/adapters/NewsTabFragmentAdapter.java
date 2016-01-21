package com.example.bheemesh.myapplication.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.fragments.NewsListFragment;
import com.example.bheemesh.myapplication.listeners.FragmentEventListener;

/**
 * Created by Administrator on 11/17/2015.
 */
public class NewsTabFragmentAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    private String tabTitles[] = new String[]{Constants.TAB_LOCATION, Constants.TAB_INTEREST, Constants
            .TAB_BY_MARK};

    public NewsTabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setTabs(String[] tabsList) {
        this.tabTitles = tabsList;
    }

    @Override
    public Fragment getItem(int index) {
        return NewsListFragment.newInstance(tabTitles[index]);
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return tabTitles.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public void updateAllFragments() {
        FragmentEventListener fragment = null;
        for (int i = 0; i < registeredFragments.size(); i++) {
            fragment = (FragmentEventListener) registeredFragments.get(i);
            if (!fragment.isFragmentVisible()) {
                fragment.updateFragment(true);
            }
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
