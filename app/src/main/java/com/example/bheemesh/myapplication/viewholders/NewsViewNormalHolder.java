package com.example.bheemesh.myapplication.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Utils;
import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.dao.DBItemInfoSQLDao;
import com.example.bheemesh.myapplication.entities.HomeActionType;
import com.example.bheemesh.myapplication.listeners.NewsUpdatableViewHolder;
import com.example.bheemesh.myapplication.listeners.OnItemClickListener;

/**
 * Created by bheemesh on 2/1/16.
 */
public class NewsViewNormalHolder extends RecyclerView.ViewHolder implements NewsUpdatableViewHolder {
    CardView cardItemLayout;
    ImageView isFavouriteImg;
    TextView title;
    TextView subTitle;
    OnItemClickListener clickListener;
    BaseItem placeObj;

    public NewsViewNormalHolder(View itemView, OnItemClickListener clickListener) {
        super(itemView);
        this.clickListener = clickListener;
        cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
        title = (TextView) itemView.findViewById(R.id.listitem_name);
        subTitle = (TextView) itemView.findViewById(R.id.listitem_subname);
        isFavouriteImg = (ImageView) itemView.findViewById(R.id.iv_favourite);
        setOnclickListener(null);
    }

    private void setOnclickListener(final OnItemClickListener clickListener) {
        cardItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("card item got clicked");
                clickListener.onItemClick(view, placeObj, HomeActionType.DETAILS);
            }
        });
        isFavouriteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeObj.setIsFavorite(!placeObj.isFavorite());
                placeObj.setFavouriteAddedTime(System.currentTimeMillis());
                DBItemInfoSQLDao database = new DBItemInfoSQLDao(Utils.getApplication());
                database.open();
                database.updateNews(placeObj);
                isFavouriteImg.setSelected(placeObj.isFavorite());
            }
        });

    }

    @Override
    public void updateViewHolder(Context context, BaseItem placeObj) {
        this.placeObj = placeObj;
        title.setText(placeObj.getName());
        subTitle.setText(placeObj.getSubName());
        isFavouriteImg.setSelected(placeObj.isFavorite());
        setOnclickListener(clickListener);
    }
}
