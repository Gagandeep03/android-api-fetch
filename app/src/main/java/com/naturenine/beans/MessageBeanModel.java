package com.naturenine.beans;

/**
 * Created by Gagandeep on 18-06-2018.
 */
public class MessageBeanModel {
    private String message;
    private boolean isChecked;


    public MessageBeanModel(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
