package com.zzsoft.zzsofttsm.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Download1 {

    private String gname;

    private List<DownloadTask> downloadTasks;


}
