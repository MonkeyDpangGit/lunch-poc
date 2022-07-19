package com.tencent.common.executor.impl.demo;

import com.tencent.common.dto.demo.UpdateDemoDTO;
import com.tencent.common.executor.IExecutor;
import com.tencent.common.model.DemoEntity;
import com.tencent.common.service.DemoService;
import com.tencent.common.vo.common.EmptyVO;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UpdateDemoExecutor
 *
 * @author torrisli
 * @date 2021/8/25
 * @Description: UpdateDemoExecutor
 */
@Component("UpdateDemo")
public class UpdateDemoExecutor implements IExecutor<UpdateDemoDTO, EmptyVO> {

    @Autowired
    private DemoService demoService;

    @Override
    public EmptyVO execute(UpdateDemoDTO updateDemoDTO) throws Exception {

        DemoEntity demoEntity = demoService.getDemoRepository().findByName(updateDemoDTO.getName());
        if (demoEntity == null) {
            throw new IllegalArgumentException("name not exists");
        }

        String desc = updateDemoDTO.getDesc();
        if (desc != null) {
            demoEntity.setDesc(desc);
        }

//        demoEntity.setLastModifiedDate(new Date());
        demoService.getDemoRepository().save(demoEntity);

        return EmptyVO.n();
    }
}
