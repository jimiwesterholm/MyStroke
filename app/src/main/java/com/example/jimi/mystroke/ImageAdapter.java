package com.example.jimi.mystroke;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.ExerciseImage;

import java.util.List;

/**
 * Created by jimi on 14/03/2018.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<ExerciseImage> images;

    public ImageAdapter(Context context, List<ExerciseImage> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }
        Bitmap bitmap = images.get(i).getBitmap();
        if (bitmap == null) {
            Glide
                    .with(context)
                    .load(images.get(i).getAddress())
                    .into(imageView);
        } else {
            Glide
                    .with(context)
                    .load(bitmap)
                    .into(imageView);
        }
        return imageView;
    }
}
