package com.tencent.common.constants;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * ApiConstants
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: ApiConstants
 */
public class ApiConstants {

    public static final String ACTION = "Action";

    public static final String UIN = "Uin";

    public static final String SUB_ACCOUNT_UIN = "SubAccountUin";

    public static final String REQUEST_ID = "RequestId";

    public static final String RESPONSE = "Response";

    public static final String CODE = "Code";

    public static final String MESSAGE = "Message";

    public static final String ERROR = "Error";

    public static final String LANGUAGE = "Language";

    public static final String CAM_CONTEXT = "CamContext";

    public static final String CLIENT_IP = "clientIP";

    public static final List NONE_TENANT_CTX_API = Lists.newArrayList();

    static {
        NONE_TENANT_CTX_API.add("CreateTenant");
    }
}
