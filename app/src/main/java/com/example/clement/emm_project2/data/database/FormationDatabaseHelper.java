package com.example.clement.emm_project2.data.database;

/**
 * Created by Clement on 17/08/15.
 */
public class FormationDatabaseHelper {

    public static final String TABLE_NAME = "formation";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MONGOID = "mongoid";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUBTITLE = "subtitle";
    public static final String COLUMN_PRODUCT_URL = "productUrl";
    public static final String COLUMN_EAN = "ean";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_OBJECTIVES = "objectives";
    public static final String COLUMN_PREREQUISITES = "prerequisites";
    public static final String COLUMN_QCM = "qcm";
    public static final String COLUMN_TEASER_TEXT = "teaserText";
    public static final String COLUMN_CATEGORY = "catId";
    public static final String COLUMN_SUBCATEGORY = "subCatId";
    public static final String COLUMN_TEASER = "teaser";
    public static final String COLUMN_PUBLISHED_DATE = "publishedDate";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_AUTHORS = "authors";
    public static final String COLUMN_IMAGES = "images";
    public static final String COLUMN_FREE = "free";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_VIDEO_COUNT = "videoCount";
    public static final String COLUMN_ACTIVE = "active";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CAN_DOWNLOAD = "canDownload";
    public static final String COLUMN_V = "v";
    public static final String COLUMN_UPDATED_AT = "updatedAt";
    public static final String COLUMN_ITEMS = "items";
    public static final String COLUMN_CHILDREN = "children";
    public static final String COLUMN_TEASER_INFO = "teaserInfo";
    public static final String COLUMN_URL_PATH = "urlPath";


    public static final String CREATE_TABLE_STATEMENT = "create table "
            + TABLE_NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_MONGOID + " text not null, "
            + COLUMN_TITLE +" text not null, "
            + COLUMN_SUBTITLE + " text, "
            + COLUMN_PRODUCT_URL + " text,"
            + COLUMN_EAN + " text,"
            + COLUMN_PRICE + " real,"
            + COLUMN_DESCRIPTION + " text,"
            + COLUMN_DURATION + " integer,"
            + COLUMN_OBJECTIVES + " text,"
            + COLUMN_PREREQUISITES + " text,"
            + COLUMN_QCM + " text,"
            + COLUMN_TEASER_TEXT + " text,"
            + COLUMN_CATEGORY + " text,"
            + COLUMN_SUBCATEGORY + " text,"
            + COLUMN_TEASER + " text,"
            + COLUMN_PUBLISHED_DATE + " text,"
            + COLUMN_POSTER + " text,"
            + COLUMN_AUTHORS + " blob,"
            + COLUMN_IMAGES + " blob,"
            + COLUMN_FREE + " integer,"
            + COLUMN_RATING + " blob,"
            + COLUMN_VIDEO_COUNT + " integer,"
            + COLUMN_ACTIVE + " integer,"
            + COLUMN_TYPE + " text,"
            + COLUMN_CAN_DOWNLOAD + " integer,"
            + COLUMN_V + " text,"
            + COLUMN_UPDATED_AT + " text,"
            + COLUMN_ITEMS + " blob,"
            + COLUMN_CHILDREN + " blob,"
            + COLUMN_TEASER_INFO + " text, "
            + COLUMN_URL_PATH + " text"
            +");";
}
