package com.wang.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import com.wang.bean.Employee;
import com.wang.bean.Msg;
import com.wang.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理员工的增删改查
 */
@Controller
public class EmployeeController {

    /**
     * 采用json数据响应
     */

    //自动装配service业务逻辑组件
    @Autowired
    EmployeeService employeeService;


    /**
     * 删除单个元素 或者批量删除
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("ids")String ids){
        if (ids.contains("-")){

            //批量删除
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id的集合

            for (String string:str_ids) {
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);

            }else{
            //单个删除
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }

        return Msg.success();
    }



    //回显员工的信息
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    //判断用户名是否重复的方法
    //返回json数据
    @RequestMapping("/checkUser")
    @ResponseBody
    public Msg checkUser(@RequestParam("empName")String empName){
        boolean b = employeeService.checkUser(empName);
        System.out.println("方法执行了");
        if (b){
            return Msg.success();
        }else{
            return Msg.fail();
        }

    }


    //定义员工保存的方法
    @ResponseBody
    @RequestMapping(value="/emp",method = RequestMethod.POST)
    public Msg saveEmp(@Valid Employee employee, BindingResult result){

        if (result.hasErrors()){
            //校验失败，在前段显示后端的错误信息
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError: errors) {
                System.out.println("错误的字段名是："+fieldError.getField());
                System.out.println("错误信息是："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            //错误信息返回给浏览器
            return Msg.fail().add("errorFields",map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }

    }






    //分页的请求接受
    @RequestMapping(value = "/emps")
    //将对象直接转换为json字符串
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){
        //为了分页方便，引入mybatis的pageHelper分页组件
        //1.查询之前，调用pageHelper
        PageHelper.startPage(pn,5);

        //2.这就是一个分页查询
        List<Employee> emps = employeeService.getAll();

        //3.pageInfo包装查询出来的数据，只需要将pageinfo交给页面皆可以了
        //封装了详细的分页信息【查询出来的数据】,连续传入显示的5页
        PageInfo page = new PageInfo(emps,5);
        //不用放入到request域中，直接返回
        return Msg.success().add("pageInfo",page);
    }









    //查询员工的数据
    //@RequestMapping(value = "/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){

        //为了分页方便，引入mybatis的pageHelper分页组件
        //1.查询之前，调用pageHelper
        PageHelper.startPage(pn,5);

        //2.这就是一个分页查询
        List<Employee> emps = employeeService.getAll();

        //3.pageInfo包装查询出来的数据，只需要将pageinfo交给页面皆可以了
        //封装了详细的分页信息【查询出来的数据】,连续传入显示的5页
        PageInfo page = new PageInfo(emps,5);

        //4.把分页所有的信息对象model设置到request域中
        model.addAttribute("pageInfo",page);

        System.out.println("进入到了controller");
        //最后跳转页面
        return "list";
    }
}
