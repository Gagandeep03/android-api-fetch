package com.androidsample.ui.activity.fragment.task1;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.androidsample.R;
import com.androidsample.api.ApiInterface;
import com.androidsample.beans.ApiResponse;
import com.androidsample.beans.ResultLiveBean;
import com.androidsample.roomdatabase.tables.MediaEntity;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;
import com.androidsample.roomdatabase.tables.ResultsEntity;
import com.androidsample.ui.baseclass.BaseViewModel;
import com.androidsample.utils.schedulers.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;


public class FirstFragmentViewModel extends BaseViewModel<FirstFragmentPresenterView.View> implements
        FirstFragmentPresenterView.Presenter {
    private final static String TAG = FirstFragmentViewModel.class.getSimpleName();
    private ApiInterface apiInterface;
    private LiveData<PagedList<ResultLiveBean>> resultLiveData;


    public FirstFragmentViewModel(SchedulerProvider schedulerProvider, ApiInterface apiInterface) {
        super(schedulerProvider);
        this.apiInterface = apiInterface;
    }

    @Override
    public LiveData getResultLiveData() {
        return resultLiveData;
    }

    @Override
    public void setResultLiveData(LiveData resultLiveData) {
        this.resultLiveData = resultLiveData;
    }

    @Override
    public void loadApi(String date, String apiKey) {
        if (date == null || date.isEmpty() || apiKey == null || apiKey.isEmpty()) {
            getmNavigator().showToast(getmNavigator().getStringIds(R.string.error_invalid_date_key));
            return;
        }

        DisposableObserver<List<MediaMetadataEntity>> observer = getDbInsertObserver();

        apiInterface.getMostPopularList(date, apiKey)
                .timeout(10, TimeUnit.SECONDS).flatMap(new Function<ApiResponse, ObservableSource<List<ResultsEntity>>>() {
            @Override
            public ObservableSource<List<ResultsEntity>> apply(ApiResponse apiResponse) throws Exception {
                if (apiResponse != null) {
                    //   Log.d(TAG," apiResponse  ==== "+apiResponse.getStatus());
                    if (apiResponse.getStatus() != null && apiResponse.getStatus().equalsIgnoreCase("OK")) {
                        return Observable.just(apiResponse.getResults());
                    }

                }
                throw new Exception(getmNavigator().getStringIds(R.string.error_api_response));
                //return null;
            }
        }).flatMap(new Function<List<ResultsEntity>, ObservableSource<ResultsEntity>>() {
            @Override
            public ObservableSource<ResultsEntity> apply(List<ResultsEntity> resultsEntities) throws Exception {
                return Observable.fromIterable(resultsEntities);
            }
        }).flatMap(new Function<ResultsEntity, ObservableSource<ResultsEntity>>() {
            @Override
            public ObservableSource<ResultsEntity> apply(final ResultsEntity resultsEntity) throws Exception {
                //    Log.d(TAG, " Result Entity  ==== " + resultsEntity.getTitle());

                synchronized (resultsEntity) {
                    long id = getmNavigator().insertResultEntry(resultsEntity);
                    resultsEntity.setId(id);
                    return Observable.just(resultsEntity);
                }
            }
        }).filter(new Predicate<ResultsEntity>() {
            @Override
            public boolean test(ResultsEntity resultsEntity) throws Exception {
                return resultsEntity.getId() > 0;
            }
        }).distinct().flatMap(new Function<ResultsEntity, ObservableSource<List<MediaEntity>>>() {
            @Override
            public ObservableSource<List<MediaEntity>> apply(ResultsEntity entity) throws Exception {
                // Update result Id;
                return getMediaList(entity);
            }
        }).flatMap(new Function<List<MediaEntity>, ObservableSource<MediaEntity>>() {
            @Override
            public ObservableSource<MediaEntity> apply(List<MediaEntity> mediaEntities) throws Exception {
                return Observable.fromIterable(mediaEntities);// for each single item emit
            }
        }).flatMap(new Function<MediaEntity, ObservableSource<MediaEntity>>() {
            @Override
            public ObservableSource<MediaEntity> apply(MediaEntity mediaEntity) throws Exception {
                //  Update the media id;
                //     Log.d(TAG, " Media Entity  ==== " + mediaEntity.getType());
                synchronized (mediaEntity) {
                    long mediaId = getmNavigator().insertMediaEntry(mediaEntity);
                    mediaEntity.setMediaId(mediaId);
                    return Observable.just(mediaEntity);
                }
            }
        }).flatMap(new Function<MediaEntity, ObservableSource<List<MediaMetadataEntity>>>() {
            @Override
            public ObservableSource<List<MediaMetadataEntity>> apply(MediaEntity mediaEntity) throws Exception {

                return getUpdateMediaDataEntity(mediaEntity);
            }
        }).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io()).subscribe(observer);

        getCompositeDisposable().add(observer);
    }

    private DisposableObserver<List<MediaMetadataEntity>> getDbInsertObserver() {
        return new DisposableObserver<List<MediaMetadataEntity>>() {
            @Override
            public void onNext(List<MediaMetadataEntity> mediaMetadataEntities) {
                if (mediaMetadataEntities != null) {
                    //    Log.d(TAG, "Database update successfully--> " + mediaMetadataEntities.size());
                    getmNavigator().insertMediaMetaList(mediaMetadataEntities);

                }
            }

            @Override
            public void onError(Throwable e) {
                // Log.e(TAG, "On Error " + e.getCause());
                getmNavigator().showToast(getmNavigator().getStringIds(R.string.error_api_response));
                getmNavigator().hideLoading();
            }

            @Override
            public void onComplete() {
                getmNavigator().hideLoading();
            }
        };
    }

    private Observable<List<MediaEntity>> getMediaList(final ResultsEntity resultsEntity) {
        return Observable.create(new ObservableOnSubscribe<List<MediaEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MediaEntity>> emitter) throws Exception {
                List<MediaEntity> list = new ArrayList<>(resultsEntity.getMedia().size());
                long resultId = resultsEntity.getId();
                //    Log.d(TAG, "result Id --- > " + resultId);
                for (MediaEntity mediaEntity : resultsEntity.getMedia()) {
                    mediaEntity.setResultId(resultId);
                    list.add(mediaEntity);
                }
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(getSchedulerProvider().computation());
    }

    private Observable<List<MediaMetadataEntity>> getUpdateMediaDataEntity(final MediaEntity mediaEntity) {
        return Observable.create(new ObservableOnSubscribe<List<MediaMetadataEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MediaMetadataEntity>> emitter) throws Exception {
                List<MediaMetadataEntity> list = new ArrayList<>(mediaEntity.getMediametadata().size());
                long mediaId = mediaEntity.getMediaId();
                //  Log.d(TAG, "media Id --- > " + mediaId);
                for (MediaMetadataEntity mediametadataEntity : mediaEntity.getMediametadata()) {
                    mediametadataEntity.setMediaId(mediaId);
                    list.add(mediametadataEntity);
                }
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).subscribeOn(getSchedulerProvider().computation());
    }

}
