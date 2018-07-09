package com.naturenine.ui.activity.fragment.HomeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.naturenine.R;
import com.naturenine.databinding.FragmentMainBinding;
import com.naturenine.enums.FragmentAvailable;
import com.naturenine.ui.baseclass.BaseFragment;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MainFragment extends BaseFragment<FragmentMainBinding, MainFragmentViewModel>
        implements View.OnClickListener, MainFragmentPresenterView.View {

    FragmentMainBinding binding;
    @Inject
    MainFragmentViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        viewModel.setmNavigator(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        viewModel.onViewCreated();
        init();
    }

    @Override
    public MainFragmentViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    private void init() {
        binding.buttonTask1.setOnClickListener(this);
        binding.buttonTask2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_task1:
                 changeCurrentFragment(FragmentAvailable.TASK_FIRST,null,true,true);
                break;
            case R.id.button_task2:
                changeCurrentFragment(FragmentAvailable.TASK_TWO,null,true,true);
                break;
        }
    }
}
