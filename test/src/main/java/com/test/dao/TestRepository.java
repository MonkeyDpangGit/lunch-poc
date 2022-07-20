package com.test.dao;

import com.test.model.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * TestRepository
 *
 * @author torrisli
 * @date 2021/8/19
 * @Description: TestRepository
 */
@Repository
public interface TestRepository extends MongoRepository<Test, String> {

    Test findByName(String name);
}
