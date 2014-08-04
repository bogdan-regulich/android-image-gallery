package com.foocompany.imagegallery.pojo;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class ImageInfo {

    private long mDbRowId;

    private String mImgName;

    public long getDbRowId() {
        return mDbRowId;
    }

    public void setDbRowId(long id) {
        mDbRowId = id;
    }

    public String getImgName() {
        return mImgName;
    }

    public void setImgName(String imgName) {
        mImgName = imgName;
    }
}
