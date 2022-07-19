package com.tencent.common.executor.impl.demo;

import com.tencent.common.dto.demo.DeleteDemoDTO;
import com.tencent.common.executor.IExecutor;
import com.tencent.common.service.DemoService;
import com.tencent.common.vo.common.EmptyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DeleteDemoExecutor
 *
 * @author torrisli
 * @date 2021/8/24
 * @Description: DeleteDemoExecutor
 */
@Component("DeleteDemo")
public class DeleteDemoExecutor implements IExecutor<DeleteDemoDTO, EmptyVO> {

    @Autowired
    private DemoService demoService;

    @Override
    public EmptyVO execute(DeleteDemoDTO deleteDemoDTO) throws Exception {

        String name = deleteDemoDTO.getName();
        boolean exists = demoService.getDemoRepository().existsByName(name);
        if (!exists) {
            throw new IllegalArgumentException("name not exists");
        }

        demoService.getDemoRepository().deleteByName(name);
        return EmptyVO.n();
    }
}
