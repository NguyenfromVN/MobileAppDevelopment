package com.example.deadline0;

import java.util.Date;

public class Item {
    private int id;
    private int status;
    private String name;
    private String minCost;
    private String maxCost;
    private Date startDate;//convert before using, from String [???????] returned string is trash
    private Date endDate;//convert before using, from String   [???????] returned string is trash
    private int adults;
    private int childs;
    private boolean isPrivate;
    private String avatar;

    public Item(int id, int status, String name, String minCost, String maxCost, String startDate,
                String endDate, int adults, int childs, boolean isPrivate, String avatar) {
        this.id=id;
        this.status=status;
        this.name=name;
        this.minCost=minCost;
        this.maxCost=maxCost;
        this.startDate=new Date();
        this.endDate=new Date();
        this.adults=adults;
        this.childs=childs;
        this.isPrivate=isPrivate;
        this.avatar=avatar;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getMinCost() {
        return minCost;
    }

    public String getMaxCost() {
        return maxCost;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getAdults() {
        return adults;
    }

    public int getChilds() {
        return childs;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setId(int id) {
        this.id=id;
    }

    public void setStatus(int status) {
        this.status=status;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setMinCost(String minCost) {
        this.minCost=minCost;
    }

    public void setMaxCost(String maxCost) {
        this.maxCost=maxCost;
    }

    public void setStartDate(String startDate) {
        this.startDate=new Date();//????????????????????????????????????????????????
    }

    public void setEndDate(String endDate) {
        this.endDate=new Date();//????????????????????????????????????????????????
    }

    public void setAdults(int adults) {
        this.adults=adults;
    }

    public void setChilds(int childs) {
        this.childs=childs;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate=isPrivate;
    }

    public void setAvatar(String avatar) {
        this.avatar=avatar;
    }
}