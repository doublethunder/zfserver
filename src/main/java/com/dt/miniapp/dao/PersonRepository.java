package com.dt.miniapp.dao;

import com.dt.miniapp.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chenlei
 * @description
 * @date 2019/3/9 11:08 PM
 */
public interface PersonRepository extends MongoRepository<Person, String> {
    Person findDistinctByFirstName(String firstName);
}
