/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.example.bheemesh.myapplication.listeners;

import android.content.Context;

import com.example.bheemesh.myapplication.beans.BaseItem;

/**
 * Recycler view holder Updater
 * <p/>
 *
 * @author bheemesh
 */
public interface NewsUpdatableViewHolder {
  void updateViewHolder(Context context, BaseItem newsItem);
}
