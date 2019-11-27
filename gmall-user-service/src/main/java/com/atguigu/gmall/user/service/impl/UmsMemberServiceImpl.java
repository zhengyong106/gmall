package com.atguigu.gmall.user.service.impl;

import com.atguigu.gmall.api.bean.UmsMember;
import com.atguigu.gmall.api.service.UmsMemberService;
import com.atguigu.gmall.user.mapper.UmsMemberMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Override
    public List<UmsMember> getAllMember() {
        return umsMemberMapper.selectAll();
    }

    @Override
    public int addMember(UmsMember member) {
        member.setId(null);
        member.setCreateTime(new Date());
        return umsMemberMapper.insert(member);
    }
}
