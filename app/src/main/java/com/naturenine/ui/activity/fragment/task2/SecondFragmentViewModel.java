package com.naturenine.ui.activity.fragment.task2;

import android.graphics.Color;
import android.text.Spannable;
import android.util.Log;

import com.naturenine.ui.baseclass.BaseViewModel;
import com.naturenine.utils.spannable.SimpleSpan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gagandeep on 17-06-2018.
 */
public class SecondFragmentViewModel extends BaseViewModel<SecondFragmentPresenterView.View>
implements SecondFragmentPresenterView.Presenter{

    private List<String> listSelected=null;


    private List<String> listMessage=null;
    


    public List<String> getListMessage() {
        return listMessage;
    }

   
    public SecondFragmentViewModel() {
    }

    @Override
    public void loadGridList() {
          if(listSelected==null)
              listSelected =new ArrayList<>();
          else  listSelected.clear();
        listMessage = new ArrayList<>();
        listMessage.add("123woods");
        listMessage.add("Hello world");
        listMessage.add("You're welcome");
        listMessage.add("Sincere thanks!");
        listMessage.add("Thank you,too");
        listMessage.add("Thank you for remembering me.");
        listMessage.add("You made my day.");
        listMessage.add("It's my pleasure.");
        listMessage.add("Its really made my day.");
        getmNavigator().updateChipCloud(listMessage);
    }

    @Override
    public void addListValue(String value)
    {
        if(value==null)
            return;
        if(!listSelected.contains(value))
        {
            listSelected.add(value);
             String currentMessage = getmNavigator().getEditBoxString();
            StringBuilder builder = new StringBuilder();
            int currentSelectionIndex = getmNavigator().getEditBoxSelectionIndex();

            if(currentSelectionIndex!=currentMessage.length())
            {
                String firstPart= currentMessage.substring(0,currentSelectionIndex);
                String endPart= currentMessage.substring(currentSelectionIndex,currentMessage.length());

                builder.append(firstPart).append(" ")
                        .append(value).append(" ").append(endPart);
            }else {
                builder.append(currentMessage).append(" ")
                        .append(value).append(" ");
            }
            getmNavigator().updateEditBoxText(getSpannableText(builder.toString(), null));
        }
    }

    @Override
    public void removeListValue(String value)
    {
        if(value==null)
            return;
        if(listSelected.contains(value))
        {
            listSelected.remove(value);
            String editString = getmNavigator().getEditBoxString();
            String updateString =editString.replaceAll(value,"")
                    .replaceAll("( +)"," ").trim();
           getmNavigator().updateEditBoxText(getSpannableText(updateString, null));
        }
    }

    @Override
    public void onTextChanges( String s) {

        Log.d("","onTextChange list size "+listSelected.size());
           Iterator<String> iterable = listSelected.iterator();
       int position =-1;
       int loopCount=0;
           while (iterable.hasNext())
           {
                 String s1 = iterable.next();
                 if(!s.contains(s1))
                 {
                     getmNavigator().deSelectChipCloud(s1);
                      position=loopCount;
                 }
               loopCount++;
           }
           List<String> tempList = null;
        if(position!=-1)
        {
            tempList = new ArrayList<>();
            tempList.addAll(listSelected);
            listSelected.remove(position);
        }
        getmNavigator().updateEditBoxText(getSpannableText(s,tempList));

    }

    private Spannable getSpannableText(String s, List<String> tempList)
    {
         List<String> localList = tempList==null?listSelected:tempList;
        String[] keywords = localList
                .toArray(new String[localList.size()]);
        return   new SimpleSpan().ofText(s)
                .spandColor(Color.BLACK)
                .find(keywords).setBold(true).setItalic(false).create();
    }


}
