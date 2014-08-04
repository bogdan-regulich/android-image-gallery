package com.foocompany.imagegallery.views;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.foocompany.imagegallery.R;

import java.io.File;
import java.util.List;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class DetailedImageView extends LinearLayout {

    private ImageWithLocationView mViewImageWithLocation;

    private ListView mListViewComments;

    private EditText mETextComment;

    private ArrayAdapter<String> mAdapterComments;

    //===========Constructors================//

    public DetailedImageView(Context context) {
        super(context);
    }

    public DetailedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //===============View lifecycle=============//

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater layoutInflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mViewImageWithLocation = (ImageWithLocationView) layoutInflater.inflate(
                R.layout.image_with_location,
                null,
                false);

        mListViewComments = (ListView) findViewById(R.id.list_comments);
        mListViewComments.addHeaderView(mViewImageWithLocation);

        mETextComment = (EditText) findViewById(R.id.etext_comment);
    }

    //=================Public methods=============//

    public void setData(File imageFile, List<String> comments, Location photoCaptureLocation) {
        mViewImageWithLocation.loadImageFromFile(imageFile);

        if (photoCaptureLocation != null) {
            mViewImageWithLocation.setLocation(
                    photoCaptureLocation.getLatitude(),
                    photoCaptureLocation.getLongitude());
        }

        mAdapterComments = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                comments);

        mListViewComments.setAdapter(mAdapterComments);
    }

    public void refreshView() {
        if (mAdapterComments != null) {
            mAdapterComments.notifyDataSetChanged();
        }
    }

}