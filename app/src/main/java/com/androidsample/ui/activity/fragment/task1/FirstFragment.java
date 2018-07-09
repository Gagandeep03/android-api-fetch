package com.androidsample.ui.activity.fragment.task1;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.androidsample.R;
import com.androidsample.beans.ListBeanModel;
import com.androidsample.databinding.FragmentTaskoneBinding;
import com.androidsample.ui.activity.fragment.task1.adapter.ListAdapter;
import com.androidsample.ui.baseclass.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Gagandeep on 16-06-2018.
 */
public class FirstFragment extends BaseFragment<FragmentTaskoneBinding, FirstFragmentViewModel> implements
        FirstFragmentPresenterView.View, ListAdapter.DisplayNameInterface {

    @Inject
    FirstFragmentViewModel viewModel;
    @Inject
    ListAdapter adapter;
    FragmentTaskoneBinding binding;
    // Create the observer which updates the UI.
    final Observer<String> nameObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable final String newName) {
            // Update the UI, in this case, a TextView.
            binding.tvDisplayName.setText(newName);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        viewModel.setmNavigator(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        adapter.setDisplaySelectionListener(this);
        setRecyclerView();
        addObserver();
        viewModel.calculateRecyclerView();

    }

    private void addObserver() {
        viewModel.getCurrentName().observe(this, nameObserver);
    }

    /**
     * Set RecyclerView Settings
     */
    private void setRecyclerView() {
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.shape_divider_list_item));
        binding.recyclerView.addItemDecoration(decoration);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return binding.recyclerView;
    }

    @Override
    public void setAdapter(List<ListBeanModel> list) {
        adapter.updateList(list);
        adapter.setSelectedItem(adapter.getItemCount() - 1);
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
    public void setAdapterSelectItem(int setColorDate) {
        if (adapter != null)
            adapter.setSelectedItem(setColorDate);
    }

    @Override
    public void showSelectedName(String name) {
        viewModel.updateSelectedName(name);
    }
}
