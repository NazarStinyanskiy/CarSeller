package com.company.carSeller.services;

import com.company.carSeller.entities.UserInfoEntity;
import com.company.carSeller.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private UserInfoRepository repository;

    @Autowired
    public UserInfoService(UserInfoRepository repository) {
        this.repository = repository;
    }

    public boolean validate(String login, String password, String loginReal, String passwordReal){
        if(repository.findAllByLoginAndPassword(login, password).size() == 0) return false;
        return login.equals(loginReal) && password.equals(passwordReal);
    }

    public UserInfoEntity findByLogin(String login){
        return repository.findByLogin(login).orElse(null);
    }

    public UserInfoEntity findById(int id){
        return repository.findById(id).get();
    }

    public void save(UserInfoEntity userInfoEntity) {
        repository.save(userInfoEntity);
    }
}
