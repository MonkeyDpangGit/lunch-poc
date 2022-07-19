package com.tencent.common.enums;

/**
 * TC3ErrCodeEnum
 *
 * @author torrisli
 * @date 2020/12/18
 * @Description: TC3标准的错误码枚举
 */
public enum TC3ErrCodeEnum {

    AUTHFAILURE_SIGNATUREFAILURE("AuthFailure.SignatureFailure", "签名错误。 签名计算错误，请对照调用方式中的签名方法文档检查签名计算过程。"),
    AUTHFAILURE_SIGNATUREEXPIRE("AuthFailure.SignatureExpire", "签名过期。Timestamp 和服务器时间相差不得超过五分钟，请检查本地时间是否和标准时间同步。"),
    UNAUTHORIZED_OPERATION("UnauthorizedOperation", "未授权操作。"),
    INVALID_PARAMETER("InvalidParameter", "参数错误。"),
    AUTHFAILURE_UNAUTHORIZED_OPERATION("AuthFailure.UnauthorizedOperation", "请求未授权。"),
    AUTHFAILURE_INVALID_SECRETID("AuthFailure.InvalidSecretId", "密钥非法（不是云 API 密钥类型）。"),
    AUTHFAILURE_TOKEN_FAILURE("AuthFailure.TokenFailure", "token 错误。"),
    UNKNOWN_PARAMETER("UnknownParameter", "未知参数错误。"),
    INVALID_PARAMETER_VALUE("InvalidParameterValue", "参数取值错误。"),
    MISSING_PARAMETER("MissingParameter", "缺少参数错误。"),
    FAILED_OPERATION("FailedOperation", "操作失败。"),
    INTERNAL_ERROR("InternalError", "内部错误。");

    private String errorCode;

    private String errorMessage;

    TC3ErrCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
