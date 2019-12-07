package com.wang.dao;

import com.wang.domain.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账户Dao接口
 * 直接由mybatis代理完成Dao的实现类，程序员不用自己编写
 */

@Repository
public interface AccountDao {

    //查询所有信息
    @Select("select * from account")
    public List<Account> findAll();

    //保存中户信息
    @Insert("insert into account (id,name,money)values(#{id},#{name},#{money})")
    public void saveAccount(Account account);
}
