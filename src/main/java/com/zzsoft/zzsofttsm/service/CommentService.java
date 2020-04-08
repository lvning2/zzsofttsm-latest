package com.zzsoft.zzsofttsm.service;

import com.zzsoft.zzsofttsm.entity.Comments;
import com.zzsoft.zzsofttsm.repository.CommonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommonsRepository commonsRepository;

    public List getAll(){               //获取所有的备注
       return commonsRepository.findAll();
    }

    public void addComment(Comments comments){      //添加一个备注
        commonsRepository.save(comments);
    }

    public void updateComment(Integer id,String content){           //修改备注

        Comments one = commonsRepository.getOne(id);
        one.setContent(content);
        commonsRepository.save(one);

    }

}
