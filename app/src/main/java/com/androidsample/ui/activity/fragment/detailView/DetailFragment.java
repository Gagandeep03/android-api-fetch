package com.androidsample.ui.activity.fragment.detailView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.androidsample.BR;
import com.androidsample.R;
import com.androidsample.beans.ResultLiveBean;
import com.androidsample.databinding.FragmentDetailScreenBinding;
import com.androidsample.di.module.GlideApp;
import com.androidsample.fragmentId.FragmentAvailable;
import com.androidsample.ui.baseclass.BaseFragment;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DetailFragment extends BaseFragment<FragmentDetailScreenBinding, DetailFragmentViewModel>
        implements View.OnClickListener, DetailFragmentPresenterView.View {

    FragmentDetailScreenBinding binding;
    @Inject
    DetailFragmentViewModel viewModel;


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
    public void onResume() {
        super.onResume();
        setCurrentFragment(FragmentAvailable.DETAIL_SCREEN);
    }

    @Override
    public DetailFragmentViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;//BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail_screen;
    }

    @Override
    public void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }

    private void init() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ResultLiveBean entity = bundle.getParcelable("item");
            viewModel.setResultEntity(entity);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {

        }
    }


    @Override
    public void loadImage(String imageUrl) {
        GlideApp
                .with(getActivity())
                .load(imageUrl).transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.incToolbar.contactPicture);
    }

    @Override
    public String getAppendString(int section_string, String section) {
        return getString(section_string, section);
    }
}
