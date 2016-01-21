package com.example.bheemesh.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bheemesh.myapplication.R;
import com.example.bheemesh.myapplication.beans.BaseItemContainer;

/**
 * Created by bheemesh on 10/1/16.
 *
 * @author bheemesh
 */
public class PostNews extends AppCompatActivity {
    private EditText newsTitle, newArticle;
    private RelativeLayout attachLink, attachPhoto, attachVideo;
    private LinearLayout locationContainer, notifyContainer, tagContainer, actionBarContainer;

    private TextView locationText, notifyText, tagText, titleText;
    private ImageView locationPin, notifyPin, tagPin, postCancel, postDone;

    private BaseItemContainer newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);
        initUI();
        setOnclickListener();
    }

    private void initUI() {
        newsTitle = (EditText) findViewById(R.id.et_news_title);
        newArticle = (EditText) findViewById(R.id.et_news_article);

        attachLink = (RelativeLayout) findViewById(R.id.rv_link_container);
        attachPhoto = (RelativeLayout) findViewById(R.id.lv_photo_container);
        attachVideo = (RelativeLayout) findViewById(R.id.lv_video_container);

        locationContainer = (LinearLayout) findViewById(R.id.lv_location_container);
        notifyContainer = (LinearLayout) findViewById(R.id.lv_notify_container);
        tagContainer = (LinearLayout) findViewById(R.id.lv_tags_container);
        actionBarContainer = (LinearLayout) findViewById(R.id.lv_title_bar);

        locationText = (TextView) locationContainer.findViewById(R.id.et_location_name);
        locationPin = (ImageView) locationContainer.findViewById(R.id.iv_location_pin);
        notifyText = (TextView) notifyContainer.findViewById(R.id.et_location_name);
        notifyPin = (ImageView) notifyContainer.findViewById(R.id.iv_location_pin);
        tagText = (TextView) tagContainer.findViewById(R.id.et_location_name);
        tagPin = (ImageView) tagContainer.findViewById(R.id.iv_location_pin);
        titleText = (TextView) actionBarContainer.findViewById(R.id.tv_title_bar_title);
        postCancel = (ImageView) actionBarContainer.findViewById(R.id.iv_cancel);
        postDone = (ImageView) actionBarContainer.findViewById(R.id.iv_done);

        setValueToUI();
    }

    private void setValueToUI() {
        ((ImageView) attachLink.findViewById(R.id.iv_attach_image)).setImageResource(R.drawable.ic_link_attachment);
        ((ImageView) attachPhoto.findViewById(R.id.iv_attach_image)).setImageResource(R.drawable.ic_camera);
        ((ImageView) attachVideo.findViewById(R.id.iv_attach_image)).setImageResource(R.drawable.ic_vedio);

        ((TextView) attachLink.findViewById(R.id.tv_attachment_type)).setText("Links");
        ((TextView) attachPhoto.findViewById(R.id.tv_attachment_type)).setText("Photos");
        ((TextView) attachVideo.findViewById(R.id.tv_attachment_type)).setText("Videos");

        ((TextView) locationContainer.findViewById(R.id.tv_location_title)).setText("Location");
        ((TextView) notifyContainer.findViewById(R.id.tv_location_title)).setText("Notify");
        ((TextView) tagContainer.findViewById(R.id.tv_location_title)).setText("Tags");

        locationText.setText("Indira Nagara, Bangalore");
        notifyText.setText("Every disagree, comment");
        tagText.setText("#travel #travelling #hour #happy");
        titleText.setText("News List");
    }

    private void setOnclickListener() {
        postCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostNews.this, NewsListActivity.class);
                startActivity(intent);
            }
        });

        postDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostNews.this, NewsListActivity.class);
                startActivity(intent);
            }
        });
    }
}
