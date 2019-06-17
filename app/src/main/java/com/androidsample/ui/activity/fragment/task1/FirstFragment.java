package com.androidsample.ui.activity.fragment.task1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.androidsample.R;
import com.androidsample.beans.ResultLiveBean;
import com.androidsample.databinding.FragmentTaskoneBinding;
import com.androidsample.fragmentId.FragmentAvailable;
import com.androidsample.roomdatabase.AppDatabase;
import com.androidsample.roomdatabase.tables.MediaEntity;
import com.androidsample.roomdatabase.tables.MediaMetadataEntity;
import com.androidsample.roomdatabase.tables.ResultsEntity;
import com.androidsample.ui.activity.fragment.task1.adapter.ResultAdapter;
import com.androidsample.ui.baseclass.BaseFragment;
import com.androidsample.utils.ConstantFile;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class FirstFragment extends BaseFragment<FragmentTaskoneBinding, FirstFragmentViewModel> implements
        FirstFragmentPresenterView.View, ResultAdapter.ItemModelListener {

    private final String TAG = FirstFragment.class
            .getSimpleName();
    @Inject
    FirstFragmentViewModel viewModel;
    @Inject
    ResultAdapter trayAdapter;
    @Inject
    AppDatabase appDatabase;

    FragmentTaskoneBinding binding;


    private Observer pagedListFinalObserver = new Observer<PagedList<ResultLiveBean>>() {
        @Override
        public void onChanged(@Nullable PagedList<ResultLiveBean> resultLiveBeans) {
            Log.d(TAG, "OnChanged Called ----");
            if (trayAdapter != null) {
                trayAdapter.submitList(resultLiveBeans);
                if ((resultLiveBeans.size() > 0) && isNetworkAvailable()) {
                    hideLoading();
                }
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        viewModel.setmNavigator(this);
        trayAdapter.setItemListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        binding.recyclerView.setAdapter(trayAdapter);
        viewModel.setResultLiveData(initEntryList());
        attachedLiveDataObserver();
        //   viewModel.loadApi(ConstantFile.API_DATE, ConstantFile.API_KEY);
    }

    private void attachedLiveDataObserver() {
        viewModel.getResultLiveData().observe(this, pagedListFinalObserver);
    }

    private void removedLiveDataObserver() {
        viewModel.getResultLiveData().removeObserver(pagedListFinalObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        setCurrentFragment(FragmentAvailable.TASK_FIRST);
    }

    @Override
    public FirstFragmentViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_taskone;
    }

    @Override
    public void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }


    @Override
    public void onItemClick(int position, ResultLiveBean bean) {
        Log.d(TAG, "Item click position " + position);
        if (position >= 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("item", bean);

            changeCurrentFragment(FragmentAvailable.DETAIL_SCREEN, bundle, true, true);
        }

    }

    @Override
    public void showToast(String message) {
        super.showToast(message);
    }

    @Override
    public String getStringIds(int stringId) {
        return getString(stringId);
    }

    @Override
    public long insertResultEntry(final ResultsEntity entity) {
        return appDatabase.resultDao().insert(entity);
    }

    @Override
    public long insertMediaEntry(final MediaEntity mediaEntity) {
        return appDatabase.mediaDao().insert(mediaEntity);
    }

    @Override
    public void insertMediaMetaList(final List<MediaMetadataEntity> mediaMetadataEntities) {
        if (appDatabase != null) {
            appDatabase.mediaMetaDao().insertAll(mediaMetadataEntities);
        }
    }

    private LiveData initEntryList() {
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setPrefetchDistance(10).setInitialLoadSizeHint(40)
                        .setPageSize(20).build();


        return (new LivePagedListBuilder(appDatabase
                .resultDao().entriesByDistinctViews(), pagedListConfig)).build();
    }

    @Override
    public void onDestroyView() {
        removedLiveDataObserver();
        super.onDestroyView();
    }

    @Override
    public void receiveNetworkStatus(Boolean isOnline) {
        if (isOnline) {
            showLoading(true);
            viewModel.loadApi(ConstantFile.API_DATE, ConstantFile.API_KEY);
        }
        // super.receiveNetworkStatus(isOnline);
    }

    @Override
    public void hideLoading() {
        showLoading(false);
    }
}
