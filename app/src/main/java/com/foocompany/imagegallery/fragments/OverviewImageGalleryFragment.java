package com.foocompany.imagegallery.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.models.OverviewImageGalleryModel;
import com.foocompany.imagegallery.views.OverviewImageGalleryView;

import java.io.InputStream;

/**
 * Created by Bogdan on 31-Jul-14.
 */
public class OverviewImageGalleryFragment
        extends
            Fragment
        implements
            OverviewImageGalleryModel.ModelListener {

    public static final String TAG = "com.foocompany.imagegallery.fragments.OverviewImageGalleryFragment.Tag";

    //================Private=======================//

    OverviewImageGalleryView mView;

    OverviewImageGalleryModel mModel;

    //================Fragment lifecycle============//

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mModel = new OverviewImageGalleryModel();
        mModel.setListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (OverviewImageGalleryView) inflater.inflate(
                R.layout.overview_image_gallery,
                container,
                false);

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mView = null;

        mModel.cancel();
        mModel.removeListener();
        mModel = null;
    }

    //================OverviewImageGalleryModel.ModelListener============//

    //================Public methods============//

    public void importImage(InputStream imageInputStream) {
        mModel.importImageAsync(imageInputStream);
    }
}