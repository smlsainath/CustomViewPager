package com.sai.customviewpagersample;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by SAI on 22/11/16.
 */

public class ImagePagerAdapter extends PagerAdapter {


    ArrayList<Integer> imagesList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public ImagePagerAdapter(Context context, ArrayList<Integer> imagesList) {

        mContext = context;
        this.imagesList = imagesList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imagesList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

         ImageView imageView = (ImageView) itemView.findViewById(R.id.productDetailsImageImageView);


                Glide
                        .with(mContext)
                        .load(imagesList.get(position))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.image_error)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}