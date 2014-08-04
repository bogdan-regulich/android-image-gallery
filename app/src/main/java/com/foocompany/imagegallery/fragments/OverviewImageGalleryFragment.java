package com.foocompany.imagegallery.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.models.OverviewImageGalleryModel;
import com.foocompany.imagegallery.pojo.ImageInfo;
import com.foocompany.imagegallery.views.OverviewImageGalleryView;

import java.io.File;
import java.io.InputStream;
import java.util.List;

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

        mModel = new OverviewImageGalleryModel(activity);
        mModel.setListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (OverviewImageGalleryView) inflater.inflate(
                R.layout.overview_image_gallery,
                container,
                false);

        mModel.fetchImagesInfoAsync();

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

    @Override
    public void onImagesInfoFetched(final File imagesPath, final List<ImageInfo> imageInfoList) {
        Activity activity = getActivity();
        if (activity == null)
            return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mView == null)
                    return;

                mView.setData(imagesPath, imageInfoList);
            }
        });
    }

    @Override
    public void onImagesCollectionChanged() {
        Activity activity = getActivity();
        if (activity == null)
            return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mView == null)
                    return;

                mView.refreshView();
            }
        });
    }

    //================Public methods============//

    public void importImage(InputStream imageInputStream) {
        mModel.importImageFromGalleryAsync(imageInputStream);
    }
}