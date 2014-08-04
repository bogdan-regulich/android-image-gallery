package com.foocompany.imagegallery.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.foocompany.imagegallery.pojo.ImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public final class DbDao {

    private static final DbDao mDbDao = new DbDao();

    private DbDao() { }

    public static DbDao getInstance() {
        return mDbDao;
    }

    public synchronized void insertImageInfo(Context context, String imgFilePath, double lat, double lng) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            ContentValues cv = new ContentValues();
            cv.put(DbContract.ImageInfoEntry.COLUMN_NAME_IMAGE_FILE_PATH, imgFilePath);
            cv.put(DbContract.ImageInfoEntry.COLUMN_NAME_LAT,             lat);
            cv.put(DbContract.ImageInfoEntry.COLUMN_NAME_LNG,             lng);

            db.insert(DbContract.ImageInfoEntry.TABLE_NAME, null, cv);

        } finally {
            dbHelper.close();
        }
    }

    public synchronized List<ImageInfo> getAllImagesInfo(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;

        List<ImageInfo> imageInfoList = new ArrayList<ImageInfo>();

        try {
            // SELECT ALL.
            cursor = db.query(DbContract.ImageInfoEntry.TABLE_NAME,
                    null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                ImageInfo imgInfo = new ImageInfo();

                imgInfo.setDbRowId(cursor.getInt(0));
                imgInfo.setImgFilePath(cursor.getString(1));

                if (!cursor.isNull(2)) {
                    imgInfo.setLat(cursor.getDouble(2));
                }
                if (!cursor.isNull(3)) {
                    imgInfo.setLng(cursor.getDouble(3));
                }

                imageInfoList.add(imgInfo);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dbHelper.close();
        }

        return imageInfoList;
    }

    public synchronized void deleteImageInfoEntry(Context context, int dbRowId) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            StringBuilder sb = new StringBuilder();

            sb.append(DbContract.ImageInfoEntry._ID)
                    .append("=")
                    .append(dbRowId);

            db.delete(DbContract.ImageInfoEntry.TABLE_NAME, sb.toString(), null);

        } finally {
            dbHelper.close();
        }
    }
}
