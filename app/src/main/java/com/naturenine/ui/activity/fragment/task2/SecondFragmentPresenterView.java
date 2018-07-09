package com.naturenine.ui.activity.fragment.task2;

import android.text.Spannable;

import java.util.List; /**
 * Created by Gagandeep on 17-06-2018.
 */
public interface SecondFragmentPresenterView {
    interface Presenter
    {

        void loadGridList();

        void addListValue(String value);

        void removeListValue(String value);

        void onTextChanges(String s);
    }

  interface View{

      void updateChipCloud(List<String> list);

      String getEditBoxString();

      int getEditBoxSelectionIndex();

      void updateEditBoxText(Spannable s);

      void deSelectChipCloud(String s1);
  }
}

