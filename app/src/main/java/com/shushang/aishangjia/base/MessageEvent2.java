package com.shushang.aishangjia.base;

/**
 * Created by YD on 2018/9/18.
 */

public class MessageEvent2 {
    private String message;
    private int position;
    public MessageEvent2(String message,int position){
        this.message=message;
        this.position=position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

