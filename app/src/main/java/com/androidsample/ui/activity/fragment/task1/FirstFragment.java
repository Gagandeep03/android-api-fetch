package com.androidsample.ui.activity.fragment.task1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.androidsample.R;
import com.androidsample.beans.ResultsEntity;
import com.androidsample.databinding.FragmentTaskoneBinding;
import com.androidsample.enums.FragmentAvailable;
import com.androidsample.ui.activity.fragment.task1.adapter.ListAdapter;
import com.androidsample.ui.baseclass.BaseFragment;
import com.androidsample.utils.ConstantFile;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class FirstFragment extends BaseFragment<FragmentTaskoneBinding, FirstFragmentViewModel> implements
        FirstFragmentPresenterView.View, ListAdapter.ItemModelListener {

    private final String TAG = FirstFragment.class
            .getSimpleName();
    @Inject
    FirstFragmentViewModel viewModel;
    @Inject
    ListAdapter adapter;
    FragmentTaskoneBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        viewModel.setmNavigator(this);
        adapter.setItemListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        binding.recyclerView.setAdapter(adapter);

        viewModel.loadApi(ConstantFile.API_DATE, ConstantFile.API_KEY);
    }


    @Override
    public void onResume() {
        super.onResume();
        setCurrentFragment(FragmentAvailable.TASK_FIRST);
    }

    @Override
    public void setAdapter(List<ResultsEntity> list) {
        adapter.updateList(list);
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
    public void onItemClick(int position, ResultsEntity bean) {
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
}
