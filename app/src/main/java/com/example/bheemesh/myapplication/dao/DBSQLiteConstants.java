package com.example.bheemesh.myapplication.dao;

/**
 * Created by bheemesh on 20/1/16.
 *
 * @author bheemesh
 */
public class DBSQLiteConstants {
    // ------ NEWS RELATED VARIABLES --------------

    public static final String NEWS_ID = "place_id";
    public static final String NEWS_NAME = "place_name";
    public static final String NEWS_SUB_NAME = "place_sub_name";
    public static final String NEWS_DESCRIPTION = "place_description";
    public static final String NEWS_IMAGE_URL = "place_imageUrl";
    public static final String NEWS_LANDMARK = "place_landMark";
    public static final String NEWS_TOPIC_KEY = "place_category";
    public static final String NEWS_LOCATION_ID = "location_id";

    //--------- LOCATION RELATED VARIABLES --------------------
    public static final String LOCATION_ID = "location_id";
    public static final String LOCATION_COUNTRY = "location_country";
    public static final String LOCATION_STATE = "location_state";
    public static final String LOCATION_DISTRICT = "location_district";
    public static final String LOCATION_TALUK = "location_taluk";
    public static final String LOCATION_LAT = "location_lat";
    public static final String LOCATION_LONG = "location_long";
    public static final String LOCATION_LANDMARK = "location_landmark";

    //--------USER EDITED VARIABLES ---------------
    public static final String BASE_ITEMS_RATING = "place_rating";
    public static final String BASE_ITEMS_TIME_ACCESS = "place_time_access";
    public static final String BASE_ITEMS_FAVORITE = "place_favorite";
    public static final String BASE_ITEMS_FAVORITE_ADDED_TIME = "place_favorite_added_time";

    // ----------- CATEGORY TABLE FIELDS-------------

    public static final String TOPIC_ID = "id";
    public static final String TOPIC_KEY = "category_key";
    public static final String TOPIC_NAME_ENG = "name_eng";
    public static final String TOPIC_DISPLAY_ENG = "display_name";
    public static final String TOPIC_IMG_LINK = "img_link";
    public static final String TOPIC_IS_OPTIONAL = "is_optional";
    public static final String TOPIC_DATA_LINK = "link";
}
