package com.androidsample.ui.baseclass;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidsample.R;
import com.androidsample.fragmentId.FragmentAvailable;
import com.androidsample.interfaces.NetworkStatus;
import com.androidsample.utils.NetworkUtils;
import com.androidsample.utils.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.DisposableSubscriber;


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements NetworkStatus {

    // Interface for On Network change
    NetworkStatus networkStatus;
    // Add the Observers for dispose at the end of life cycle
    CompositeDisposable compositeDisposable;
    private BaseActivity mActivity;
    private T mViewDataBinding;
    private V mViewModel;
    private View mRootView;
    private Bundle mbundle;
    // PublishProcessor for the use of listening the network state change first
    private PublishProcessor<Boolean> publishProcessor;
    // Receiver for listening the Connectivity change
    private BroadcastReceiver broadcastReceiver;
    // variable for network true or false.
    private boolean isNetworkAvailable;

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = getViewModel();
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
        mViewModel.onViewCreated();
        compositeDisposable = new CompositeDisposable();
        networkChangeCode();
        listenNetworkReceiver();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
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
        if (broadcastReceiver != null)
            getBaseActivity().unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
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

    public void showToast(String message) {
        if (mActivity != null)
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

    /**
     * Broadcast Receiver for network change
     */
    private void listenNetworkReceiver() {
        broadcastReceiver =
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (publishProcessor != null)
                            publishProcessor.onNext(NetworkUtils.isNetworkConnected(context));
                      /*  if(RxBusUtil.hasObservers(rxBusInjector.rxBusNetwork.provideBus()))
                        {
                            RxBusUtil.send(rxBusInjector.rxBusNetwork.provideBus(),getConnectivityStatus(context));
                        }*/
                    }
                };

        final IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mActivity.registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * function to perform Publish processor for listening the network change with only distinct network value
     * and on subscriber result it always passes unique result then before, For ex: If time true send, then it should wait for network disconnect
     * for sending false as value.
     */
    private void networkChangeCode() {


        DisposableSubscriber<Boolean> subscriber = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (networkStatus != null)
                    networkStatus.receiveNetworkStatus(aBoolean);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        // initialize the publisher
        isNetworkAvailable = NetworkUtils.isNetworkConnected(mActivity.getApplication());
        publishProcessor = PublishProcessor.create();
        networkStatus = this;
        publishProcessor
                .startWith(isNetworkAvailable)
                .distinctUntilChanged().subscribeOn(SchedulerProvider.getInstance().computation())
                .observeOn(SchedulerProvider.getInstance().io())
                .subscribe(subscriber);

        // Add subscriber here
        compositeDisposable.add(subscriber);
    }

    @Override
    public void receiveNetworkStatus(Boolean isOnline) {
        isNetworkAvailable = isOnline;
        if (!isOnline && isVisible()) {
            showNetworkSnackBar();
        }
    }

    public void showNetworkSnackBar() {
        Snackbar snackbar = Snackbar
                .make(mViewDataBinding.getRoot(), getString(R.string.error_network_failed), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

}
