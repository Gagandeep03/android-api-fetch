package com.androidsample.enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({FragmentAvailable.HOME_SCREEN, FragmentAvailable.TASK_FIRST,
        FragmentAvailable.TASK_TWO})
@Retention(RetentionPolicy.SOURCE)
public @interface FragmentAvailable {

    int HOME_SCREEN = 1;
    int TASK_FIRST = 2;
    int TASK_TWO = 3;


}