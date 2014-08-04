package com.foocompany.imagegallery.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.foocompany.imagegallery.pojo.ImageInfo;
import com.foocompany.imagegallery.views.FileUriLoadImageView;
import com.foocompany.imagegallery.views.SquaredImageView;

import java.io.File;
import java.util.List;

/**
 * Created by Bogdan on 31-Jul-14.
 */
public class OverviewImagesAdapter extends BaseAdapter {

    private File mImagesPath;

    private List<ImageInfo> mImagesInfoList;

    private Context mContext;

    //=================Constructor=============//

    public OverviewImagesAdapter(Context context, File imagesPath, List<ImageInfo> imagesInfoList) {
        mContext = context;
        mImagesPath = imagesPath;
        mImagesInfoList = imagesInfoList;
    }

    //==================BaseAdapter============//

    @Override
    public int getCount() {
        return mImagesInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mImagesInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FileUriLoadImageView imgView;

        if (convertView == null) {
            imgView = new SquaredImageView(mContext);
        } else {
            imgView = (SquaredImageView) convertView;
        }

        File imgFile = new File(mImagesPath, mImagesInfoList.get(position).getImgName());

        imgView.loadImageFromFile(imgFile, true);

        return imgView;
    }
}
