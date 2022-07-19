package com.tencent.common.dto.tenant;

import javax.validation.constraints.NotBlank;

/**
 * CreateTenantDTO
 *
 * @author torrisli
 * @date 2021/8/24
 * @Description: CreateTenantDTO
 */
public class CreateTenantDTO {

    @NotBlank
    private String uin;

    private String id;

    private String status;

    @NotBlank
    private String customizedHost;

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

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
