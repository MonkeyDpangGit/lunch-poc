package com.tencent.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.common.constants.ApiConstants;
import com.tencent.common.context.AppCtx;
import com.tencent.common.context.SubjectCtx;
import com.tencent.common.context.helper.ClientCtxHelper;
import com.tencent.common.context.helper.SubjectCtxHelper;
import com.tencent.common.context.helper.TenantCtxHelper;
import com.tencent.common.enums.ErrorCodeEnum;
import com.tencent.common.model.Tenant;
import com.tencent.common.service.TenantService;
import com.tencent.common.utils.CustomRequestWrapper;
import com.tencent.common.utils.tc3.TC3Request;
import com.tencent.common.utils.tc3.TC3Utils;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CommonFilter
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: CommonFilter
 */
@Component
public class CommonFilter implements Filter {

    @Autowired
    private ClientCtxHelper clientCtxHelper;
    @Autowired
    private TenantCtxHelper tenantCtxHelper;
    @Autowired
    private SubjectCtxHelper subjectCtxHelper;

    @Autowired
    private TenantService tenantService;

    @Override public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

        try {
            CustomRequestWrapper wrapper = CustomRequestWrapper.newInstance((HttpServletRequest) servletRequest);
            initAppCtx(wrapper);
            filterChain.doFilter(wrapper, servletResponse);
        } catch (Exception e) {
            errorResponse((HttpServletResponse) servletResponse, e.getMessage());
            return;
        } finally {
            AppCtx.destroy();
        }
    }

    @Override public void destroy() {

    }

    private void initAppCtx(CustomRequestWrapper wrapper) throws Exception {

        AppCtx.init();
        AppCtx appCtx = AppCtx.get();

        String path = wrapper.getRequestURI();

        if (path.contains("/capi/execute")) {

            Map camInput = JSON.parseObject(wrapper.getBody(), Map.class);
            if (MapUtils.isEmpty(camInput)) {
                throw new IllegalArgumentException("common parameter required!");
            }
            String requestId = MapUtils
                    .getString(camInput, ApiConstants.REQUEST_ID, "sys_" + UUID.randomUUID().toString());
            appCtx.setRequestId(requestId);

            String uin = MapUtils.getString(camInput, ApiConstants.UIN);
            if (StringUtils.isBlank(uin)) {
                throw new IllegalArgumentException("parameter 'Uin' required!");
            }

            String action = MapUtils.getString(camInput, ApiConstants.ACTION);
            if (StringUtils.isBlank(action)) {
                throw new Exception("parameter 'Action' required!");
            }
            if (!ApiConstants.NONE_TENANT_CTX_API.contains(action)) {
                Tenant tenant = tenantService.getTenantById(uin);
                if (tenant == null) {
                    throw new IllegalArgumentException("can not find tenant.");
                }
                appCtx.setTenantCtx(tenantCtxHelper.buildByTenant(tenant));
            }

            appCtx.setSubjectCtx(subjectCtxHelper.buildByCamInput(camInput));
            appCtx.setClientCtx(clientCtxHelper.buildByCamInput(camInput));

        } else if (path.contains("/manage/execute")) {

            TC3Request tc3Request = TC3Request.parse(wrapper);

            String secretId = tc3Request.getSecretId();
            // get secretKey by secretId
            String secretKey = "";
            Long signatureExpire = 100L;
            TC3Utils.verifySignature(tc3Request, secretKey, signatureExpire);

            Map camInput = JSON.parseObject(tc3Request.getPayload(), Map.class);
            if (MapUtils.isEmpty(camInput)) {
                throw new IllegalArgumentException("common parameter required!");
            }
            String requestId = MapUtils
                    .getString(camInput, ApiConstants.REQUEST_ID, "sys_" + UUID.randomUUID().toString());
            appCtx.setRequestId(requestId);

            String uin = MapUtils.getString(camInput, ApiConstants.UIN);
            if (StringUtils.isBlank(uin)) {
                throw new IllegalArgumentException("parameter 'Uin' required!");
            }
            // verify service sig todo

            Tenant tenant = tenantService.getTenantById(uin);
            if (tenant == null) {
                throw new IllegalArgumentException("can not find tenant.");
            }

            appCtx.setTenantCtx(tenantCtxHelper.buildByTenant(tenant));
            appCtx.setSubjectCtx(subjectCtxHelper.buildByCamInput(camInput));
            appCtx.setClientCtx(clientCtxHelper.buildByHttpRequest(wrapper));

        } else if (path.contains("/third/execute")) {

            Map camInput = JSON.parseObject(wrapper.getBody(), Map.class);
            String requestId = MapUtils
                    .getString(camInput, ApiConstants.REQUEST_ID, "sys_" + UUID.randomUUID().toString());
            appCtx.setRequestId(requestId);

            // verify tenant sig todo
            String uin = null;
            camInput = null;

            Tenant tenant = tenantService.getTenantById(uin);
            appCtx.setTenantCtx(tenantCtxHelper.buildByTenant(tenant));
            appCtx.setSubjectCtx(subjectCtxHelper.buildByCamInput(camInput));
            appCtx.setClientCtx(clientCtxHelper.buildByHttpRequest(wrapper));

        } else {
            appCtx.setRequestId("sys_" + UUID.randomUUID().toString());

            Tenant tenant = tenantService.getTenantByHost(wrapper);
            if (tenant == null) {
                throw new IllegalArgumentException("can not find tenant.");
            }
            // verify session
            SubjectCtx subjectCtx = (SubjectCtx) wrapper.getSession().getAttribute("SUBJECT_CTX");
            if (subjectCtx == null) {
                // redirect to login
            }
            appCtx.setTenantCtx(tenantCtxHelper.buildByTenant(tenant));
            appCtx.setSubjectCtx(subjectCtxHelper.buildBySession(wrapper));
            appCtx.setClientCtx(clientCtxHelper.buildByHttpRequest(wrapper));
        }
    }

    /**
     * 失败返回信息
     *
     * @param response http响应
     * @param errorMsg 错误信息
     */
    private void errorResponse(HttpServletResponse response, String errorMsg) {

        try {
            JSONObject errorMap = new JSONObject();
            JSONObject result = new JSONObject();
            errorMap.put(ApiConstants.CODE, ErrorCodeEnum.ILLEGAL_PARAMETER.errorCode);
            errorMap.put(ApiConstants.MESSAGE, errorMsg);
            result.put(ApiConstants.ERROR, errorMap);
            result.put(ApiConstants.REQUEST_ID, AppCtx.get().getRequestId());

            JSONObject output = new JSONObject();
            output.put(ApiConstants.RESPONSE, result);

            // 输出返回
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(output.toString());
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
