package com.tencent.common.executor.impl.demo;

import com.tencent.common.dto.demo.CreateDemoDTO;
import com.tencent.common.executor.IExecutor;
import com.tencent.common.model.DemoEntity;
import com.tencent.common.service.DemoService;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.vo.demo.CreateDemoVO;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CreateDemoExecutor
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: CreateDemoExecutor
 */
@Component("CreateDemo")
public class CreateDemoExecutor implements IExecutor<CreateDemoDTO, CreateDemoVO> {

    @Autowired
    private DemoService demoService;

    @Override
    public CreateDemoVO execute(CreateDemoDTO createDemoEntityDTO) throws Exception {

        String name = createDemoEntityDTO.getName();
        boolean exists = demoService.getDemoRepository().existsByName(name);
        if (exists) {
            throw new IllegalArgumentException("name exists");
        }

        DemoEntity demoEntity = new DemoEntity();
        Date date = new Date();
//        demoEntity.setCreatedDate(date);
//        demoEntity.setLastModifiedDate(date);
        demoEntity.setExpiredDate(date);
        demoEntity.setDesc(createDemoEntityDTO.getDesc());
        demoEntity.setName(name);
        demoEntity.setOrg(createDemoEntityDTO.getOrg());

//        String id = StringUtils.defaultString(createDemoEntityDTO.getId(), UUID.randomUUID().toString());
//        demoEntity.setId(id);

        demoService.getDemoRepository().insert(demoEntity);

        CreateDemoVO vo = new CreateDemoVO();
//        vo.setId(id);

        return vo;
    }
}
