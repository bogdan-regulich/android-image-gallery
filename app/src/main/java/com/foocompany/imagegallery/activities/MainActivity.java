package com.foocompany.imagegallery.activities;

import android.app.Activity;
import android.os.Bundle;

import com.foocompany.imagegallery.R;

/**
 * Created by soyuzcontent on 31.07.2014.
 */
public class MainActivity extends Activity {

    //==============Activity lifecycle==================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
