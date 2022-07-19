package com.tencent.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Tenant
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: Tenant
 */
@Document
public class Tenant extends BaseModel {

    @Id
    private String id;

    private String status;

    @Indexed(unique = true)
    private String customizedHost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomizedHost() {
        return customizedHost;
    }

    public void setCustomizedHost(String customizedHost) {
        this.customizedHost = customizedHost;
    }
}
