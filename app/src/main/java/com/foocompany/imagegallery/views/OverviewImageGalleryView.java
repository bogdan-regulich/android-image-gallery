package com.foocompany.imagegallery.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.foocompany.imagegallery.adapters.OverviewImagesAdapter;

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

    public void setData(List<String> imgFileUris)
    {
        setAdapter(new OverviewImagesAdapter(getContext(), imgFileUris));
    }

    public void refreshView() {
        BaseAdapter adapter = (BaseAdapter) getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
