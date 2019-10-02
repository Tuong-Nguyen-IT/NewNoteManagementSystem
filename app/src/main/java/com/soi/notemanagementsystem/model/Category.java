package com.soi.notemanagementsystem.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Category {
    private int id;
    private String name;
    private Date createDate;

    public Category() {
    }

    public Category(String name) {
        Calendar calendar = Calendar.getInstance();
        Date currentDay = calendar.getTime();
        this.name = name;
        this.createDate = currentDay;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name, Date createDate) {
        this.name = name;
        this.createDate = createDate;
    }

    public Category(int id, String name, Date createDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateFomated(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(createDate);
    }


}
