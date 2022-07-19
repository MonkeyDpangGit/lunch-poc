package com.tencent.common.context.helper;

import com.tencent.common.constants.ApiConstants;
import com.tencent.common.context.SubjectCtx;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

/**
 * SubjectCtxHelper
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: SubjectCtxHelper
 */
@Configuration
public class SubjectCtxHelper {

    public SubjectCtx buildByCamInput(Map camInput) {

        String subAccountUin = MapUtils.getString(camInput, ApiConstants.SUB_ACCOUNT_UIN);
        if (StringUtils.isBlank(subAccountUin)) {
            subAccountUin = MapUtils.getString(camInput, ApiConstants.UIN);
        }

        SubjectCtx subjectCtx = new SubjectCtx();
        subjectCtx.setUserId(subAccountUin);
        subjectCtx.setUserName(subAccountUin);

        return subjectCtx;
    }

    public SubjectCtx buildBySession(HttpServletRequest request) {

        return null;
    }
}
