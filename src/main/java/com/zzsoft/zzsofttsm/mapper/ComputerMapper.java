package com.zzsoft.zzsofttsm.mapper;

import com.zzsoft.zzsofttsm.entity.Computer;
import com.zzsoft.zzsofttsm.vo.ComputerVo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ComputerMapper {

    public static ComputerVo toVo(Computer computer){
        ComputerVo mapper = CGlibMapper.mapper(computer, ComputerVo.class);
        String hdCapacity = computer.getHdCapacity();

        if (!StringUtils.isBlank(hdCapacity)){
            String[] split = hdCapacity.split("\\+");
            if (split.length==0){
                mapper.setHdCapacity("");
            }else {
                mapper.setHdCapacity1(split[0]);
            }
            if (split.length>1){
                mapper.setHdCapacity2(split[1]);
            }else {
                mapper.setHdCapacity2("");
            }
        }


        return mapper;
    }

    public static List<ComputerVo> toVoList(List<Computer> computers){
        List<ComputerVo> list= new ArrayList<>();
        for (Computer computer : computers){
            ComputerVo computerVo = toVo(computer);
            list.add(computerVo);
        }
        return list;
    }

    public static Computer fromVo(ComputerVo computerVo){
        Computer mapper = CGlibMapper.mapper(computerVo, Computer.class);
        return mapper;
    }

}
