package com.foocompany.imagegallery.interfaces;

/**
 * Created by Bogdan on 03-Aug-14.
 */
public interface IListener<T> {
    void setListener(T listener);
    void removeListener();
}
