package com.sai.customviewpagersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> imagesList = new ArrayList<>();
        imagesList.add(R.drawable.image_1);
        imagesList.add(R.drawable.image_2);
        imagesList.add(R.drawable.image_3);
        imagesList.add(R.drawable.image_4);
        imagesList.add(R.drawable.image_5);

        CustomViewPager customViewPager = (CustomViewPager) findViewById(R.id.CustomViewPager);
        customViewPager.setAdapter(new ImagePagerAdapter(this,imagesList));

    }
}
