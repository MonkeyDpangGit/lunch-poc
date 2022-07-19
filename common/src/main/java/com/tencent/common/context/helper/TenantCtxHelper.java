package com.tencent.common.context.helper;

import com.tencent.common.context.TenantCtx;
import com.tencent.common.model.Tenant;
import org.springframework.context.annotation.Configuration;

/**
 * TenantCtxHelper
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: TenantCtxHelper
 */
@Configuration
public class TenantCtxHelper {

    public TenantCtx buildByTenant(Tenant tenant) {

        TenantCtx tenantCtx = new TenantCtx();
        tenantCtx.setTenantId(tenant.getId());
        tenantCtx.setCustomizedHost(tenant.getCustomizedHost());
        tenantCtx.setStatus(tenant.getStatus());

        return tenantCtx;
    }
}
