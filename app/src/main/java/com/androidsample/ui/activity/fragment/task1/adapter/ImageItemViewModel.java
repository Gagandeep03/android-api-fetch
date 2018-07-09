package com.androidsample.ui.activity.fragment.task1.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.androidsample.R;
import com.androidsample.beans.ListBeanModel;
import com.androidsample.di.module.GlideApp;

/**
 * Created by Gagandeep on 16-06-2018.
 */
public class ImageItemViewModel {

    private final Context context;
    private String imageUrl;
    private static int width,height;
    public ObservableField<String> tv_number =new ObservableField<>();
    public ObservableField<Boolean> isSelected = new ObservableField<>(Boolean.FALSE);


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageItemViewModel(Context context, ListBeanModel model) {
        this.context = context;
       imageUrl = model.getImageUrl();

       setImageUrl(imageUrl);
       int local_width = getScreenWidth(context);
       width = Math.abs(local_width/3);
       height =width;
        tv_number.set(model.getName());
        isSelected.set(model.isSelected());
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {

        view.setMaxWidth(width);
        view.setMinimumWidth(width);
        view.setMaxHeight(height);
        view.setMinimumHeight(height);
        GlideApp
                .with(view.getContext())
                .load(imageUrl).override(width,height)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }


    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

}
