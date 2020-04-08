package com.zzsoft.zzsofttsm.vo;

import lombok.Data;
import lombok.ToString;


public class DTask {

    private String taskcontent;

    private Integer tp;

    public String getTaskcontent() {
        return taskcontent;
    }

    public void setTaskcontent(String taskcontent) {
        this.taskcontent = taskcontent;
    }

    public Integer getTp() {
        return tp;
    }

    public void setTp(Integer tp) {
        this.tp = tp;
    }

    @Override
    public String toString() {
        return "DTask{" +
                "taskcontent='" + taskcontent + '\'' +
                ", tp=" + tp +
                '}';
    }
}
