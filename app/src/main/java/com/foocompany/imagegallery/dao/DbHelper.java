package com.foocompany.imagegallery.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public final class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "image_gallery.db";

    public static final String SQL_CREATE_TABLE_IMAGES_INFO = "CREATE TABLE IF NOT EXISTS " +
            DbContract.ImageInfoEntry.TABLE_NAME + " (" +
                  DbContract.ImageInfoEntry._ID                         + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE DEFAULT 0," +
            " " + DbContract.ImageInfoEntry.COLUMN_NAME_IMAGE_FILE_PATH + " TEXT)";

    public static final String SQL_CREATE_TABLE_IMAGES_COORDINATES = "CREATE TABLE IF NOT EXISTS " +
            DbContract.ImageCoordinatesEntry.TABLE_NAME + " (" +
            DbContract.ImageCoordinatesEntry._ID                              + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE DEFAULT 0," +
            " " + DbContract.ImageCoordinatesEntry.COLUMN_NAME_FOREIGN_KEY_ID + " INTEGER NOT NULL," +
            " " + DbContract.ImageCoordinatesEntry.COLUMN_NAME_LAT            + " REAL," +
            " " + DbContract.ImageCoordinatesEntry.COLUMN_NAME_LNG            + " REAL," +
            " " + "FOREIGN KEY (" + DbContract.ImageCoordinatesEntry.COLUMN_NAME_FOREIGN_KEY_ID + ")" +
            " " + "REFERENCES " + DbContract.ImageInfoEntry.TABLE_NAME +
            " (" + DbContract.ImageInfoEntry._ID + ") ON DELETE CASCADE)";

    public static final String SQL_CREATE_TABLE_COMMENTS = "CREATE TABLE IF NOT EXISTS " +
            DbContract.CommentEntry.TABLE_NAME + " (" +
                  DbContract.CommentEntry._ID                         + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE DEFAULT 0," +
            " " + DbContract.CommentEntry.COLUMN_NAME_FOREIGN_KEY_ID  + " INTEGER NOT NULL," +
            " " + DbContract.CommentEntry.COLUMN_NAME_TEXT + " TEXT," +
            " " + "FOREIGN KEY (" + DbContract.CommentEntry.COLUMN_NAME_FOREIGN_KEY_ID + ")" +
            " " + "REFERENCES " + DbContract.ImageInfoEntry.TABLE_NAME +
            " (" + DbContract.ImageInfoEntry._ID + ") ON DELETE CASCADE)";

    private static final String SQL_DELETE_TABLE_IMAGES_INFO = "DROP TABLE IF EXISTS "
            + DbContract.ImageInfoEntry.TABLE_NAME;

    private static final String SQL_DELETE_TABLE_IMAGES_COORDINATES = "DROP TABLE IF EXISTS "
            + DbContract.ImageCoordinatesEntry.TABLE_NAME;

    private static final String SQL_DELETE_TABLE_COMMENTS = "DROP TABLE IF EXISTS "
            + DbContract.CommentEntry.TABLE_NAME;

    //========================Constructor====================//

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //========================SQLiteOpenHelper====================//

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_IMAGES_INFO);
        db.execSQL(SQL_CREATE_TABLE_IMAGES_COORDINATES);
        db.execSQL(SQL_CREATE_TABLE_COMMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_COMMENTS);
        db.execSQL(SQL_DELETE_TABLE_IMAGES_COORDINATES);
        db.execSQL(SQL_DELETE_TABLE_IMAGES_INFO);

        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }
}
