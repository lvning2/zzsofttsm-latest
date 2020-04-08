package com.zzsoft.zzsofttsm.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TreeNode {

    private String title;

    private String id;

    private List children;


}
