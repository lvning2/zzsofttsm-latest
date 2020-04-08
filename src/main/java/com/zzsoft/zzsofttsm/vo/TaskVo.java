package com.zzsoft.zzsofttsm.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


import java.util.Date;

@Data
@ToString
@ApiModel("任务视图")
public class TaskVo  {

    private Long id;        //id

    @ApiModelProperty("内容")
    private String content;     //任务内容

    private Integer progress;      //任务完成进度

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date time;          //当前任务时间

    private Integer gid;        //任务所属组id

    private String gname;       //组名称

    private Integer isDelay;        //是否延期      1是, 0否

    private Integer isNew;          //是否新增      1是, 0否


}
