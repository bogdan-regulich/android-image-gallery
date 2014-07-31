package com.foocompany.imagegallery.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;

import com.foocompany.imagegallery.R;
import com.foocompany.imagegallery.fragments.OverviewImageGalleryFragment;

/**
 * Created by soyuzcontent on 31.07.2014.
 */
public class MainActivity extends Activity {

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
}
