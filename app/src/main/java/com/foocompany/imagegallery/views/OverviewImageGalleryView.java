package com.foocompany.imagegallery.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.foocompany.imagegallery.adapters.OverviewImagesAdapter;
import com.foocompany.imagegallery.pojo.ImageInfo;

import java.io.File;
import java.util.List;

/**
 * Created by Bogdan on 31-Jul-14.
 */
public class OverviewImageGalleryView extends GridView {

    //============Constructor==============//

    public OverviewImageGalleryView(Context context) {
        super(context);
    }

    public OverviewImageGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverviewImageGalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //============View lifecycle===========//

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    //============Public methods===========//

    public void setData(File imagesPath, List<ImageInfo> imagesInfoList)
    {
        setAdapter(new OverviewImagesAdapter(getContext(), imagesPath, imagesInfoList));
    }

    public void refreshView() {
        BaseAdapter adapter = (BaseAdapter) getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
