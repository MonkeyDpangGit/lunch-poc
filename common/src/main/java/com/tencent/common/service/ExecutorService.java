package com.tencent.common.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.tencent.common.constants.ApiConstants;
import com.tencent.common.context.AppCtx;
import com.tencent.common.dto.common.PageDTO;
import com.tencent.common.enums.ErrorCodeEnum;
import com.tencent.common.exception.ApplicationException;
import com.tencent.common.executor.IExecutor;
import com.tencent.common.utils.CaseTransferUtils;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ExecutorService
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: ExecutorService
 */
@Component
public class ExecutorService {

    private static final Logger log = LoggerFactory.getLogger(ExecutorService.class);

    @Value("${openapi.trace.enable:true}")
    private Boolean traceEnable;
    @Value("${openapi.trace.expired:3000}")
    private Integer traceExpired;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("projectValidator")
    private Validator validator;

    /**
     * 执行指定executor方法
     *
     * @param input 输入对象
     * @return
     */
    public Map execute(Map input) {

        long beginTime = System.currentTimeMillis();
        Map result = Maps.newHashMap();

        try {
            // 必输字段检查
            if (MapUtils.isEmpty(input)) {
                throw new Exception("common parameter required!");
            }
            if (StringUtils.isBlank(MapUtils.getString(input, ApiConstants.ACTION))) {
                throw new Exception("parameter 'Action' required!");
            }

            // 获取Action参数指定的executor
            String action = MapUtils.getString(input, ApiConstants.ACTION);
            IExecutor executor = (IExecutor) applicationContext.getBean(action);
            if (executor == null) {
                throw new Exception("cannot find executor: " + action);
            }

            // 获得execute方法，且不是生成的
            Method method = null;
            int methodCount = 0;
            for (Method item : executor.getClass().getMethods()) {
                if ("execute".equals(item.getName()) && !item.isSynthetic()) {
                    method = item;
                    methodCount++;
                }
            }
            if (methodCount != 1) {
                throw new Exception("too many execute method");
            }

            // 处理输入参数
            Map realInputMap = CaseTransferUtils.lowerKeyFirstLetterInMap(input);
            Class<?> paramType = method.getParameterTypes()[0];
            Object inputObj = JSONObject.parseObject(JSON.toJSONString(realInputMap), paramType);

            // 参数检查
            checkParam(inputObj);

            // 执行处理逻辑
            Object outputObj = executor.execute(inputObj);

            // 处理输出参数
            Map outMap = JSONObject.parseObject(JSON.toJSONString(outputObj, SerializerFeature.WriteMapNullValue));
            outMap = CaseTransferUtils.upperKeyFirstLetterInMap(outMap);

            outMap.put(ApiConstants.REQUEST_ID, AppCtx.get().getRequestId());
            result.put(ApiConstants.RESPONSE, outMap);
        } catch (Exception e) {
            result = errorResponse(e);
        }

        // 日志跟踪
        long consumeTime = System.currentTimeMillis() - beginTime;
        if (traceEnable || consumeTime > traceExpired) {
            log.info("[input] [requestId]{}, [param]{}", AppCtx.get().getRequestId(), input.toString());
            log.info("[output] [requestId]{}, [param]{}", AppCtx.get().getRequestId(), result.toString());
            log.info("[summary] [requestId]{}, [consume]{}", AppCtx.get().getRequestId(), consumeTime);
        }

        return result;
    }

    /**
     * 处理错误返回
     *
     * @param e 异常类
     * @return map
     */
    private Map errorResponse(Exception e) {

        Map errorMap = Maps.newHashMap();

        if (e instanceof ApplicationException) {
            ApplicationException exception = (ApplicationException) e;
            errorMap.put(ApiConstants.CODE, exception.errorEnum.errorCode);
            String errorMessage = exception.errorEnum.errorMessage;
            Object[] fillParameter = exception.getFillParameter();
            if (fillParameter != null) {
                errorMessage = String.format(errorMessage, fillParameter);
            }
            errorMap.put(ApiConstants.MESSAGE, errorMessage);
        } else if (e instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) e;
            errorMap.put(ApiConstants.CODE, ErrorCodeEnum.ILLEGAL_PARAMETER.errorCode);
            errorMap.put(ApiConstants.MESSAGE,
                    String.format(ErrorCodeEnum.ILLEGAL_PARAMETER.errorMessage, exception.getMessage()));
        } else {
            errorMap.put(ApiConstants.CODE, ErrorCodeEnum.FAILED_OPERATION.errorCode);
            errorMap.put(ApiConstants.MESSAGE,
                    StringUtils.defaultIfBlank(e.getMessage(), ErrorCodeEnum.FAILED_OPERATION.errorMessage));
        }

        Map outputMap = Maps.newHashMap();
        outputMap.put(ApiConstants.ERROR, errorMap);
        outputMap.put(ApiConstants.REQUEST_ID, AppCtx.get().getRequestId());
        Map result = Maps.newHashMap();
        result.put(ApiConstants.RESPONSE, outputMap);
        return result;
    }

    /**
     * 参数校验
     *
     * @param t 范型参数
     * @param <T>
     */
    private <T> void checkParam(T t) {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (violations.size() > 0) {
            String message = "";
            for (ConstraintViolation<T> violation : violations) {
                message = violation.getMessage();
                break;
            }
            throw new IllegalArgumentException(message);
        }
        if (t instanceof PageDTO) {
            PageDTO pageDTO = (PageDTO) t;
            Integer offset = pageDTO.getOffset();
            Integer limit = pageDTO.getLimit();
            if ((offset == null && limit != null) || (limit == null && offset != null)) {
                throw new IllegalArgumentException("'Offset' and 'Limit' should attend at the same time");
            } else if (offset != null && limit != null) {
                if (offset < 0) {
                    throw new IllegalArgumentException("'Offset' cannot be less then zero");
                }
                if (limit <= 0) {
                    throw new IllegalArgumentException("'Limit' should be greater then zero");
                }
            }
        }
    }

}
