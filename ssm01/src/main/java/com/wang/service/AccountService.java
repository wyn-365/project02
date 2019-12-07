package com.wang.service;

import com.wang.domain.Account;

import java.util.List;

public interface AccountService {

    //查询所有信息
    public List<Account> findAll();

    //保存中户信息
    public void saveAccount(Account account);
}
