package com.androidsample.ui.baseclass;


import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidsample.enums.FragmentAvailable;

/**
 * Created by Gagandeep on 19-09-2017.
 */

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    private BaseActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;
    private View mRootView;
    private Bundle mbundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mViewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel();
        mViewDataBinding.setVariable(getBindingVariable(),mViewModel);
        mViewDataBinding.executePendingBindings();
        mViewModel.onViewCreated();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity)
        {
            BaseActivity activity =(BaseActivity)context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }

    }




    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }




    @Override
    public void onDestroyView() {
        mViewModel.onDestroyView();
        super.onDestroyView();
    }

    public Bundle getMbundle() {
        return mbundle;
    }

    public void setMbundle(Bundle mbundle) {
        this.mbundle = mbundle;
    }



    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    public void openActivityOnTokenExpire() {
        if (mActivity != null) {
          //  mActivity.openActivityOnTokenExpire();
        }
    }

    public void showToast(String message)
    {
        if(mActivity !=null)
            mActivity.showToast(message);
    }


    public void changeCurrentFragment(@FragmentAvailable int newFragmentType, Bundle extras, boolean isBackStep, boolean isFragReplace) {
        if (mActivity != null) {
            mActivity.changeCurrentFragment(newFragmentType, extras, isBackStep, isFragReplace);
        }
    }

    public void setCurrentFragment(@FragmentAvailable int currentFragment1) {
        if (mActivity != null) {
            mActivity.setCurrentFragment(currentFragment1);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {

        return Boolean.parseBoolean(String.valueOf(ActivityCompat.checkSelfPermission(getActivity(), permission)));

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }


    /**
     * Show progressbar loading
     *
     * @param showloading
     */
    public void showLoading(boolean showloading) {
        if (mActivity != null) {
            mActivity.showProgressLoading(showloading);
        }
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

    /**
     * To add dependency Injection
     */
    public abstract void performDependencyInjection();

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }
}
