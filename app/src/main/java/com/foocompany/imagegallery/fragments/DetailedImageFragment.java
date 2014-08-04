package com.foocompany.imagegallery.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.models.DetailedImageModel;
import com.foocompany.imagegallery.pojo.ImageInfo;
import com.foocompany.imagegallery.views.DetailedImageView;

import java.io.File;
import java.util.List;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class DetailedImageFragment
        extends
            Fragment
        implements
            DetailedImageModel.ModelListener {

    public static final String TAG = "com.foocompany.imagegallery.fragments.DetailedImageFragment.Tag";

    public static final String EXTRA_DB_IMAGE_ROW_ID = "com.foocompany.imagegallery.fragments.DetailedImageFragment.DbImgRowId";

    public static final String EXTRA_IMAGE_NAME = "com.foocompany.imagegallery.fragments.DetailedImageFragment.ImgName";

    //================Private=======================//

    DetailedImageView mView;

    DetailedImageModel mModel;


    //================Fragment lifecycle============//

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mModel = new DetailedImageModel(activity);
        mModel.setListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (DetailedImageView) inflater.inflate(
                R.layout.detailed_image_view,
                container,
                false);

        Bundle args = getArguments();
        if (args != null) {

            ImageInfo imgInfo = new ImageInfo();
            imgInfo.setDbRowId(args.getLong(EXTRA_DB_IMAGE_ROW_ID));
            imgInfo.setImgName(args.getString(EXTRA_IMAGE_NAME));

            mModel.fetchDetailedImageInfoAsync(imgInfo);
        }

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mView = null;
        mModel.cancel();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mModel.removeListener();
        mModel = null;
    }

    //================DetailedImageModel.ModelListener============//

    @Override
    public void onDetailedImageInfoFetched(final File imageFile,
                                           final List<String> comments,
                                           final Location location) {

        Activity activity = getActivity();
        if (activity == null)
            return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mView == null)
                    return;

                mView.setData(imageFile, comments, location);
            }
        });
    }

    //==================Fragment creator=========================//

    public static DetailedImageFragment createFragment(ImageInfo imageInfo) {
        DetailedImageFragment fragment = new DetailedImageFragment();
        Bundle args = fragment.getArguments();
        if (args == null) {
            args = new Bundle();
            fragment.setArguments(args);
        }
        args.putLong(EXTRA_DB_IMAGE_ROW_ID, imageInfo.getDbRowId());
        args.putString(EXTRA_IMAGE_NAME, imageInfo.getImgName());

        return fragment;
    }
}
