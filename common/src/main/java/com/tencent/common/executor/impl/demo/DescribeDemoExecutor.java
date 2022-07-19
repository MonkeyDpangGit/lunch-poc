package com.tencent.common.executor.impl.demo;

import com.tencent.common.dto.demo.DescribeDemoDTO;
import com.tencent.common.executor.IExecutor;
import com.tencent.common.model.DemoEntity;
import com.tencent.common.service.DemoService;
import com.tencent.common.vo.demo.DescribeDemoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DescribeDemoExecutor
 *
 * @author torrisli
 * @date 2021/8/24
 * @Description: DescribeDemoExecutor
 */
@Component("DescribeDemo")
public class DescribeDemoExecutor implements IExecutor<DescribeDemoDTO, DescribeDemoVO> {

    @Autowired
    private DemoService demoService;

    @Override
    public DescribeDemoVO execute(DescribeDemoDTO describeDemoDTO) throws Exception {

        String name = describeDemoDTO.getName();
        boolean exists = demoService.getDemoRepository().existsByName(name);
        if (!exists) {
            throw new IllegalArgumentException("name not exists");
        }

        DemoEntity demoEntity = demoService.getDemoRepository().findByName(name);

        DescribeDemoVO vo = new DescribeDemoVO();
        vo.setDesc(demoEntity.getDesc());
        vo.setName(demoEntity.getName());
        vo.setId(demoEntity.getId());

        return vo;
    }
}
