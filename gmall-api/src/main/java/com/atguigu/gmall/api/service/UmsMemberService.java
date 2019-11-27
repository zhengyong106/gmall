package com.atguigu.gmall.api.service;

import com.atguigu.gmall.api.bean.UmsMember;
import java.util.List;

public interface UmsMemberService {
    List<UmsMember> getAllMember();

    int addMember(UmsMember member);
}
