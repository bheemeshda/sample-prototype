/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.example.bheemesh.myapplication.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;

import com.example.bheemesh.myapplication.Utils.UniqueIdHelper;

/**
 * Base Fragment class which generates a fragmentId unique to each fragment
 * This helps in uniquely identifying which fragment made the actual call
 *
 * @author bheemesh
 */
public class BaseFragment extends Fragment {

  private static final String FRAGMENT_ID = "FRAGMENT_ID";
  private int fragmentId;

  public BaseFragment() {
  }

  @Override
  public void onCreate(Bundle savedState) {
    super.onCreate(savedState);
    if (savedState != null) {
      fragmentId = savedState.getInt(FRAGMENT_ID);
    } else {
      fragmentId = UniqueIdHelper.getInstance().generateUniqueId();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putInt(FRAGMENT_ID, fragmentId);
    super.onSaveInstanceState(outState);
  }

  @Override
  public boolean getUserVisibleHint() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
      return super.getUserVisibleHint();
    } else {
      return true;
    }
  }

  protected int getFragmentId() {
    return fragmentId;
  }
}