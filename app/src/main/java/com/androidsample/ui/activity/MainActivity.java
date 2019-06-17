package com.androidsample.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.androidsample.BR;
import com.androidsample.R;
import com.androidsample.databinding.ActivityHomeBinding;
import com.androidsample.fragmentId.FragmentAvailable;
import com.androidsample.ui.baseclass.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.androidsample.fragmentId.FragmentAvailable.TASK_FIRST;

public class MainActivity extends BaseActivity<ActivityHomeBinding, MainActivityViewModel>
        implements HasSupportFragmentInjector, MainActivityPresenterView.View {

    ActivityHomeBinding homeBinding;
    @Inject
    MainActivityViewModel viewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeBinding = getViewDataBinding();
        viewModel.setmNavigator(this);

        // Call Login Screen default
        changeCurrentFragment(FragmentAvailable.TASK_FIRST, null, false, true);
    }

    @Override
    protected void showProgressLoading(boolean showLoading) {
        viewModel.setIsLoading(showLoading);
    }

    @Override
    public MainActivityViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @Override
    protected void changeCurrentFragment(@FragmentAvailable int newFragmentType, Bundle extras, boolean isBackStep, boolean isFragReplace) {
        viewModel.changeCurrentFragment(newFragmentType, extras, isBackStep, isFragReplace);
    }

    @Override
    protected void setCurrentFragment(@FragmentAvailable int currentFragment1) {
        viewModel.setCurrentFragment(currentFragment1);
    }


    @Override
    public void showToolBarVisibility(boolean show) {

    }

    @Override
    public String getStringResource(int stringId) {
        return null;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    /**
     * Back press commented here, so user won't go back to splash screen.
     */
    @Override
    public void onBackPressed() {
        if (viewModel.getCurrentFragment() == TASK_FIRST) {
            finish();
            System.exit(0);//  super.onBackPressed();
        } else oneStepBack();
    }

}
