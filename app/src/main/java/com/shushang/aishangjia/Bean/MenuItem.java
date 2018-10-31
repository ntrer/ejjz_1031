package com.shushang.aishangjia.Bean;

public class MenuItem {
    private String name;
    private int img_id;

    public MenuItem(String name, int img_id) {
        this.name = name;
        this.img_id = img_id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }
}
