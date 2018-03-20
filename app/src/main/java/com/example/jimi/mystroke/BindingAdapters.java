package com.example.jimi.mystroke;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by jimi on 20/03/2018.
 */

public class BindingAdapters {
    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView view, int resource) {
        view.setImageResource(resource);
    }
}
