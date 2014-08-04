package com.foocompany.imagegallery.dao;

import android.provider.BaseColumns;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public final class DbContract {

    private DbContract() { }

    public static final class ImageInfoEntry implements BaseColumns {

        public static final String TABLE_NAME = "IMAGES_INFO";

        public static final String COLUMN_NAME_IMAGE_FILE_PATH = "IMG_FILE_PATH";
        public static final String COLUMN_NAME_LAT             = "LAT";
        public static final String COLUMN_NAME_LNG             = "LNG";
    }

    public static final class CommentEntry implements BaseColumns {

        public static final String TABLE_NAME = "COMMENTS";

        public static final String COLUMN_NAME_FOREIGN_KEY_ID = "FOREIGN_KEY_ID";
        public static final String COLUMN_NAME_TEXT           = "TEXT";
    }
}