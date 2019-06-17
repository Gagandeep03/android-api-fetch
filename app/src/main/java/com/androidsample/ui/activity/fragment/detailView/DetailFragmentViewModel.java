package com.androidsample.ui.activity.fragment.detailView;

import android.databinding.ObservableField;

import com.androidsample.R;
import com.androidsample.beans.MediaLiveBean;
import com.androidsample.beans.ResultLiveBean;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;
import com.androidsample.ui.activity.fragment.detailView.DetailFragmentPresenterView.Presenter;
import com.androidsample.ui.activity.fragment.detailView.DetailFragmentPresenterView.View;
import com.androidsample.ui.baseclass.BaseViewModel;

import java.util.List;


public class DetailFragmentViewModel extends BaseViewModel<View> implements Presenter {

    public ObservableField<String> observer_title = new ObservableField<>();
    public ObservableField<String> observer_writtenBy = new ObservableField<>();
    public ObservableField<String> observer_date = new ObservableField<>();
    public ObservableField<String> observer_source = new ObservableField<>();
    public ObservableField<String> observer_views_count = new ObservableField<>();
    public ObservableField<String> observer_section = new ObservableField<>();
    public ObservableField<String> observer_abstract = new ObservableField<>();
    ResultLiveBean resultsEntity;


    public DetailFragmentViewModel() {
        super();
    }


    @Override
    public void setResultEntity(ResultLiveBean entity) {
        this.resultsEntity = entity;
        if (resultsEntity != null) {
            synchronized (resultsEntity) {
                setImage();
                setTitle();
                setWrittenBy();
                setPublishDate();
                setObserver_views_count();
                setObserver_source();
                setObserver_section();
                setObserver_abstract();
            }
        }

    }

    private void setImage() {
        List<MediaLiveBean> mediaEntities = resultsEntity.getMediaEntities();
        if (mediaEntities != null) {
            List<MediaMetadataEntity> mediametadataEntities = mediaEntities.get(0).getMediaMetadataEntities();
            if (mediametadataEntities != null) {
                for (MediaMetadataEntity entity : mediametadataEntities) {
                    if (entity.getFormat().equalsIgnoreCase("Large")) {
                        getmNavigator().loadImage(entity.getUrl());
                        break;
                    }
                }
            }
        }
    }

    private void setTitle() {
        String title = resultsEntity.getTitle();
        if (title != null) {
            observer_title.set(title);
        }
    }

    private void setWrittenBy() {
        String writtenBy = resultsEntity.getByline();
        if (writtenBy != null) {
            observer_writtenBy.set(writtenBy);
        }
    }

    private void setPublishDate() {
        String date = resultsEntity.getPublishedDate();
        if (date != null) {
            observer_date.set(date);
        }
    }

    private void setObserver_source() {
        String source = resultsEntity.getSource();
        if (source != null) {
            source = getmNavigator()
                    .getAppendString(R.string.source_string, source);
            observer_source.set(source);
        }
    }

    private void setObserver_views_count() {
        String count = String.valueOf(resultsEntity.getViews());
        if (count != null) {
            count = getmNavigator().getAppendString(R.string.view_count, count);
            observer_views_count.set(count);
        }
    }

    private void setObserver_section() {
        String section = resultsEntity.getSection();
        if (section != null) {
            section = getmNavigator().getAppendString(R.string.section_string, section);
            observer_section.set(section);
        }
    }

    private void setObserver_abstract() {
        String abstractString = resultsEntity.getAbstractX();
        if (abstractString != null) {
            observer_abstract.set(abstractString);
        }
    }

}
