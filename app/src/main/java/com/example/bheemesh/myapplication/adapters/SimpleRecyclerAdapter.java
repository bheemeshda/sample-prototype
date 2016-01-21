package com.example.bheemesh.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.BaseItemContainer;
import com.example.bheemesh.myapplication.entities.HomeCardType;
import com.example.bheemesh.myapplication.listeners.OnItemClickPlaceListener;
import com.example.bheemesh.myapplication.listeners.UpdatableViewHolder;
import com.example.bheemesh.myapplication.viewfactory.PlaceHomeViewHolderFactory;

import java.util.ArrayList;

/**
 * Created by bheemesh on 26/11/15.
 */
public class SimpleRecyclerAdapter extends RecyclerView.Adapter {
    BaseItemContainer placeCollection;
    ArrayList<BaseItem> placeArrayList;
    Context context;
    OnItemClickPlaceListener clickListener;

    public SimpleRecyclerAdapter(Context context, BaseItemContainer placeCollection, OnItemClickPlaceListener clickListener) {
        this.context = context;
        this.placeCollection = placeCollection;
        this.placeArrayList = placeCollection.getBaseItems();
        this.clickListener = clickListener;
    }

    public void setPlaceCollection(BaseItemContainer placeCollection){
        this.placeCollection = placeCollection;
        this.placeArrayList = placeCollection.getBaseItems();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeCardType cardType = HomeCardType.fromIndex(viewType);
        return PlaceHomeViewHolderFactory.getHomeViewHolder(parent, clickListener, cardType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseItem placeObj = placeArrayList.get(position);
        UpdatableViewHolder updatableViewHolder = (UpdatableViewHolder) holder;
        updatableViewHolder.updateViewHolder(context, placeObj);
    }

    @Override
    public int getItemCount() {
        return placeArrayList == null ? 0 : placeArrayList.size();
    }
}