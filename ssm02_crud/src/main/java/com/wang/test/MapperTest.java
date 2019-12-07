package com.wang.test;

import com.wang.bean.Department;
import com.wang.bean.Employee;
import com.wang.dao.DepartmentMapper;
import com.wang.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 测试dao层
 */
/**
 * 测试departmentMapper
 * 建议使用spring的单元测试  可已自动注入
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD(){
        /*//1、创键IOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2、IOC容器中取出mapper
        ioc.getBean(DepartmentMapper.class);*/

        System.out.println(departmentMapper);

        //1.插入部门
        /*departmentMapper.insertSelective(new Department(null,"开发部"));
        departmentMapper.insertSelective(new Department(null,"测试部"));*/

        //2、生成一些员工数  插入员工
        //employeeMapper.insertSelective(new Employee(null,"美人","M","meiren@123.com",1));

        //3.批量的插入员工,批量操作的sqlSession

        /*for (){
            employeeMapper.insertSelective(new Employee(null, ,"M","meiren@123.com",1));
        }
        */
        /*EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i=0; i<100; i++){

            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null,uid,"M",uid+"@163.com",1));

        }
        System.out.println("批量操作结束");*/


    }
}
