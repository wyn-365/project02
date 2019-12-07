package com.wang.controller;

import com.wang.bean.Department;
import com.wang.bean.Employee;
import com.wang.bean.Msg;
import com.wang.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理和部门有关的请求
 */
@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

     //返回所有的部门信息
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts(){

        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts",list);
    }
}
