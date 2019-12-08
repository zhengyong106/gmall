package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.api.bean.UmsMember;
import com.atguigu.gmall.api.service.UmsMemberService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("member")
public class MemberController {
    @Reference
    UmsMemberService umsMemberService;

    @GetMapping("all")
    @ResponseBody
    public List<UmsMember> getAllMember(){
        return umsMemberService.getAllMember();
    }

    @PostMapping
    @ResponseBody
    public void addMember(UmsMember member){
        if (StringUtils.isEmpty(member.getUsername()) || StringUtils.isEmpty(member.getPassword())) {

        }
        umsMemberService.saveMember(member);
    }
}
