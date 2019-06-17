package com.androidsample.fragmentId;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({FragmentAvailable.DETAIL_SCREEN, FragmentAvailable.TASK_FIRST,
})
@Retention(RetentionPolicy.SOURCE)
public @interface FragmentAvailable {
    int TASK_FIRST = 1;
    int DETAIL_SCREEN = 2;


}