package com.naturenine.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.naturenine.BR;
import com.naturenine.R;
import com.naturenine.databinding.ActivityHomeBinding;
import com.naturenine.enums.FragmentAvailable;
import com.naturenine.ui.baseclass.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.naturenine.enums.FragmentAvailable.HOME_SCREEN;

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
        changeCurrentFragment(HOME_SCREEN, null, false, true);
    }

    @Override
    protected void showProgressLoading(boolean showLoading) {

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
        if (viewModel.getCurrentFragment() == HOME_SCREEN) {
            finish();
            System.exit(0);//  super.onBackPressed();
        }else oneStepBack();
    }

}
