package com.example.bheemesh.myapplication.viewfactory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.entities.NewsCardType;
import com.example.bheemesh.myapplication.listeners.OnItemClickListener;
import com.example.bheemesh.myapplication.viewholders.NewsViewLocationHolder;
import com.example.bheemesh.myapplication.viewholders.NewsViewNormalHolder;

/**
 * Created by bheemesh on 2/1/16.
 */
public class NewsHomeViewHolderFactory {

    public static RecyclerView.ViewHolder getNewsViewHolder(ViewGroup parent,
                                                            OnItemClickListener viewOnItemClickPlaceListener,
                                                            NewsCardType newsCardType) {
        switch (newsCardType) {
            case NORMAL:
                return new NewsViewNormalHolder(createView(parent, newsCardType),
                        viewOnItemClickPlaceListener);
            case WITH_CAT_LOCATION:
                return new NewsViewLocationHolder(createView(parent, newsCardType),
                        viewOnItemClickPlaceListener);
            default:
                return new NewsViewNormalHolder(createView(parent, newsCardType),
                        viewOnItemClickPlaceListener);
        }
    }

    private static View createView(ViewGroup parent, NewsCardType newsCardType) {
        switch (newsCardType) {
            case NORMAL:
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerlist_item, parent, false);
            case WITH_CAT_LOCATION:
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerlist_item_cat_location, parent, false);
            default:
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerlist_item, parent, false);
        }
    }

}
