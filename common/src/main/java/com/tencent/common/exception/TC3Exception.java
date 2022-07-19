package com.tencent.common.exception;

import com.tencent.common.enums.TC3ErrCodeEnum;

/**
 * TC3Exception
 *
 * @author torrisli
 * @date 2020/12/17
 * @Description: TC3异常类
 */
public class TC3Exception extends RuntimeException {

    public TC3Exception(String message) {
        super(message);
    }

    public TC3Exception(TC3ErrCodeEnum errorCodeEnum) {
        super("[" + errorCodeEnum.getErrorCode() + "] " + errorCodeEnum.getErrorMessage());
    }
}
