package com.foocompany.imagegallery.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.fragments.OverviewImageGalleryFragment;

/**
 * Created by soyuzcontent on 31.07.2014.
 */
public class MainActivity extends Activity {

    private static final int IMPORT_IMAGE_FROM_GALLERY_REQUEST_CODE = 1;

    private static final int IMPORT_IMAGE_FROM_CAMERA_REQUEST_CODE = 2;

    //==============Activity lifecycle==================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();

        OverviewImageGalleryFragment fragment =
                (OverviewImageGalleryFragment) fragmentManager.findFragmentByTag(OverviewImageGalleryFragment.TAG);

        if (fragment == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.layout_fragment_container,
                            new OverviewImageGalleryFragment(),
                            OverviewImageGalleryFragment.TAG)
                    .commit();
        }
    }

    //==============Action bar menu ====================//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_image_from_gallery: {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                startActivityForResult(intent, IMPORT_IMAGE_FROM_GALLERY_REQUEST_CODE);
                break;
            }
            case R.id.action_image_from_camera: {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, IMPORT_IMAGE_FROM_CAMERA_REQUEST_CODE);
                }
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
