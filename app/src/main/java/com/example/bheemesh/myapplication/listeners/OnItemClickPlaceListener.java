package com.example.bheemesh.myapplication.listeners;

import android.view.View;

import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.entities.HomeActionType;

/**
 * Created by bheemesh on 2/1/16.
 */
public interface OnItemClickPlaceListener {
    void onItemClick(View view, BaseItem placeObj, HomeActionType actionType);
}
