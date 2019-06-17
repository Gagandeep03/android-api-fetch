package com.androidsample.ui.activity.fragment.task1.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.androidsample.R;
import com.androidsample.beans.MediaLiveBean;
import com.androidsample.beans.ResultLiveBean;
import com.androidsample.di.module.GlideApp;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;

import java.util.List;


public class ListItemViewModel {
    private final static String TAG = ListItemViewModel.class.getSimpleName();
    final int position;
    final ResultLiveBean trayBean;
    private final Context context;
    private final TrayItemModelListener listener;
    public ObservableField<String> tv_title = new ObservableField<>();
    public ObservableField<String> tv_writtenBy = new ObservableField<>();
    public ObservableField<String> tv_displayTime = new ObservableField<>();
    private String imageUrl;


    public ListItemViewModel(Context context, ResultLiveBean model, TrayItemModelListener listener, int position) {
        this.context = context;
        this.listener = listener;
        this.position = position;
        this.trayBean = model;

        if (model != null) {
            List<MediaLiveBean> mediaEntities = model.getMediaEntities();
            MediaLiveBean mediaEntity = mediaEntities.get(0);
            List<MediaMetadataEntity> mediametadataEntities = mediaEntity.getMediaMetadataEntities();
            for (MediaMetadataEntity entity : mediametadataEntities) {
                if (entity.getFormat().equalsIgnoreCase("Large Thumbnail")) {
                    setImageUrl(entity.getUrl());
                    break;
                }
            }

            tv_title.set(model.getTitle());
            tv_writtenBy.set(model.getByline());
            tv_displayTime.set(model.getPublishedDate());
        }

    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        GlideApp
                .with(view.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void onItemClick() {

        if (listener != null) {
            listener.onItemClick(position, trayBean);
        }
    }

    public interface TrayItemModelListener {
        void onItemClick(int position, ResultLiveBean bean);
    }

}
