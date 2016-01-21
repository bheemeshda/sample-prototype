/*
The MIT License (MIT)

Copyright (c) 2014 Nir Hartmann

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

  Link to parallax scroll view
  https://github.com/nirhart/ParallaxScroll
 */

package com.example.bheemesh.myapplication.customeviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.Utils.Utils;

import java.util.ArrayList;

public class ParallaxScrollView extends NotifyingScrollView {

  private static final int DEFAULT_PARALLAX_VIEWS = 1;
  private int numOfParallaxViews = DEFAULT_PARALLAX_VIEWS;
  private static final float DEFAULT_INNER_PARALLAX_FACTOR = 1.9F;
  private static final float DEFAULT_PARALLAX_FACTOR = 1.9F;
  private float innerParallaxFactor = DEFAULT_PARALLAX_FACTOR;
  private float parallaxFactor = DEFAULT_PARALLAX_FACTOR;
  private static final float DEFAULT_ALPHA_FACTOR = -1F;
  private float alphaFactor = DEFAULT_ALPHA_FACTOR;
  private ArrayList<ParallaxedView> parallaxedViews = new ArrayList<ParallaxedView>();

  private int screenHeight;
  private int imageHeight;

  private Toolbar toolbar;
  private View shareOptions;
  private boolean isToolbarVisible = false;
  private boolean nearEndOfPage = false;
  private String assetType;

  public ParallaxScrollView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs);
  }

  public ParallaxScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public ParallaxScrollView(Context context) {
    super(context);
  }

  protected void init(Context context, AttributeSet attrs) {
    TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.ParallaxScroll);
    this.parallaxFactor = typeArray.getFloat(R.styleable.ParallaxScroll_parallax_factor,
        DEFAULT_PARALLAX_FACTOR);
    this.alphaFactor = typeArray.getFloat(R.styleable.ParallaxScroll_alpha_factor,
        DEFAULT_ALPHA_FACTOR);
    this.innerParallaxFactor = typeArray.getFloat(R.styleable.ParallaxScroll_inner_parallax_factor,
        DEFAULT_INNER_PARALLAX_FACTOR);
    this.numOfParallaxViews = typeArray.getInt(R.styleable.ParallaxScroll_parallax_views_num,
        DEFAULT_PARALLAX_VIEWS);
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    screenHeight = metrics.heightPixels;
    imageHeight = (int) (350 * metrics.density);
    typeArray.recycle();
  }

  public void setToolbar(Toolbar toolbar) {
    this.toolbar = toolbar;
    imageHeight = toolbar.getHeight();
  }

  public void setShareOptions(ShareView shareOptions) {
    this.shareOptions = shareOptions;
    this.shareOptions.setVisibility(GONE);
  }

  public void hideShareWithAnimation() {
    shareOptions.animate().translationY(0).setInterpolator(
        new AccelerateInterpolator(2));

  }

  public void showShareWithAnimation() {
    shareOptions.animate().translationY(shareOptions.getHeight()).setInterpolator(
        new DecelerateInterpolator(2));
  }

  private void showActionbarWithAnimation() {
    toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(
        new AccelerateInterpolator(2));
  }

  private void hideActionbarWithAnimation() {
    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
  }

  public void showShareOptions() {
    this.shareOptions.setVisibility(VISIBLE);
  }

  private void onTopToBottomSwipe() {
    if (isToolbarVisible) {
      isToolbarVisible = false;
      if (Utils.isEmpty(assetType) || !assetType.equalsIgnoreCase(Constants.VIDEO_TYPE)) {
        hideActionbarWithAnimation();
      }
      if (shareOptions.getVisibility() == GONE) {
        shareOptions.setVisibility(VISIBLE);
      }
      hideShareWithAnimation();
    }
  }

  private void onBottomToTopSwipe() {
    if (!isToolbarVisible) {
      isToolbarVisible = true;
      if (Utils.isEmpty(assetType) || !assetType.equalsIgnoreCase(Constants.VIDEO_TYPE)) {
        showActionbarWithAnimation();
      }
      showShareWithAnimation();
    }
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    makeViewsParallax();
  }

  private void makeViewsParallax() {
    if (getChildCount() > 0 && getChildAt(0) instanceof ViewGroup) {
      ViewGroup viewsHolder = (ViewGroup) getChildAt(0);
      int numOfParallaxViews = Math.min(this.numOfParallaxViews, viewsHolder.getChildCount());
      for (int i = 0; i < numOfParallaxViews; i++) {
        ParallaxedView parallaxedView = new ScrollViewParallaxedItem(viewsHolder.getChildAt(i));
        parallaxedViews.add(parallaxedView);
      }
    }
  }

  @Override
  protected void onScrollChanged(int x, int y, int oldX, int oldY) {
    super.onScrollChanged(x, y, oldX, oldY);
    float parallax = parallaxFactor;
    float alpha = alphaFactor;
    for (ParallaxedView parallaxedView : parallaxedViews) {
      parallaxedView.setOffset((float) y / parallax);
      parallax *= innerParallaxFactor;
      if (alpha != DEFAULT_ALPHA_FACTOR) {
        float fixedAlpha = (y <= 0) ? 1 : (100 / ((float) y * alpha));
        parallaxedView.setAlpha(fixedAlpha);
        alpha /= alphaFactor;
      }
      parallaxedView.animateNow();
    }

    View view = this.getChildAt(0);
    int diff = (view.getBottom() - (this.getHeight() + this.getScrollY()));
    nearEndOfPage = diff <= 250;

    //TODO: (anand.winjit) needed when fine tuning action bar with changes
    //toolbar.setAlpha(getAlphaForView(imageHeight - this.getScrollY()));

    if (toolbar != null && shareOptions != null) {
      if (y > oldY && (oldY >= 0)) {
        onBottomToTopSwipe();
      } else if (y + 5 < oldY) {
        onTopToBottomSwipe();
      }
    } else if (toolbar == null && assetType != null &&
        assetType.equalsIgnoreCase(Constants.VIDEO_TYPE)) {
      if (y > oldY && (oldY >= 0)) {
        onBottomToTopSwipe();
      } else if (y + 5 < oldY) {
        onTopToBottomSwipe();
      }
    }
  }

  public boolean isNearEndOfPage() {
    return nearEndOfPage;
  }

  public void setAssetType(String assetType) {
    this.assetType = assetType;
  }

  public int getScreenHeight() {
    return screenHeight;
  }

  protected class ScrollViewParallaxedItem extends ParallaxedView {

    public ScrollViewParallaxedItem(View view) {
      super(view);
    }

    @Override
    protected void translatePreICS(View view, float offset) {
      view.offsetTopAndBottom((int) offset - lastOffset);
      lastOffset = (int) offset;
    }
  }
}