package com.foocompany.imagegallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.views.FileUriLoadImageView;

import java.util.List;

/**
 * Created by Bogdan on 31-Jul-14.
 */
public class OverviewImagesAdapter extends BaseAdapter {

    private List<String> mImgFileUris;

    private LayoutInflater mLayoutInflater;

    //=================Constructor=============//

    public OverviewImagesAdapter(Context context, List<String> imgFileUris) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImgFileUris = imgFileUris;
    }

    //==================BaseAdapter============//

    @Override
    public int getCount() {
        return mImgFileUris.size();
    }

    @Override
    public Object getItem(int position) {
        return mImgFileUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FileUriLoadImageView imgView = null;

        if (convertView == null) {
            imgView = (FileUriLoadImageView) mLayoutInflater.inflate(
                    R.layout.file_uri_load_image_view,
                    parent,
                    false);
        } else {
            imgView = (FileUriLoadImageView) convertView;
        }

        imgView.loadImgFromFileUri(
                mImgFileUris.get(position));

        return imgView;
    }
}
