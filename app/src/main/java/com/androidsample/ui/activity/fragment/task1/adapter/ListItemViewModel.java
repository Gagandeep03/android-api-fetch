package com.androidsample.ui.activity.fragment.task1.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.ImageView;

import com.androidsample.R;
import com.androidsample.beans.MediaEntity;
import com.androidsample.beans.MediametadataEntity;
import com.androidsample.beans.ResultsEntity;
import com.androidsample.di.module.GlideApp;

import java.util.List;


public class ListItemViewModel {
    final int position;
    private final Context context;
    private String imageUrl;
    private static int width,height;
    final ResultsEntity trayBean;
    private final String TAG = ListItemViewModel.class.getSimpleName();
    private final TrayItemModelListener listener;
    public ObservableField<String> tv_title = new ObservableField<>();
    public ObservableField<String> tv_writtenBy = new ObservableField<>();
    public ObservableField<String> tv_displayTime = new ObservableField<>();


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ListItemViewModel(Context context, ResultsEntity model, TrayItemModelListener listener, int position) {
        this.context = context;
        this.listener = listener;
        this.position = position;
        this.trayBean = model;


        List<MediaEntity> mediaEntities = model.getMedia();
        MediaEntity mediaEntity = mediaEntities.get(0);
        List<MediametadataEntity> mediametadataEntities = mediaEntity.getMediametadata();
        for (MediametadataEntity entity : mediametadataEntities) {
            if (entity.getFormat().equalsIgnoreCase("Large Thumbnail")) {
                setImageUrl(entity.getUrl());
                break;
            }
        }

        tv_title.set(model.getTitle());
        tv_writtenBy.set(model.getByline());
        tv_displayTime.set(model.getPublishedDate());

    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        GlideApp
                .with(view.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }


    public void onItemClick() {
        Log.i(TAG, "onItemClick  ");
        if (listener != null) {
            Log.d(TAG, "onItemClick  position " + position);
            listener.onItemClick(position, trayBean);
        }
    }

    public interface TrayItemModelListener {
        void onItemClick(int position, ResultsEntity bean);
    }

}
