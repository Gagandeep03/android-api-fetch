package com.androidsample.ui.activity.fragment.detailView;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.androidsample.beans.MediaLiveBean;
import com.androidsample.beans.ResultLiveBean;
import com.androidsample.roomdatabase.tables.ResultsEntity;
import com.androidsample.utils.schedulers.SchedulerProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DetailFragmentViewModelTest {
    @Mock
    ObservableField<String> observer_title;
    @Mock
    ObservableField<String> observer_writtenBy;
    @Mock
    ObservableField<String> observer_date;
    @Mock
    ObservableField<String> observer_source;
    @Mock
    ObservableField<String> observer_views_count;
    @Mock
    ObservableField<String> observer_section;
    @Mock
    ObservableField<String> observer_abstract;
    @Mock
    ResultLiveBean resultsEntity;
    @Mock
    ObservableBoolean mIsLoading;
    @Mock
    SchedulerProvider mSchedulerProvider;
    @Mock
    DetailFragmentPresenterView.View mNavigator;
    //Field mCompositeDisposable of type CompositeDisposable - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @InjectMocks
    DetailFragmentViewModel detailFragmentViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetResultEntityWithValid() throws Exception {
        when(resultsEntity.getSection()).thenReturn("getSectionResponse");
        when(resultsEntity.getByline()).thenReturn("getBylineResponse");
        when(resultsEntity.getTitle()).thenReturn("getTitleResponse");
        when(resultsEntity.getAbstractX()).thenReturn("getAbstractXResponse");
        when(resultsEntity.getPublishedDate()).thenReturn("getPublishedDateResponse");
        when(resultsEntity.getSource()).thenReturn("getSourceResponse");
        when(resultsEntity.getViews()).thenReturn(0);
        when(resultsEntity.getMediaEntities()).thenReturn(Arrays.<MediaLiveBean>asList(new MediaLiveBean()));
        when(mNavigator.getAppendString(anyInt(), anyString())).thenReturn("hello");
        try {
            detailFragmentViewModel.setResultEntity(resultsEntity);
            // Assert.assertEquals("getSectionResponse",detailFragmentViewModel.observer_section.get());
            verify(observer_writtenBy, times(1)).set("getBylineResponse");
            verify(observer_section, times(1)).set("hello");
            verify(observer_date, times(1)).set("getPublishedDateResponse");
            verify(observer_title, times(1)).set("getTitleResponse");
            verify(observer_source, times(1)).set("hello");
            verify(observer_views_count, times(1)).set("hello");

        } catch (Exception ex) {
            Assert.fail("Test fail");
        }

    }

    @Test
    public void testSetResultEntityWithNullValue() {
        try {
            detailFragmentViewModel.setResultEntity(null);
            verify(observer_writtenBy, never()).set(anyString());
            verify(observer_section, never()).set(anyString());
            verify(observer_date, never()).set(anyString());
            verify(observer_title, never()).set(anyString());
            verify(observer_source, never()).set(anyString());
            verify(observer_views_count, never()).set(anyString());
        } catch (Exception ex) {
            Assert.fail("Test fail");
        }
    }

    @Test
    public void testSetResultEntityWithDefaultValues() throws Exception {
        when(resultsEntity.getSection()).thenReturn(null);
        when(resultsEntity.getByline()).thenReturn(null);
        when(resultsEntity.getTitle()).thenReturn(null);
        when(resultsEntity.getAbstractX()).thenReturn(null);
        when(resultsEntity.getPublishedDate()).thenReturn(null);
        when(resultsEntity.getSource()).thenReturn(null);
        when(resultsEntity.getViews()).thenReturn(0);
        when(resultsEntity.getMediaEntities()).thenReturn(null);
        when(mNavigator.getAppendString(anyInt(), anyString())).thenReturn(null);
        try {
            detailFragmentViewModel.setResultEntity(resultsEntity);
            verify(observer_writtenBy, never()).set(anyString());
            verify(observer_section, never()).set(anyString());
            verify(observer_date, never()).set(anyString());
            verify(observer_title, never()).set(anyString());
            verify(observer_source, never()).set(anyString());
            verify(observer_views_count, never()).set(anyString());

        } catch (Exception ex) {
            Assert.fail("Test fail");
        }

    }


    @Test
    public void testOnViewCreated() throws Exception {
        detailFragmentViewModel.onViewCreated();
    }

    @Test
    public void testOnDestroyView() throws Exception {
        detailFragmentViewModel.onDestroyView();
    }

    @Test
    public void testSetIsLoading() throws Exception {
        detailFragmentViewModel.setIsLoading(true);
    }

    private ResultsEntity getResultsEntity() {
        ResultsEntity entity = new ResultsEntity();
        entity.setSource("Source");
        entity.setByline("ByLine");
        entity.setPublishedDate("30 sept 2010");
        entity.setSection("Section");
        entity.setTitle("title");
        entity.setAdxKeywords("keyword");
        entity.setId(1);
        return entity;
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme