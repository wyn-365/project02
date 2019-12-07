package com.wang.test;

import com.wang.dao.AccountDao;
import com.wang.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class TestMybatis {

    /**
     * 测试查询
     * @throws Exception
     */
    @Test
    public void run1() throws Exception{
        Account account = new Account();
        account.setId(6);
        account.setName("倾杯");
        account.setMoney(23.6);

        //加载配置文件
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建sqlsessionfactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //创阿金sqlsession
        SqlSession session = factory.openSession();
        //获取代理对象
        AccountDao dao = session.getMapper(AccountDao.class);
        //保存
        dao.saveAccount(account);

        //提交事务
        session.commit();
        session.close();
        is.close();

    }


    /**
     * 测试保存
     * @throws Exception
     */
    @Test
    public void run2() throws Exception{
        //加载配置文件
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建sqlsessionfactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        //创阿金sqlsession
        SqlSession session = factory.openSession();
        //获取代理对象
        AccountDao dao = session.getMapper(AccountDao.class);
        //查询所有
        List<Account> lists = dao.findAll();
        for (Account list: lists){
            System.out.println(list);
        }
        //关闭资源
        session.close();
        is.close();

    }
}
