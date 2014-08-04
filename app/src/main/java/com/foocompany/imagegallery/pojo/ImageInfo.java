package com.foocompany.imagegallery.pojo;

import java.util.List;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class ImageInfo {

    private int mDbRowId;

    private String mImgFilePath;

    private double mLat = -1;

    private double mLng = -1;

    private List<String> mComments;

    public int getDbRowId() {
        return mDbRowId;
    }

    public void setDbRowId(int id) {
        mDbRowId = id;
    }

    public String getImgFilePath() {
        return mImgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        mImgFilePath = imgFilePath;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        mLng = lng;
    }

    public List<String> getComments() {
        return mComments;
    }

    public void setComments(List<String> comments) {
        mComments = comments;
    }
}
