package com.example.bheemesh.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.Utils.Utils;
import com.example.bheemesh.myapplication.activities.BaseItemDetailsActivity;
import com.example.bheemesh.myapplication.adapters.NewsListAdapter;
import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.beans.BaseItemContainer;
import com.example.bheemesh.myapplication.dao.DBItemInfoSQLDao;
import com.example.bheemesh.myapplication.entities.HomeActionType;
import com.example.bheemesh.myapplication.entities.NewsCardType;
import com.example.bheemesh.myapplication.listeners.ActivityEventListener;
import com.example.bheemesh.myapplication.listeners.FragmentEventListener;
import com.example.bheemesh.myapplication.listeners.OnItemClickListener;

import java.util.ArrayList;

/**
 * Created by bheemesh on 26/11/15.
 */
public class NewsListFragment extends BaseFragment implements OnItemClickListener, FragmentEventListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private NewsListAdapter adapter;
    private BaseItemContainer newsList;
    private String pageTabName;
    private boolean isVisible;
    private ActivityEventListener parentActivity;


    public static NewsListFragment newInstance(String pageTitle) {
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, pageTitle);
        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            pageTabName = getArguments().getString(ARG_PAGE);
        }
        loadPlaces();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (ActivityEventListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dummyfrag_scrollableview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new NewsListAdapter(getActivity(), newsList, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void loadPlaces() {
        DBItemInfoSQLDao database = new DBItemInfoSQLDao(Utils.getApplication());
        database.open();
        ArrayList<BaseItem> baseItems = null;
        baseItems = database.getAllBaseItems();

        for (int i = 0; i < baseItems.size(); i++) {
            if (pageTabName.equals(Constants.TAB_LOCATION)) {
                ((BaseItem) baseItems.get(i)).setCardType(NewsCardType.NORMAL);
            } else {
                ((BaseItem) baseItems.get(i)).setCardType(NewsCardType.WITH_CAT_LOCATION);
            }
        }

        newsList = new BaseItemContainer();
        newsList.setId("" + pageTabName);
        newsList.setCollectionTitle(pageTabName + " Title");
        newsList.setCollectionSubTitle(pageTabName + " Sub Title");
        newsList.setDisplyName("" + pageTabName);
        newsList.setBaseItems(baseItems);
    }

    @Override
    public void onItemClick(View view, BaseItem placeObj, HomeActionType actionType) {
        Intent intent = null;
        switch (actionType) {
            case DETAILS:
                intent = new Intent(getActivity(), BaseItemDetailsActivity.class);
                intent.putExtra(Constants.BUNDLE_ITEM_COLLECTION, newsList);
                intent.putExtra(Constants.BUNDLE_ITEM, placeObj);
                break;
            case UPDATEUI:
                updateParentActivity();
                break;
            case CATEGORYLIST:
            case NONCLICKABLE:
                break;
        }
        System.out.println("BHEEM : OnItem click in HomeFragment");
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void updateParentActivity() {
        parentActivity.updateActivity(true);
    }

    @Override
    public void updateFragment(boolean updateAllFragment) {
        if (adapter != null) {
            if (pageTabName.equals(Constants.TAB_FAVOURITE)) {
                loadPlaces();
            } else if (pageTabName.equals(Constants.TAB_RECENT)) {
                loadPlaces();
            }
            adapter.setPlaceCollection(newsList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean isFragmentVisible() {
        return isVisible;
    }
}
