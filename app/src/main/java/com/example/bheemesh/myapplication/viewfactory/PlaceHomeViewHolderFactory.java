package com.example.bheemesh.myapplication.viewfactory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.entities.HomeCardType;
import com.example.bheemesh.myapplication.listeners.OnItemClickPlaceListener;
import com.example.bheemesh.myapplication.viewholders.PlaceViewHolder;

/**
 * Created by bheemesh on 2/1/16.
 */
public class PlaceHomeViewHolderFactory {

    public static RecyclerView.ViewHolder getHomeViewHolder(ViewGroup parent,
                                                            OnItemClickPlaceListener viewOnItemClickPlaceListener,
                                                            HomeCardType booksHomeCardType) {
        switch (booksHomeCardType) {
            case NORMAL:
                return new PlaceViewHolder(createView(parent, booksHomeCardType),
                        viewOnItemClickPlaceListener);
            default:
                return new PlaceViewHolder(createView(parent, booksHomeCardType),
                        viewOnItemClickPlaceListener);
        }
    }

    private static View createView(ViewGroup parent, HomeCardType booksHomeCardType) {
        switch (booksHomeCardType) {
            case NORMAL:
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerlist_item, parent, false);
            default:
                return LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recyclerlist_item, parent, false);
        }
    }

}
