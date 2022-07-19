package com.tencent.common.utils.tc3;

import com.google.common.collect.Maps;
import com.tencent.common.constants.TC3Constants;
import com.tencent.common.exception.TC3Exception;
import com.tencent.common.utils.RequestUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * TC3Request
 *
 * @author torrisli
 * @date 2020/12/17
 * @Description: TC3请求
 */
public class TC3Request {

    /**
     * header -> X-TC-Region
     */
    private String region;
    /**
     * header -> X-TC-Action
     */
    private String action;
    /**
     * header -> X-TC-Version
     */
    private String version;
    /**
     * header -> X-TC-Language
     */
    private String language;
    /**
     * header -> Host
     */
    private String host;

    /**
     * header -> X-TC-Timestamp
     * String.valueOf(System.currentTimeMillis() / 1000)
     */
    private String timestamp;
    /**
     * header -> Content-Type
     */
    private String contentType;
    /**
     * header -> Authorization
     */
    private String authorization;
    /**
     * header -> Authorization -> Credential
     * secretId/credentialScope
     */
    private String credential;
    /**
     * header -> Authorization -> Credential -> secretId
     */
    private String secretId;
    /**
     * header -> Authorization -> Credential -> credentialScope
     * date/service/tc3_request
     */
    private String credentialScope;
    /**
     * header -> Authorization -> Credential -> credentialScope -> date
     */
    private String date;
    /**
     * header -> Authorization -> Credential -> credentialScope -> service
     */
    private String service;
    /**
     * header -> Authorization -> SignedHeaders
     */
    private String signedHeaders;
    /**
     * header -> Authorization -> Signature
     */
    private String signature;
    /**
     * body
     */
    private String payload;
    /**
     * request method
     */
    private String httpRequestMethod;
    /**
     * GET 请求，为 URL 中问号（?）后面的字符串内容；POST 请求，固定为空字符串""
     */
    private String canonicalQueryString;
    /**
     * headers
     */
    public Map<String, String> headers;

    /**
     * 构造符合TC3要求的http请求体
     *
     * @param host Host
     * @param payload http体
     * @param action X-TC-Action
     * @param version X-TC-Version
     * @param secretId secretId
     * @return
     */
    public static TC3Request build(String host, String payload, String action, String version, String secretId) {

        TC3Request tc3Request = new TC3Request();
        tc3Request.setHost(host);
        tc3Request.setPayload(payload);
        tc3Request.setAction(action);
        tc3Request.setVersion(version);
        tc3Request.setSecretId(secretId);

        tc3Request.setContentType("application/json; charset=utf-8");
        tc3Request.setService("idam");
        tc3Request.setSignedHeaders("content-type;host");
        tc3Request.setHttpRequestMethod("POST");

        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        tc3Request.setTimestamp(timestamp);
        tc3Request.setDate(TC3Utils.timestampToDateStr(timestamp));

        Map headers = Maps.newHashMap();
        headers.put(TC3Constants.CONTENT_TYPE.toLowerCase(), "application/json; charset=utf-8");
        headers.put(TC3Constants.HOST.toLowerCase(), host);
        tc3Request.setHeaders(headers);
        return tc3Request;
    }

    /**
     * 解析出符合TC3要求的http请求体
     *
     * @param request http请求体
     */
    public static TC3Request parse(HttpServletRequest request) throws Exception {

        // get http request method
        String httpRequestMethod = RequestUtils.parseMethod(request);
        // get http body
        String playload = RequestUtils.parsePayload(request);
        // get http query string
        String queryString = request.getQueryString();
        // get http header attribute
        Map<String, String> headers = RequestUtils.parseHeaders(request);

        return doParse(httpRequestMethod, queryString, playload, headers);
    }

