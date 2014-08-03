package com.foocompany.imagegallery.models;

import android.os.Environment;

import com.foocompany.imagegallery.interfaces.IListener;
import com.foocompany.imagegallery.utils.ExtStorageUtils;
import com.foocompany.imagegallery.utils.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
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

    private final Object mSyncObjImportImg = new Object();

    //=================ModelListener===========//

    private ModelListener mListener;

    public static interface ModelListener {

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
    }

    //================Public methods=============//

    public void importImageAsync(final InputStream imageInputStream) {
        if (mFutureImportImage != null) {
            mFutureImportImage.cancel(true);
        }
        mFutureImportImage = mThreadPool.submit(new Runnable() {
            @Override
            public void run() {

                synchronized (mSyncObjImportImg) {
                    if (tryImportImage(imageInputStream)) {

                        synchronized (mSyncObjListener) {

                        }
                    }
                }
            }
        });
    }

    //================Private methods=============//

    private boolean tryImportImage(InputStream imageInputStream) {
        if (ExtStorageUtils.isExtStorageWritable()) {

            File path = ExtStorageUtils.getExtStoragePubDir(
                    "img gallery",
                    Environment.DIRECTORY_PICTURES);

            File newImgFile = new File(path, "test_img.some");

            try {

                return IOUtils.copy(imageInputStream, new FileOutputStream(newImgFile));

            } catch (FileNotFoundException ex) { ex.printStackTrace(); }
        }

        return false;
    }
}