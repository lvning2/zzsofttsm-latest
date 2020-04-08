package com.zzsoft.zzsofttsm.vo;

import lombok.Data;
import lombok.ToString;


public class DPlan {

    private String plancontent;

    private Integer pp;

    public String getPlancontent() {
        return plancontent;
    }

    public void setPlancontent(String plancontent) {
        this.plancontent = plancontent;
    }

    public Integer getPp() {
        return pp;
    }

    public void setPp(Integer pp) {
        this.pp = pp;
    }

    @Override
    public String toString() {
        return "DPlan{" +
                "plancontent='" + plancontent + '\'' +
                ", pp=" + pp +
                '}';
    }
}
