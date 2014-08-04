package com.foocompany.imagegallery.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

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

    //=========================Image info===========================//

    /**
     * @return inserted row id;
     * */
    public synchronized long insertImageInfo(Context context, String imgFilePath) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            ContentValues cv = new ContentValues();
            cv.put(DbContract.ImageInfoEntry.COLUMN_NAME_IMAGE_FILE_PATH, imgFilePath);

            return db.insert(DbContract.ImageInfoEntry.TABLE_NAME, null, cv);

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
                imgInfo.setImgName(cursor.getString(1));

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

    public synchronized void deleteImageInfoEntry(Context context, long dbImgRowId) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            StringBuilder sb = new StringBuilder();

            sb.append(DbContract.ImageInfoEntry._ID)
                    .append("=")
                    .append(dbImgRowId);

            db.delete(DbContract.ImageInfoEntry.TABLE_NAME, sb.toString(), null);

        } finally {
            dbHelper.close();
        }
    }

    //=========================Photo capture location===============//

    public synchronized void insertPhotoCaptureLocation(Context context, long fkImgRowId, Location location) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            ContentValues cv = new ContentValues();
            cv.put(DbContract.ImageCoordinatesEntry.COLUMN_NAME_FOREIGN_KEY_ID, fkImgRowId);
            cv.put(DbContract.ImageCoordinatesEntry.COLUMN_NAME_LAT,            location.getLatitude());
            cv.put(DbContract.ImageCoordinatesEntry.COLUMN_NAME_LNG,            location.getLongitude());

            db.insert(DbContract.ImageCoordinatesEntry.TABLE_NAME, null, cv);

        } finally {
            dbHelper.close();
        }
    }

    public synchronized Location getPhotoCaptureLocation(Context context, long fkImgRowId) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;

        try {

            String[] tableColumns = new String[] {
                    DbContract.ImageCoordinatesEntry.COLUMN_NAME_LAT,
                    DbContract.ImageCoordinatesEntry.COLUMN_NAME_LNG
            };
            String whereClause = DbContract.ImageCoordinatesEntry.COLUMN_NAME_FOREIGN_KEY_ID + " = ?";
            String[] whereArgs = new String[] {
                    Long.toString(fkImgRowId),
            };

            cursor = db.query(DbContract.ImageCoordinatesEntry.TABLE_NAME,
                    tableColumns,
                    whereClause,
                    whereArgs,
                    null,
                    null,
                    null);

            if (cursor.getCount() == 1) {
                cursor.moveToNext();

                double lat = cursor.getDouble(0);
                double lng = cursor.getDouble(1);

                Location location = new Location("");
                location.setLatitude(lat);
                location.setLongitude(lng);

                return location;
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dbHelper.close();
        }

        return null;
    }

    //=========================Comments=============================//

    public synchronized void insertComment(Context context, long fkImgRowId, String text) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            ContentValues cv = new ContentValues();
            cv.put(DbContract.CommentEntry.COLUMN_NAME_FOREIGN_KEY_ID, fkImgRowId);
            cv.put(DbContract.CommentEntry.COLUMN_NAME_TEXT, text);

            db.insert(DbContract.CommentEntry.TABLE_NAME, null, cv);

        } finally {
            dbHelper.close();
        }
    }

    public synchronized List<String> getComments(Context context, long fkImgRowId) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;

        List<String> comments = new ArrayList<String>();

        try {
            String[] tableColumns = new String[] {
                    DbContract.CommentEntry.COLUMN_NAME_TEXT,
            };
            String whereClause = DbContract.CommentEntry.COLUMN_NAME_FOREIGN_KEY_ID + " = ?";
            String[] whereArgs = new String[] {
                    Long.toString(fkImgRowId),
            };

            cursor = db.query(DbContract.CommentEntry.TABLE_NAME,
                    tableColumns,
                    whereClause,
                    whereArgs,
                    null,
                    null,
                    null);

            while (cursor.moveToNext()) {
                comments.add(cursor.getString(0));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            dbHelper.close();
        }

        return comments;
    }
}
