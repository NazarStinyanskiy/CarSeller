package com.company.carSeller.repositories;

import com.company.carSeller.entities.UserInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfoEntity, Integer> {
    List<UserInfoEntity> findAllByLoginAndPassword(String login, String password);
    Optional<UserInfoEntity> findByLogin(String login);
}