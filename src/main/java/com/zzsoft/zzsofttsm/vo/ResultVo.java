package com.zzsoft.zzsofttsm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("返回结果实体类")
public class ResultVo {

    @ApiModelProperty(value = "状态码 0成功，",example = "0")
    private Integer code;
    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty(value = "数据条数，",example = "0")
    private Integer count;
    @ApiModelProperty("数据")
    private Object data;

    public ResultVo() {

    }

    public ResultVo(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = 0;
        this.data = data;
    }

    public ResultVo(Integer code, String msg, Integer count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    @ApiModelProperty(value = "返回数据",example = "0")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }


}



