package com.tencent.common.dao;

import com.tencent.common.model.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * TenantRepository
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: TenantRepository
 */
@Repository
public interface TenantRepository extends MongoRepository<Tenant, String> {

    Tenant findByCustomizedHost(String host);
}
