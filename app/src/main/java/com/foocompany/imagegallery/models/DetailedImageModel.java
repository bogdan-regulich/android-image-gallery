package com.foocompany.imagegallery.models;

import android.content.Context;
import android.location.Location;
import android.os.Environment;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.dao.DbDao;
import com.foocompany.imagegallery.interfaces.IListener;
import com.foocompany.imagegallery.pojo.ImageInfo;
import com.foocompany.imagegallery.utils.ExtStorageUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class DetailedImageModel
        extends
            ThreadPooledModel
        implements
            IListener<DetailedImageModel.ModelListener>,
            ICancelable {

    private Future mFutureFetchDetailedImgInfo;

    private final Object mSyncObjFetchDetailedImgInfo = new Object();

    private Context mContext;

    private File mImagesPath;

    private File mImageFile;

    private Location mPhotoCaptureLocation;

    private List<String> mComments;

    //=================Constructor==============//

    public DetailedImageModel(Context context) {
        mContext = context;

        if (ExtStorageUtils.isExtStorageWritable()) {
            mImagesPath = ExtStorageUtils.getExtStoragePubDir(
                    mContext.getString(R.string.images_directory_name),
                    Environment.DIRECTORY_PICTURES);
        }
    }

    //=================ModelListener===========//

    private ModelListener mListener;

    public static interface ModelListener {
        void onDetailedImageInfoFetched(File imageFile, List<String> comments, Location location);
    }

    //=================IListener===============//

    @Override
    public synchronized void setListener(ModelListener listener) {
        mListener = listener;
    }

    @Override
    public synchronized void removeListener() {
        mListener = null;
    }

    //=================ICancelable===============//

    @Override
    public void cancel() {
        if (mFutureFetchDetailedImgInfo != null) {
            mFutureFetchDetailedImgInfo.cancel(true);
        }
    }

    //================Public methods=============//

    public void fetchDetailedImageInfoAsync(final ImageInfo imageInfo) {
        if (mFutureFetchDetailedImgInfo != null) {
            mFutureFetchDetailedImgInfo.cancel(true);
        }
        mFutureFetchDetailedImgInfo = mThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                synchronized (mSyncObjFetchDetailedImgInfo) {

                    mImageFile = new File(mImagesPath, imageInfo.getImgName());

                    DbDao dbDao = DbDao.getInstance();

                    mPhotoCaptureLocation = dbDao.getPhotoCaptureLocation(
                            mContext,
                            imageInfo.getDbRowId());

                    mComments = dbDao.getComments(mContext, imageInfo.getDbRowId());

                    synchronized (mSyncObjListener) {
                        if (mListener != null) {
                            mListener.onDetailedImageInfoFetched(
                                    mImageFile,
                                    mComments,
                                    mPhotoCaptureLocation);
                        }
                    }
                }
            }
        });
    }
}
