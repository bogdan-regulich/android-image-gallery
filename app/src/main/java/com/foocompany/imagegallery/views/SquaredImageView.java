package com.foocompany.imagegallery.views;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class SquaredImageView extends FileUriLoadImageView {

    //=====================Constructors================//

    public SquaredImageView(Context context) {
        super(context);
    }

    public SquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //=====================View lifecycle===============//

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int size = 0;
        // finding min size of square;
        if (widthMeasureSpec == 0) {
            size = heightMeasureSpec;

        } else if (heightMeasureSpec == 0) {
            size = widthMeasureSpec;

        } else if (widthMeasureSpec == heightMeasureSpec) {
            size = widthMeasureSpec;

        } else if (widthMeasureSpec < heightMeasureSpec) {
            size = widthMeasureSpec;

        } else {
            size = heightMeasureSpec;
        }

        super.onMeasure(size, size);
    }
}
