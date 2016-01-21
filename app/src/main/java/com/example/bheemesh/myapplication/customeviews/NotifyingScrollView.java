/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.example.bheemesh.myapplication.customeviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Scroll view which notifies on scroll
 *
 * @author sumedh.tambat
 */
public class NotifyingScrollView extends ScrollView {

  private OnScrollChangedListener onScrollChangedListener;

  public NotifyingScrollView(Context context) {
    super(context);
  }

  public NotifyingScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);

    if (onScrollChangedListener != null) {
      onScrollChangedListener.onScrollChanged(t);
    }
  }

  public void setOnScrollChangedListener(OnScrollChangedListener listener) {
    this.onScrollChangedListener = listener;
  }

  public interface OnScrollChangedListener {
    void onScrollChanged(int scrollY);
  }
}


