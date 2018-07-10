package com.androidsample.ui.activity;

import android.databinding.ObservableBoolean;
import android.os.Bundle;

import com.androidsample.R;
import com.androidsample.fragmentId.FragmentAvailable;
import com.androidsample.ui.activity.fragment.detailView.DetailFragment;
import com.androidsample.ui.activity.fragment.task1.FirstFragment;
import com.androidsample.utils.schedulers.SchedulerProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainActivityViewModelTest {
    @Mock
    ObservableBoolean isToolbarVisible;
    @Mock
    ObservableBoolean mIsLoading;
    @Mock
    SchedulerProvider mSchedulerProvider;
    @Mock
    FirstFragment firstFragment;
    @Mock
    DetailFragment detailFragment;

    MainActivityPresenterView.View mNavigator;
    //Field mCompositeDisposable of type CompositeDisposable - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @InjectMocks
    MainActivityViewModel mainActivityViewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // mock the interface for view
        mNavigator = mock(MainActivityPresenterView.View.class);
        mainActivityViewModel.setmNavigator(mNavigator);
    }

    @Test
    public void testLoadingObservableWithTrue() {
        MainActivityViewModel viewModel = getSplashViewModel();

        viewModel.setIsLoading(true);
        Assert.assertEquals(true, viewModel.getIsLoading().get());
    }

    @Test
    public void testLoadingObservableWithFalse() {
        MainActivityViewModel viewModel = getSplashViewModel();

        viewModel.setIsLoading(false);

        Assert.assertEquals(false, viewModel.getIsLoading().get());
    }


    @Test
    public void testSetCurrentFragmentWithFirstFragmentScreenFragmentId() {
        MainActivityViewModel viewModel = getSplashViewModel();
        try {
            viewModel.setCurrentFragment(FragmentAvailable.TASK_FIRST);
            Assert.assertEquals(FragmentAvailable.TASK_FIRST, viewModel.currentFragment);
        } catch (Exception e) {
            Assert.fail("Failed");
        }
    }

    @Test
    public void testSetCurrentFragmentWitNullId() {
        MainActivityViewModel viewModel = getSplashViewModel();
        try {
            viewModel.setCurrentFragment(-1);
            Assert.assertEquals(-1, viewModel.currentFragment);
            viewModel.onDestroyView();
        } catch (Exception e) {
            Assert.fail("Failed");
        }
    }

    @Test
    public void testChangeCurrentFragmentWithFirstFragment() throws Exception {
        MainActivityViewModel viewModel = getSplashViewModel();
        when(viewModel.getFirstFragment()).thenReturn(firstFragment);

        try {
            viewModel.changeCurrentFragment(FragmentAvailable.TASK_FIRST, null, true, true);
            viewModel.onDestroyView();


            verify(mNavigator, times(1)).navigateToWithBundle(R.id.dashboard_container
                    , firstFragment,
                    true,
                    null);
            verify(mNavigator, never()).
                    addFragmentWithBundle(R.id.dashboard_container, firstFragment, true, null);
            verify(firstFragment, never()).setArguments(any(Bundle.class));
            Assert.assertEquals(FragmentAvailable.TASK_FIRST, viewModel.currentFragment);
        } catch (Exception e) {
            Assert.fail("test Failed " + e.getMessage());
        }
    }

    @Test
    public void testChangeCurrentFragmentWithFirstFragmentWithBundle() throws Exception {
        MainActivityViewModel viewModel = getSplashViewModel();
        when(viewModel.getFirstFragment()).thenReturn(firstFragment);
        Bundle bundle = mock(Bundle.class);
        try {
            viewModel.changeCurrentFragment(FragmentAvailable.TASK_FIRST, bundle, true, true);

            verify(mNavigator, times(1)).navigateToWithBundle(R.id.dashboard_container
                    , firstFragment,
                    true,
                    bundle);
            verify(mNavigator, never()).
                    addFragmentWithBundle(R.id.dashboard_container, firstFragment, true, bundle);
            verify(firstFragment, times(1)).setArguments(bundle);
            Assert.assertEquals(FragmentAvailable.TASK_FIRST, viewModel.currentFragment);
            viewModel.onDestroyView();
        } catch (Exception e) {
            Assert.fail("test Failed " + e.getMessage());
        }
    }

    @Test
    public void testChangeCurrentFragmentWithDetailFragment() throws Exception {
        MainActivityViewModel viewModel = getSplashViewModel();
        when(viewModel.getDetailFragment()).thenReturn(detailFragment);

        try {
            viewModel.changeCurrentFragment(FragmentAvailable.DETAIL_SCREEN, null, true, true);
            viewModel.onDestroyView();


            verify(mNavigator, times(1)).navigateToWithBundle(R.id.dashboard_container
                    , detailFragment,
                    true,
                    null);
            verify(mNavigator, never()).
                    addFragmentWithBundle(R.id.dashboard_container, detailFragment, true, null);
            verify(detailFragment, never()).setArguments(any(Bundle.class));
            Assert.assertEquals(FragmentAvailable.DETAIL_SCREEN, viewModel.currentFragment);
        } catch (Exception e) {
            Assert.fail("test Failed " + e.getMessage());
        }
    }

    @Test
    public void testChangeCurrentFragmentWithDetailFragmentWithBundle() throws Exception {
        MainActivityViewModel viewModel = getSplashViewModel();
        when(viewModel.getDetailFragment()).thenReturn(detailFragment);
        Bundle bundle = mock(Bundle.class);
        try {
            viewModel.changeCurrentFragment(FragmentAvailable.DETAIL_SCREEN, bundle, true, true);

            verify(mNavigator, times(1)).navigateToWithBundle(R.id.dashboard_container
                    , detailFragment,
                    true,
                    bundle);
            verify(mNavigator, never()).
                    addFragmentWithBundle(R.id.dashboard_container, firstFragment, true, bundle);
            verify(detailFragment, times(1)).setArguments(bundle);
            Assert.assertEquals(FragmentAvailable.DETAIL_SCREEN, viewModel.currentFragment);
            viewModel.onDestroyView();
        } catch (Exception e) {
            Assert.fail("test Failed " + e.getMessage());
        }
    }


    @Test
    public void testChangeCurrentFragmentWithWrongFragmentAvailableIdPass() throws Exception {
        MainActivityViewModel viewModel = getSplashViewModel();
        when(viewModel.getFirstFragment()).thenReturn(firstFragment);
        Bundle bundle = mock(Bundle.class);
        try {
            viewModel.changeCurrentFragment(-1, bundle, true, true);
            viewModel.onDestroyView();

            verify(mNavigator, never()).showToolBarVisibility(anyBoolean());
            verify(mNavigator, never()).navigateToWithBundle(R.id.dashboard_container
                    , null,
                    true,
                    bundle);
            verify(mNavigator, never()).
                    addFragmentWithBundle(R.id.dashboard_container, null, true, bundle);

            verify(firstFragment, never()).setMbundle(any(Bundle.class));
            Assert.assertEquals(0, viewModel.currentFragment);

        } catch (Exception e) {
            Assert.fail("test Failed " + e.getMessage());
        }
    }

    @Test
    public void testChangeCurrentFragmentWithAddFragmentMethodOnFirstFormFragment() throws Exception {
        MainActivityViewModel viewModel = getSplashViewModel();
        when(viewModel.getFirstFragment()).thenReturn(firstFragment);

        try {
            // make the last param false
            viewModel.changeCurrentFragment(FragmentAvailable.TASK_FIRST, null, true, false);

            viewModel.onDestroyView();

            verify(mNavigator, never()).navigateToWithBundle(R.id.dashboard_container
                    , firstFragment,
                    true,
                    null);
            verify(mNavigator, times(1)).
                    addFragmentWithBundle(R.id.dashboard_container, firstFragment, true, null);
            verify(firstFragment, never()).setArguments(any(Bundle.class));
            Assert.assertEquals(FragmentAvailable.TASK_FIRST, viewModel.currentFragment);


        } catch (Exception e) {
            Assert.fail("test Failed " + e.getMessage());
        }
    }


    @Test
    public void testSetIsLoading() throws Exception {
        mainActivityViewModel.setIsLoading(true);
    }

    @Test
    public void testSetIsToolbarVisible() throws Exception {
        mainActivityViewModel.setIsToolbarVisible(true);
    }

    @Test
    public void testGetDetailFragment() throws Exception {
        DetailFragment result = mainActivityViewModel.getDetailFragment();

        Assert.assertNotNull(result);
    }

    @Test
    public void testGetFirstFragment() throws Exception {
        FirstFragment result = mainActivityViewModel.getFirstFragment();
        Assert.assertNotNull(result);
    }

    @Test
    public void testOnViewCreated() throws Exception {
        mainActivityViewModel.onViewCreated();
    }

    @Test
    public void testOnDestroyView() throws Exception {
        mainActivityViewModel.onDestroyView();
    }

    public MainActivityViewModel getSplashViewModel() {
        MainActivityViewModel viewModel = spy(new MainActivityViewModel());
        viewModel.setmNavigator(mNavigator);
        return viewModel;
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme