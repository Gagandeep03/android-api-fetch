package com.naturenine.ui.activity.fragment.task2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.naturenine.BR;
import com.naturenine.R;
import com.naturenine.databinding.FragmentTaskSecondViewBinding;
import com.naturenine.ui.baseclass.BaseFragment;
import com.naturenine.utils.chipcloud.ChipCloud;
import com.naturenine.utils.chipcloud.ChipListener;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gagandeep on 17-06-2018.
 */
public class SecondFragment extends BaseFragment<FragmentTaskSecondViewBinding, SecondFragmentViewModel>
        implements SecondFragmentPresenterView.View {

    FragmentTaskSecondViewBinding binding;



    @Inject
    SecondFragmentViewModel viewModel;
    ChipCloud.Configure configure;


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
        viewModel.loadGridList();
        setEditBox();
    }

     @SuppressLint("CheckResult")
     private void setEditBox()
     {
         binding.editMessage.setMovementMethod(LinkMovementMethod.getInstance());
         RxTextView.textChanges(binding.editMessage).filter(new Predicate<CharSequence>() {
             @Override
             public boolean test(CharSequence charSequence) throws Exception {
                 if(charSequence.length()==0)
                 return false;
                 else return true;
             }
         }).distinct().subscribeOn(Schedulers.computation())
                 .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CharSequence>() {
             @Override
             public void accept(CharSequence charSequence) throws Exception {
                    viewModel.onTextChanges(charSequence.toString());
             }
         });
     }




    @Override
    public void updateChipCloud(final List<String> list) {
      /*  new ChipCloud.Configure()
                .chipCloud(binding.chipCloud)
                .selectedColor(Color.parseColor("#ff00cc"))
                .selectedFontColor(Color.parseColor("#ffffff"))
                .deselectedColor(Color.parseColor("#e1e1e1"))
                .deselectedFontColor(Color.parseColor("#333333"))
                .selectTransitionMS(500)
                .deselectTransitionMS(250)
                .labels(list.toArray(new String[list.size()]))
                .mode(ChipCloud.Mode.MULTI)
                .allCaps(false)
                .gravity(ChipCloud.Gravity.LEFT)
                .textSize(getResources().getDimensionPixelSize(R.dimen.default_textsize))
                .verticalSpacing(getResources().getDimensionPixelSize(R.dimen.vertical_spacing))
                .minHorizontalSpacing(getResources().getDimensionPixelSize(R.dimen.min_horizontal_spacing))
                //.typeface(Typeface.createFromAsset(getContext().getAssets(), "RobotoSlab-Regular.ttf"))
                .chipListener(new ChipListener() {
                    @Override
                    public void chipSelected(int index) {
                        //...
                        viewModel.addListValue(list.get(index));
                    }

                    @Override
                    public void chipDeselected(int index) {
                        //...
                            viewModel.removeListValue(list.get(index));
                    }
                })
                .build();*/
        configure= new ChipCloud.Configure().chipCloud(binding.chipCloud);
        configure.selectedColor(Color.parseColor("#ff00cc"));
        configure.selectedFontColor(Color.parseColor("#ffffff"));
        configure.deselectedColor(Color.parseColor("#e1e1e1"));
        configure.deselectedFontColor(Color.parseColor("#333333"));
        configure.selectTransitionMS(500);
        configure.deselectTransitionMS(250);
        configure .labels(list.toArray(new String[list.size()]));
        configure.mode(ChipCloud.Mode.MULTI);
        configure.allCaps(false);
        configure.gravity(ChipCloud.Gravity.LEFT);
        configure.textSize(getResources().getDimensionPixelSize(R.dimen.default_textsize));
        configure .verticalSpacing(getResources().getDimensionPixelSize(R.dimen.vertical_spacing));
        configure.minHorizontalSpacing(getResources().getDimensionPixelSize(R.dimen.min_horizontal_spacing));
        configure.chipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                //...
                viewModel.addListValue(list.get(index));
            }

            @Override
            public void chipDeselected(int index) {
                //...
                viewModel.removeListValue(list.get(index));
            }
        });
        configure.build();
    }


    @Override
    public String getEditBoxString()
    {
        return binding.editMessage.getText().toString();
    }

    @Override
    public int getEditBoxSelectionIndex()
    {
        return binding.editMessage.getSelectionStart();
    }

    @Override
    public void updateEditBoxText(final Spannable s) {
        if(!s.toString().
                equalsIgnoreCase(binding.editMessage.getText().toString()))
        {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.editMessage.setText(s);
                    binding.editMessage.setSelection(s.length());
                }
            });

        }

    }

    @Override
    public void deSelectChipCloud(final String s1) {
        if(configure!=null)
        {
            int index = viewModel.getListMessage().indexOf(s1);
            configure.deselectChip(index,s1);
        }

    }

    @Override
    public SecondFragmentViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_second_view;
    }

    @Override
    public void performDependencyInjection() {
        AndroidSupportInjection.inject(this);
    }
}
