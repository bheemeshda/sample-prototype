/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */

package com.example.bheemesh.myapplication.listeners;

/**
 * Listener to get callback for share click.
 *
 * @author sumedh.tambat.
 */
public interface ShareViewShowListener {
  public void onShareViewShown();

  public void onShareViewClick(String packageName);
}
