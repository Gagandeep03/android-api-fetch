package com.androidsample.ui.baseclass;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.androidsample.fragmentId.FragmentAvailable;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements BaseFragment.Callback {


    private T mViewDataBinding;
    private V mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);

        performDataBinding();
        mViewModel.onViewCreated();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    protected void onDestroy() {
        mViewModel.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showToast(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    protected abstract void showProgressLoading(boolean showloading);


    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract
    @IdRes
    int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract void performDependencyInjection();


    /**
     * For control the fragment switching on the Activity
     *
     * @param newFragmentType
     * @param extras
     * @param isBackStep
     * @param isFragReplace
     */
    protected abstract void changeCurrentFragment(@FragmentAvailable int newFragmentType, Bundle extras, boolean isBackStep, boolean isFragReplace);

    /**
     * Get the current fragment attached with @{@link android.app.Activity}
     *
     * @param currentFragment1
     */
    protected abstract void setCurrentFragment(@FragmentAvailable int currentFragment1);


    //---------- Fragment transaction
    public void navigateToWithBundle(int container, Fragment fragment, boolean isBackStack, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.replace(container, fragment, fragment.getClass().getSimpleName());
        fts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (isBackStack)
            fts.addToBackStack(fragment.getClass().getSimpleName());
        fts.commit();
    }

    public void addFragmentWithBundle(int container, Fragment fragment, boolean isBackStack, Bundle bundle) {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fts.add(container, fragment, fragment.getClass().getSimpleName());
        fts.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (isBackStack)
            fts.addToBackStack(fragment.getClass().getSimpleName());
        fts.commit();
    }

    public void oneStepBack() {
        try {
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() >= 1) {
                fragmentManager.popBackStackImmediate();
                fts.commit();
            } else {
                supportFinishAfterTransition();
            }
        } catch (Exception e) {
            Log.d("", "exception " + e.getMessage());
        }

    }


}
