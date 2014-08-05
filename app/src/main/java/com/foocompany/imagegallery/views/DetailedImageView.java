package com.foocompany.imagegallery.views;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.interfaces.IListener;

import java.io.File;
import java.util.List;

/**
 * Created by soyuzcontent on 04.08.2014.
 */
public class DetailedImageView
        extends
            LinearLayout
        implements
            View.OnKeyListener,
            IListener<DetailedImageView.ViewListener> {

    private ImageWithLocationView mViewImageWithLocation;

    private ListView mListViewComments;

    private EditText mETextComment;

    private ArrayAdapter<String> mAdapterComments;

    //===========View listener===============//

    private ViewListener mListener;

    public static interface ViewListener {
        void onNewCommentEntered(String comment);
    }

    //============IListener=================//

    @Override
    public void setListener(ViewListener listener) {
        mListener = listener;
    }

    @Override
    public void removeListener() {
        mListener = null;
    }

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

        mListViewComments = (ListView) findViewById(R.id.list_comments);

        mViewImageWithLocation = (ImageWithLocationView) findViewById(R.id.layout_img_with_loc);

        if (mViewImageWithLocation == null) {

            mViewImageWithLocation = (ImageWithLocationView) layoutInflater.inflate(
                    R.layout.image_with_location,
                    mListViewComments,
                    false);

            mListViewComments.addHeaderView(mViewImageWithLocation);
        }

        mETextComment = (EditText) findViewById(R.id.etext_comment);
        mETextComment.setOnKeyListener(this);
    }

    //===============View.OnKeyListener===========//

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // If the event is a key-down event on the "enter" button
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            // Perform action on key press
            if (mListener != null) {
                mListener.onNewCommentEntered(mETextComment.getText().toString());
            }
            return true;
        }
        return false;
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