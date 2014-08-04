package com.foocompany.imagegallery.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Bogdan on 31-Jul-14.
 */
public class FileUriLoadImageView extends ImageView {

    //============Constructors==============//

    public FileUriLoadImageView(Context context) {
        super(context);
    }

    public FileUriLoadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FileUriLoadImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //==============Public methods==========//

    public void loadImgFromFileUri(File imgFile) {

        Picasso.with(getContext()).cancelRequest(this);

        Picasso.with(getContext())
                .load(imgFile)
                .into(this);
    }
}
