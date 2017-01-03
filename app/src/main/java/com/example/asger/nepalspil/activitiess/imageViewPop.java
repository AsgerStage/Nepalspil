package com.example.asger.nepalspil.activitiess;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.asger.nepalspil.R;

/**
 * Created by Nicki on 22-11-2016.
 */

public class imageViewPop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text1);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.50),(int) (height*.66));

    }
}
