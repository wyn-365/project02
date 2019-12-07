package com.wang.controller;


import com.wang.domain.Account;
import com.wang.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * web层的控制器
 */

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired//自动注入类型
    private AccountService accountService;

    @RequestMapping("/findAll")
    public String  findAll(Model model){
        System.out.println("表现层：查询所有的账户。。。");
        //调用service方法
        List<Account> list = accountService.findAll();
        model.addAttribute("list",list);
        return "list";
    }


    /**
     * 测试保存
     * @param
     * @return
     */

    @RequestMapping("/save")
    public void  save(Account account,HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("表现层：查询所有的账户。。。");
        //调用service方法
        accountService.saveAccount(account);
        response.sendRedirect(request.getContextPath()+"/account/findAll");
        return ;
    }
}
