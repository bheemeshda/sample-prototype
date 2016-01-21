package com.example.bheemesh.myapplication.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.fragments.ProfileAboutFragment;
import com.example.bheemesh.myapplication.fragments.ProfileGeneralFragment;
import com.example.bheemesh.myapplication.listeners.FragmentEventListener;

/**
 * Created by Administrator on 11/17/2015.
 */
public class ProfileFragmentAdapter extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    private String tabTitles[] = new String[]{Constants.TAB_FAVOURITE, Constants.TAB_RECENT, Constants
            .TAB_COLLECTION};

    public ProfileFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setCategories(String[] categories) {
        this.tabTitles = categories;
    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment;
        if (tabTitles[index].equalsIgnoreCase(Constants.TAB_PROFILE_ABOUT)) {
            fragment = ProfileAboutFragment.newInstance();
        }else{
            fragment = ProfileGeneralFragment.newInstance();
        }
        return fragment;
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
