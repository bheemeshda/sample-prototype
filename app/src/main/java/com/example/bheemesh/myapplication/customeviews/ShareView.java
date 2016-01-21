/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.example.bheemesh.myapplication.customeviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.bheemesh.myapplication.R;

/**
 * Provides functionality for various sharing options
 *
 * @author bheemesh
 */
public class ShareView extends LinearLayout {
  private static final int MAX_SHARE_OPTIONS = 4;

  private Context context;

  public ShareView(Context context) {
    super(context);
    this.context = context;
    init();
  }

  public ShareView(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    init();
  }

  public ShareView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.context = context;
    init();
  }

  private void init() {
    View shareView = LayoutInflater.from(getContext())
        .inflate(R.layout.view_share_options, this, true);
  }
}