package com.ontop.challenge.transaction.infrastructure.adapter.db;


import com.ontop.challenge.transaction.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepositoryMySQL extends CrudRepository<UserEntity, Integer> {

}
