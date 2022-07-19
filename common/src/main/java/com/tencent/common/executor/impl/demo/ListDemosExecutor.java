package com.tencent.common.executor.impl.demo;

import com.tencent.common.dto.common.PageDTO;
import com.tencent.common.executor.IExecutor;
import com.tencent.common.model.DemoEntity;
import com.tencent.common.service.DemoService;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.vo.common.PageVO;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * ListDemosExecutor
 *
 * @author torrisli
 * @date 2021/8/25
 * @Description: ListDemosExecutor
 */
@Component("ListDemos")
public class ListDemosExecutor implements IExecutor<PageDTO, PageVO<DemoEntity>> {

    @Autowired
    private DemoService demoService;

    @Override
    public PageVO<DemoEntity> execute(PageDTO pageDTO) throws Exception {

        Map searchCondition = pageDTO.getSearchCondition();
        Pageable pageable = pageDTO.toPageable("id");

        Page<DemoEntity> demoEntitieList = null;

        String name = MapUtils.getString(searchCondition, "name");
        List org = (List) MapUtils.getObject(searchCondition, "org");
        if (StringUtils.isNotBlank(name)) {
            demoEntitieList = demoService.getDemoRepository().findAllByName(name, pageable);
        } else if (CollectionUtils.isNotEmpty(org)) {
            demoEntitieList = demoService.getDemoRepository().findAllByOrgIn(org, pageable);
        } else {
            demoEntitieList = demoService.getDemoRepository().findAll(pageable);
        }

        return PageVO.ok(demoEntitieList.toList(), demoEntitieList.getTotalElements(), "EntitieList");
    }
}
