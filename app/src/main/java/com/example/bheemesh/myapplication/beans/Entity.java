/*
 * Copyright (c) 2015 Newshunt. All rights reserved.
 */
package com.example.bheemesh.myapplication.beans;

/**
 * Setter and getter of product IDs.
 *
 * @param <K>
 */
public interface Entity<K> {

  K getId();

  void setId(K id);
}
