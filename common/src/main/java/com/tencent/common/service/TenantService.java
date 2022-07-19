package com.tencent.common.service;

import com.tencent.common.dao.TenantRepository;
import com.tencent.common.model.Tenant;
import com.tencent.common.utils.RequestUtils;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TenantService
 *
 * @author torrisli
 * @date 2021/8/24
 * @Description: TenantService
 */
@Component
public class TenantService {

    @Value(value = "${tenant.host.suffix:vip.identitytencent.com}")
    private String tenantHostSuffix;

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant getTenantByHost(HttpServletRequest request) {

        String host = RequestUtils.parseHostWithoutPort(request);

        if (host.endsWith(tenantHostSuffix)) {
            String hostPrefix = host.split("\\.")[0];
            Optional<Tenant> optionalTenant = tenantRepository.findById(hostPrefix);
            return optionalTenant.get();
        } else {
            return tenantRepository.findByCustomizedHost(host);
        }
    }

    public Tenant getTenantById(String id) {

        Optional<Tenant> optionalTenant = tenantRepository.findById(id);
        return optionalTenant.get();
    }

    public TenantRepository getTenantRepository() {
        return tenantRepository;
    }
}