    /**
     * 解析TC3要求的http请求体
     *
     * @param httpRequestMethod http请求方法
     * @param queryString 发起 HTTP请求 URL中的查询字符串
     * @param playload http body
     * @param headers 所有header参数
     */
    private static TC3Request doParse(String httpRequestMethod, String queryString, String playload,
            Map<String, String> headers) throws Exception {

        TC3Request tc3Request = new TC3Request();

        // get http request method
        tc3Request.setHttpRequestMethod(httpRequestMethod);

        // get http body
        tc3Request.setPayload(playload);

        // get http query string
        tc3Request.setCanonicalQueryString(queryString);

        // get http header attribute
        tc3Request.setHeaders(headers);

        // get header X-TC-Action
        tc3Request.setAction(headers.get(TC3Constants.X_TC_ACTION.toLowerCase()));
        // get header X-TC-Region
        tc3Request.setRegion(headers.get(TC3Constants.X_TC_REGION.toLowerCase()));
        // get header X-TC-Version
        tc3Request.setVersion(headers.get(TC3Constants.X_TC_VERSION.toLowerCase()));
        // get header X-TC-Timestamp
        String timestamp = headers.get(TC3Constants.X_TC_TIMESTAMP.toLowerCase());
        tc3Request.setTimestamp(timestamp);
        // get header X-TC-Language
        tc3Request.setLanguage(headers.get(TC3Constants.X_TC_LANGUAGE.toLowerCase()));
        // get header Host
        tc3Request.setHost(headers.get(TC3Constants.HOST.toLowerCase()));
        // get header Content-Type
        tc3Request.setContentType(headers.get(TC3Constants.CONTENT_TYPE.toLowerCase()));
        // get header Authorization
        String authorization = headers.get(TC3Constants.AUTHORIZATION.toLowerCase());
        tc3Request.setAuthorization(authorization);

        // Authorization example:
        if (StringUtils.isBlank(authorization) || !authorization.startsWith(TC3Constants.DEFAULT_ALGORITHM)) {
            throw new TC3Exception("'Authorization' field needed in valid format");
        }

        String authInfo = authorization.substring(TC3Constants.DEFAULT_ALGORITHM.length() + 1);
        if (StringUtils.isBlank(authInfo)) {
            throw new TC3Exception("'Authorization' field needed in valid format");
        }
        String[] authInfoGrp = authInfo.split(", ");
        if (authInfoGrp.length != 3) {
            throw new TC3Exception("'Authorization' field needed in valid format");
        }

        // get Credential(secretId/credentialScope)
        String credential = authInfoGrp[0].replaceFirst("Credential=", "");
        tc3Request.setCredential(credential);

        String[] credentialGrp = credential.split("/");
        if (credentialGrp.length != 4) {
            throw new TC3Exception("'Credential' invalid format");
        }
        // get SecretId
        tc3Request.setSecretId(credentialGrp[0]);
        // get Date
        String date = credentialGrp[1];
        if (!date.equals(TC3Utils.timestampToDateStr(timestamp))) {
            throw new TC3Exception("'Date' not match 'Timestamp'");
        }
        tc3Request.setDate(date);
        // get Service
        String service = credentialGrp[2];
        tc3Request.setService(service);
        if (!TC3Constants.DEFAULT_CREDENTIAL_SCOPE_END.equals(credentialGrp[3])) {
            throw new TC3Exception("'Credential' invalid format");
        }
        // get CredentialScope
        tc3Request.setCredentialScope(TC3Utils.generateCredentialScope(date, service));

        // get SignedHeaders
        String signedHeaders = authInfoGrp[1].replaceFirst("SignedHeaders=", "");
        tc3Request.setSignedHeaders(signedHeaders);
        // get Signature
        String signature = authInfoGrp[2].replaceFirst("Signature=", "");
        tc3Request.setSignature(signature);

        return tc3Request;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getCredentialScope() {
        return credentialScope;
    }

    public void setCredentialScope(String credentialScope) {
        this.credentialScope = credentialScope;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSignedHeaders() {
        return signedHeaders;
    }

    public void setSignedHeaders(String signedHeaders) {
        this.signedHeaders = signedHeaders;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public void setHttpRequestMethod(String httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public String getCanonicalQueryString() {
        return canonicalQueryString;
    }

    public void setCanonicalQueryString(String canonicalQueryString) {
        this.canonicalQueryString = canonicalQueryString;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
