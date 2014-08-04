package com.foocompany.imagegallery.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
            OverviewImageGalleryModel.ModelListener,
            OverviewImageGalleryView.OnItemClickListener,
            OverviewImageGalleryView.OnItemLongClickListener {

    public static final String TAG = "com.foocompany.imagegallery.fragments.OverviewImageGalleryFragment.Tag";

    //================Private=======================//

    OverviewImageGalleryView mView;

    OverviewImageGalleryModel mModel;

    //================Fragment listener=============//

    private FragmentListener mListener;

    public static interface FragmentListener {
        void onUserImageSelected(ImageInfo imageInfo);
    }

    //================Fragment lifecycle============//

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mModel = new OverviewImageGalleryModel(activity);
        mModel.setListener(this);

        try {
            mListener = (FragmentListener) activity;
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = (OverviewImageGalleryView) inflater.inflate(
                R.layout.overview_image_gallery,
                container,
                false);

        mView.setOnItemClickListener(this);
        mView.setOnItemLongClickListener(this);

        mModel.fetchImagesInfoAsync();

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

        mListener = null;
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

    //=============OverviewImageGalleryView listeners======================//

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            ImageInfo imageInfo = (ImageInfo) parent.getAdapter().getItem(position);
            mListener.onUserImageSelected(imageInfo);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
        myAlertDialog.setTitle(getActivity().getString(R.string.dialog_title_remove));
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                mModel.removeImageAsync(position);
            }});
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) { }});
        myAlertDialog.show();

        return true;
    }

    //================Public methods============//

    public void importImageFromGallery(InputStream imageInputStream) {
        mModel.importImageFromGalleryAsync(imageInputStream);
    }

    public void importImageFromCamera(String cameraImgFileName) {
        mModel.importImageFromCameraAsync(cameraImgFileName);
    }
}