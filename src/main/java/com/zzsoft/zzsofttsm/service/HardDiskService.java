package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.HardDisk;
import com.zzsoft.zzsofttsm.repository.HardDiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
public class HardDiskService {

    @Autowired
    private HardDiskRepository hardDiskRepository;

    public void del(Integer id){            //删除一个hard disk
        hardDiskRepository.deleteById(id);
    }

    public HardDisk add(HardDisk hardDisk){                  //增加一个hard disk
        return  hardDiskRepository.save(hardDisk);
    }

}
