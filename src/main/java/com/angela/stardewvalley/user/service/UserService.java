package com.angela.stardewvalley.user.service;

import com.angela.stardewvalley.user.repo.UserRepo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Resource
    private UserRepo userRepo;
    /*
    登录
     */
    public boolean login(String username,String password){
        return userRepo.findByUsernameAndPassword(username,password)!=null;
    }

    /*
    注册
     */
    @Transactional
    public boolean register(String username,String password){
        return userRepo.register(username,password)!=0;
    }
}
