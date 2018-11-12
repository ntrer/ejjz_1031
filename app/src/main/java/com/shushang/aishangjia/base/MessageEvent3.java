package com.shushang.aishangjia.base;

/**
 * Created by YD on 2018/9/18.
 */

public class MessageEvent3 {
    private String message;
    private int position;
    private double price;
    public MessageEvent3(String message,double price, int position){
        this.message=message;
        this.position=position;
        this.price=price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

