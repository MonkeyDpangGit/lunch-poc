package com.tencent.common.executor.impl.tenant;

import com.tencent.common.dto.tenant.CreateTenantDTO;
import com.tencent.common.executor.IExecutor;
import com.tencent.common.model.Tenant;
import com.tencent.common.service.TenantService;
import com.tencent.common.vo.tenant.CreateTenantVO;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CreateTenanatExecutor
 *
 * @author torrisli
 * @date 2021/8/24
 * @Description: CreateTenanatExecutor
 */
@Component("CreateTenant")
public class CreateTenantExecutor implements IExecutor<CreateTenantDTO, CreateTenantVO> {

    @Autowired
    private TenantService tenantService;

    @Override
    public CreateTenantVO execute(CreateTenantDTO createTenantDTO) throws Exception {

        String id = createTenantDTO.getId();
        if (StringUtils.isNotBlank(id) && tenantService.getTenantRepository().existsById(id)) {
            throw new IllegalArgumentException("id exists");
        }

        Tenant tenant = new Tenant();
        Date date = new Date();
        tenant.setCreatedDate(date);
        tenant.setLastModifiedDate(date);
        tenant.setCustomizedHost(createTenantDTO.getCustomizedHost());
        tenant.setStatus(StringUtils.defaultString(createTenantDTO.getStatus(), "0"));

        id = StringUtils.defaultString(id, UUID.randomUUID().toString());
        tenant.setId(id);

        tenantService.getTenantRepository().insert(tenant);

        CreateTenantVO vo = new CreateTenantVO();
        vo.setId(id);
        return vo;
    }
}
