package com.tencent.common.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * TC3Constants
 *
 * @author torrisli
 * @date 2021/8/29
 * @Description: TC3Constants
 */
public class TC3Constants {

    public static final Charset UTF8 = StandardCharsets.UTF_8;
    public static final String LINEFEED = "\n";

    public static final String DEFAULT_ALGORITHM = "TC3-HMAC-SHA256";
    public static final String DEFAULT_CREDENTIAL_SCOPE_END = "tc3_request";
    public static final String DEFAULT_CANONICALURI = "/";

    public static final String X_TC_REGION = "X-TC-Region";
    public static final String X_TC_VERSION = "X-TC-Version";
    public static final String X_TC_TIMESTAMP = "X-TC-Timestamp";
    public static final String X_TC_ACTION = "X-TC-Action";
    public static final String X_TC_LANGUAGE = "X-TC-Language";
    public static final String HOST = "Host";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
}
