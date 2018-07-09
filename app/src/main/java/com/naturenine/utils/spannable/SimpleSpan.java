package com.naturenine.utils.spannable;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;


/**
 * Created by Gagandeep on 18-06-2018.
 */
public class SimpleSpan {

    private String textFull;
    private String[] textToFind;
    private int[] color;
    private boolean isBold = false;
    private boolean isItalic = false;

    private int colorDefault = Color.parseColor("#000000");

    public SimpleSpan() {
    }

    public SimpleSpan ofText(String textFull) {
        this.textFull = textFull;
        return this;
    }

    public SimpleSpan find(String... textToFind) {
        this.textToFind = textToFind;
        return this;
    }

    public SimpleSpan spandColor(int... color) {
        this.color = color;
        return this;
    }

    public SimpleSpan setBold(boolean isBold) {
        this.isBold = isBold;
        return this;
    }

    public SimpleSpan setItalic(boolean isItalic) {
        this.isItalic = isItalic;
        return this;
    }


    public Spannable create() {
        Spannable wordtoSpan = new SpannableString(this.textFull);


        for( int i = 0; i < this.textToFind.length; ++i) {
            int start = this.textFull.indexOf(this.textToFind[i]);
            int end = start + this.textToFind[i].length();

            if(!textFull.contains(textToFind[i]))
            {
              //  wordtoSpan.setSpan( new StyleSpan(1), 0, textFull.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
              continue;
            }

            wordtoSpan.setSpan(new ForegroundColorSpan(this.color.length != this.textToFind.length ? this.colorDefault : this.color[i]), start, end, 33);


            StyleSpan style = null;

            if (this.isBold) {
                style = new StyleSpan(this.isItalic ? 3 : 1);
            } else if (this.isItalic) {
                style = new StyleSpan(2);
            } else {
                style = new StyleSpan(0);
            }

            wordtoSpan.setSpan(style, start, end, 33);
        }

        return wordtoSpan;
    }
}
