package com.foocompany.imagegallery.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.foocompany.imagegallery.R;

import java.io.File;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class ImageWithLocationView extends FrameLayout {

    private FileUriLoadImageView mImageView;

    private TextView mTextLocation;

    //===============Constructors==============//

    public ImageWithLocationView(Context context) {
        super(context);
    }

    public ImageWithLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageWithLocationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //===============View lifecycle============//

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mImageView = (FileUriLoadImageView) findViewById(R.id.img_image);
        mTextLocation = (TextView) findViewById(R.id.text_location);
    }

    //===============Public methods==============//

    public void setLocation(double lat, double lng) {
        StringBuilder sb = new StringBuilder();
        sb.append("lat: ")
                .append(lat)
                .append("\n")
                .append("lng: ")
                .append(lng);

        mTextLocation.setText(sb.toString());
    }

    public void loadImageFromFile(File imageFile) {
        mImageView.loadImageFromFile(imageFile, false);
    }
}
