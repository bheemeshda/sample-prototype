package com.example.bheemesh.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.Utils.Constants;
import com.example.bheemesh.myapplication.Utils.Utils;
import com.example.bheemesh.myapplication.beans.BaseItem;
import com.example.bheemesh.myapplication.dao.DBItemInfoSQLDao;

/**
 * Created by bheemesh on 26/11/15.
 */
public class DetailsFragment extends BaseFragment {
    private BaseItem item;
    private ImageView detailsImage;
    private TextView title, subTitle, description;


    public static DetailsFragment newInstance(BaseItem placeObj) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.BUNDLE_ITEM, placeObj);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initBundle(getArguments());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_details_view, container, false);
        detailsImage = (ImageView) view.findViewById(R.id.iv_details);
        title = (TextView) view.findViewById(R.id.tv_title);
        subTitle = (TextView) view.findViewById(R.id.tv_sub_title);
        description = (TextView) view.findViewById(R.id.tv_description);
        prepareView();
        return view;
    }

    private void initBundle(Bundle bundle) {
        if (bundle != null) {
            item = (BaseItem) bundle.get(Constants.BUNDLE_ITEM);
        }
    }

    private void prepareView() {
        detailsImage.setBackground(Utils.getDrawable(getActivity(), R.drawable.bheemesh));
        title.setText(item.getName());
        subTitle.setText(item.getSubName());
        description.setText(item.getDescription() + item.getDescription() + item.getDescription());
        updateRecentDB();
    }

    private void updateRecentDB() {
        item.setTimeAccess(System.currentTimeMillis());
        DBItemInfoSQLDao database = new DBItemInfoSQLDao(Utils.getApplication());
        database.open();
        database.updateNews(item);
    }
}
