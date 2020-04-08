package com.zzsoft.zzsofttsm.vo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;



public class Downloads {


    private String gname;

    private List<DTask> task;

    private List<DPlan> plan;

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public List getTask() {
        return task;
    }

    public void setTask(List task) {
        this.task = task;
    }

    public List getPlan() {
        return plan;
    }

    public void setPlan(List plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Downloads{" +
                "gname='" + gname + '\'' +
                ", task=" + task +
                ", plan=" + plan +
                '}';
    }
}
