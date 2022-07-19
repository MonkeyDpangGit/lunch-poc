package com.tencent.common.context;

/**
 * TenantCtx
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: TenantCtx
 */
public class TenantCtx {

    private String tenantId;

    private String status;

    private String defaultHost;

    private String customizedHost;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefaultHost() {
        return defaultHost;
    }

    public void setDefaultHost(String defaultHost) {
        this.defaultHost = defaultHost;
    }

    public String getCustomizedHost() {
        return customizedHost;
    }

    public void setCustomizedHost(String customizedHost) {
        this.customizedHost = customizedHost;
    }
}
