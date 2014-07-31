package com.foocompany.imagegallery.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.views.OverviewImageGalleryView;

/**
 * Created by Bogdan on 31-Jul-14.
 */
public class OverviewImageGalleryFragment extends Fragment {

    public static final String TAG = "com.foocompany.imagegallery.fragments.OverviewImageGalleryFragment.Tag";

    //================Private=======================//

    OverviewImageGalleryView mView;

    //================Fragment lifecycle============//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (OverviewImageGalleryView) inflater.inflate(
                R.layout.overview_image_gallery,
                container,
                false);

        return mView;
    }
}