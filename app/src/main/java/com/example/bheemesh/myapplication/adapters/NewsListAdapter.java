package com.example.bheemesh.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.BaseItemContainer;
import com.example.bheemesh.myapplication.entities.NewsCardType;
import com.example.bheemesh.myapplication.listeners.NewsUpdatableViewHolder;
import com.example.bheemesh.myapplication.listeners.OnItemClickListener;
import com.example.bheemesh.myapplication.listeners.OnItemClickPlaceListener;
import com.example.bheemesh.myapplication.viewfactory.NewsHomeViewHolderFactory;

import java.util.ArrayList;

/**
 * Created by bheemesh on 26/11/15.
 */
public class NewsListAdapter extends RecyclerView.Adapter {
    BaseItemContainer placeCollection;
    ArrayList<BaseItem> newsItems;
    Context context;
    OnItemClickListener clickListener;

    public NewsListAdapter(Context context, BaseItemContainer placeCollection,
                           OnItemClickListener clickListener) {
        this.context = context;
        this.placeCollection = placeCollection;
        this.newsItems = placeCollection.getBaseItems();
        this.clickListener = clickListener;
    }

    public void setPlaceCollection(BaseItemContainer placeCollection){
        this.placeCollection = placeCollection;
        this.newsItems = placeCollection.getBaseItems();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsCardType cardType = NewsCardType.fromIndex(viewType);
        return NewsHomeViewHolderFactory.getNewsViewHolder(parent, clickListener, cardType);
    }

    @Override
    public int getItemViewType(int position) {
        NewsCardType displayCardType = getContentItem(position).getCardType();
        if (displayCardType == null) {
            return -1;
        }
        return displayCardType.getIndex();
    }
    public BaseItem getContentItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseItem placeObj = newsItems.get(position);
        NewsUpdatableViewHolder updatableViewHolder = (NewsUpdatableViewHolder) holder;
        updatableViewHolder.updateViewHolder(context, placeObj);
    }

    @Override
    public int getItemCount() {
        return newsItems == null ? 0 : newsItems.size();
    }
}