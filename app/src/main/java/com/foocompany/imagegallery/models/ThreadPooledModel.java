package com.foocompany.imagegallery.models;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bogdan on 03-Aug-14.
 */
public abstract class ThreadPooledModel {

    protected final Object mSyncObjListener = this;

    protected final ExecutorService mThreadPool = Executors.newCachedThreadPool();
}
