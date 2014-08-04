package com.foocompany.imagegallery.models;

import android.content.Context;
import android.location.Location;
import android.os.Environment;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.dao.DbDao;
import com.foocompany.imagegallery.interfaces.IListener;
import com.foocompany.imagegallery.pojo.ImageInfo;
import com.foocompany.imagegallery.utils.ExtStorageUtils;
import com.foocompany.imagegallery.utils.IOUtils;
import com.foocompany.imagegallery.utils.LocationManagerUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * Created by Bogdan on 03-Aug-14.
 */
public class OverviewImageGalleryModel
        extends
            ThreadPooledModel
        implements
            IListener<OverviewImageGalleryModel.ModelListener>,
            ICancelable {

    private Future mFutureImportImage;

    private Future mFutureFetchImagesInfo;

    private Future mFutureRemoveImageInfo;

    private final Object mSyncObjImportImg = new Object();

    private final Object mSyncObjFetchImagesInfo = new Object();

    private final Object mSyncObjImgInfoList = new Object();

    private Context mContext;

    private File mImagesPath;

    private List<ImageInfo> mImageInfoList;

    //=================Constructor==============//

    public OverviewImageGalleryModel(Context context) {
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
        void onImagesInfoFetched(File imagesPath, List<ImageInfo> imageInfoList);
        void onImagesCollectionChanged();
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
        if (mFutureImportImage != null) {
            mFutureImportImage.cancel(true);
        }
        if (mFutureFetchImagesInfo != null) {
            mFutureFetchImagesInfo.cancel(true);
        }
        if (mFutureRemoveImageInfo != null) {
            mFutureRemoveImageInfo.cancel(true);
        }
    }

    //================Public methods=============//

    public void importImageFromGalleryAsync(final InputStream imageInputStream) {
        if (mFutureImportImage != null) {
            mFutureImportImage.cancel(true);
        }
        mFutureImportImage = mThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                synchronized (mSyncObjImportImg) {
                    ImageInfo imageInfo = tryImportImageFromGallery(imageInputStream);
                    if (imageInfo != null) {
                        synchronized (mSyncObjImgInfoList) {
                            mImageInfoList.add(imageInfo);
                        }
                        synchronized (mSyncObjListener) {
                            if (mListener != null) {
                                mListener.onImagesCollectionChanged();
                            }
                        }
                    }
                }
            }
        });
    }

    public void importImageFromCameraAsync(final String cameraImgFileName) {
        if (mFutureImportImage != null) {
            mFutureImportImage.cancel(true);
        }
        mFutureImportImage = mThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                synchronized (mSyncObjImportImg) {

                    DbDao dbDao = DbDao.getInstance();

                    long dbImgRowId = dbDao.insertImageInfo(mContext, cameraImgFileName);

                    // Fetching photo capture location.
                    Location location =
                            LocationManagerUtils.getLastKnownLocationFromBestAvailableProvider(mContext);

                    if (location != null) {
                        dbDao.insertPhotoCaptureLocation(mContext, dbImgRowId, location);
                    }

                    ImageInfo imageInfo = new ImageInfo();
                    imageInfo.setDbRowId(dbImgRowId);
                    imageInfo.setImgName(cameraImgFileName);

                    synchronized (mSyncObjImgInfoList) {
                        mImageInfoList.add(imageInfo);
                    }
                    synchronized (mSyncObjListener) {
                        if (mListener != null) {
                            mListener.onImagesCollectionChanged();
                        }
                    }
                }
            }
        });
    }

    public void fetchImagesInfoAsync() {
        if (mFutureFetchImagesInfo != null) {
            mFutureFetchImagesInfo.cancel(true);
        }
        mFutureFetchImagesInfo = mThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                synchronized (mSyncObjFetchImagesInfo) {

                    List<ImageInfo> imgInfoList = DbDao.getInstance().getAllImagesInfo(mContext);

                    synchronized (mSyncObjImgInfoList) {

                        mImageInfoList = imgInfoList;

                        synchronized (mSyncObjListener) {
                            if (mListener != null) {
                                mListener.onImagesInfoFetched(mImagesPath, mImageInfoList);
                            }
                        }
                    }
                }
            }
        });
    }

    public void removeImageAsync(final int position) {
        if (mFutureRemoveImageInfo != null) {
            mFutureRemoveImageInfo.cancel(true);
        }
        mFutureRemoveImageInfo = mThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                synchronized (mSyncObjImgInfoList) {
                    ImageInfo imageInfo = mImageInfoList.get(position);

                    DbDao.getInstance().deleteImageInfoEntry(mContext, imageInfo.getDbRowId());
                    new File(mImagesPath, imageInfo.getImgName()).delete();
                    mImageInfoList.remove(position);
                }
                synchronized (mSyncObjListener) {
                    if (mListener != null) {
                        mListener.onImagesCollectionChanged();
                    }
                }
            }
        });
    }

    //================Private methods=============//

    /**
     * @return copied image info or null if image copy was not successful;
     * */
    private ImageInfo tryImportImageFromGallery(InputStream imageInputStream) {
        if (ExtStorageUtils.isExtStorageWritable()) {

            String imgName = UUID.randomUUID().toString();
            File newImgFile = new File(mImagesPath, imgName);

            try {

                boolean isImageCopySuccessful =
                        IOUtils.copy(imageInputStream, new FileOutputStream(newImgFile));

                if (isImageCopySuccessful) {
                    // Saving copied image info to the database.
                    long dbRowId = DbDao.getInstance().insertImageInfo(mContext, imgName);

                    ImageInfo imgInfo = new ImageInfo();
                    imgInfo.setDbRowId(dbRowId);
                    imgInfo.setImgName(imgName);

                    return imgInfo;
                }
            } catch (FileNotFoundException ex) { ex.printStackTrace(); }
        }

        return null;
    }
}