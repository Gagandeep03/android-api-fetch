package com.androidsample.ui.activity.fragment.task1;

import com.androidsample.R;
import com.androidsample.api.ApiInterface;
import com.androidsample.beans.ApiResponse;
import com.androidsample.beans.ResultsEntity;
import com.androidsample.ui.baseclass.BaseViewModel;
import com.androidsample.utils.schedulers.SchedulerProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.DisposableObserver;


public class FirstFragmentViewModel extends BaseViewModel<FirstFragmentPresenterView.View> implements
        FirstFragmentPresenterView.Presenter {
    ApiInterface apiInterface;
    private List<ResultsEntity> list;


    public FirstFragmentViewModel(SchedulerProvider schedulerProvider, ApiInterface apiInterface) {
        super(schedulerProvider);
        this.apiInterface = apiInterface;
    }

    @Override
    public void loadApi(String date, String apiKey) {
        if (date == null || date.isEmpty() || apiKey == null || apiKey.isEmpty()) {
            getmNavigator().showToast(getmNavigator().getStringIds(R.string.error_invalid_date_key));
            return;
        }
        if (list != null && list.size() > 0) {
            getmNavigator().setAdapter(list);
            return;
        }

        DisposableObserver<ApiResponse> observer = getObserver();

        apiInterface.getMostPopularList(date, apiKey).
                timeout(10, TimeUnit.SECONDS)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui()).subscribe(observer);
        getCompositeDisposable().add(observer);
    }


    private DisposableObserver<ApiResponse> getObserver() {
        return new DisposableObserver<ApiResponse>() {
            @Override
            public void onNext(ApiResponse apiResponse) {
                if (apiResponse != null && apiResponse.getStatus().equalsIgnoreCase("OK")) {
                    list = apiResponse.getResults();
                }
            }

            @Override
            public void onError(Throwable e) {
                getmNavigator().showToast(getmNavigator().getStringIds(R.string.error_api_response));
            }

            @Override
            public void onComplete() {
                if (list != null && list.size() > 0)
                    getmNavigator().setAdapter(list);
            }
        };
    }





}
