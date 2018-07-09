package com.naturenine.ui.activity.fragment.task1;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.naturenine.beans.ListBeanModel;
import com.naturenine.ui.baseclass.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gagandeep on 16-06-2018.
 */
public class FirstFragmentViewModel extends BaseViewModel<FirstFragmentPresenterView.View> implements
        FirstFragmentPresenterView.Presenter {
    private List<ListBeanModel> list;


    private float itemWidthDate;
    private int allPixelsDate;

    // Create a LiveData with a String
    private MutableLiveData<String> mCurrentName;

    public FirstFragmentViewModel() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<String>();
        }
    }

    @Override
    public MutableLiveData<String> getCurrentName() {
        return mCurrentName;
    }

    @Override
    public void updateSelectedName(String name)
    {
        mCurrentName.setValue(name);
    }


    @Override
    public void calculateRecyclerView() {
        getmNavigator().getRecyclerView().postDelayed(new Runnable() {
            @Override
            public void run() {
                //recyclerViewDate.smoothScrollToPosition(dateAdapter.getItemCount()-1);
                setItemValue();
            }
        }, 300);
        ViewTreeObserver vtoDate =  getmNavigator().getRecyclerView().getViewTreeObserver();
        vtoDate.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
               getmNavigator().getRecyclerView().getViewTreeObserver().removeOnPreDrawListener(this);
                itemWidthDate = Math.abs(getmNavigator().getRecyclerView().getWidth()/3);
                 allPixelsDate = 0;



                /* Create a LinearSnapHelper and attach the recyclerView to it. */
                final LinearSnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView( getmNavigator().getRecyclerView());

                getmNavigator().getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        allPixelsDate += dx;
                        recyclerView.post(new Runnable() {
                            public void run() {
                                setItemValue();
                            }
                        });
                    }
                });
                loadList();

                return true;
            }
        });
    }


    private void loadList()
    {
        if(list ==null)
        list = new ArrayList<>();
        else
           list.clear();
        for (int i = 0; i <25 ; i++) {
            String url="https://picsum.photos/200/300?image="+i;

           ListBeanModel listBeanModel = new ListBeanModel("Name-"+i,url);
            list.add(listBeanModel);
        }
         getmNavigator().setAdapter(list);
    }

    private void setItemValue() {
        int expectedPositionItem = Math.round(allPixelsDate / itemWidthDate);
        int setNewItem = expectedPositionItem + 1;
//        set color here
        Log.d("","Selected Item value "+setNewItem);
       getmNavigator().setAdapterSelectItem(setNewItem);
    }
}
