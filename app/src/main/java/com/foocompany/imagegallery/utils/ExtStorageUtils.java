package com.foocompany.imagegallery.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Bogdan on 03-Aug-14.
 */
public final class ExtStorageUtils {

    private ExtStorageUtils() { }

    /* Checks if external storage is available for read and write */
    public static boolean isExtStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExtStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getExtStoragePubDir(String dirName, String dirType) {

        File path = new File(
                Environment.getExternalStoragePublicDirectory(dirType),
                dirName);

        if (!path.mkdirs()) {
            Log.e("ExtStorageUtils", "Directory not created");
        }

        return path;
    }
}