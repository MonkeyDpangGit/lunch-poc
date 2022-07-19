package com.tencent.common.utils.tc3;

import com.tencent.common.constants.TC3Constants;
import com.tencent.common.enums.TC3ErrCodeEnum;
import com.tencent.common.exception.TC3Exception;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * TC3标准签名工具类
 *
 * @author torrisli
 * @date 2020/12/16
 * @Description: TC3标准签名工具类
 */
public class TC3Utils {

    /**
     * 生成TC3签名信息
     *
     * @param tc3Request TC3请求对象
     * @param secretKey 签名用的secretKey
     */
    public static String generateSignature(TC3Request tc3Request, String secretKey) throws Exception {

        // get http input
        String httpRequestMethod = tc3Request.getHttpRequestMethod();
        String canonicalQueryString = tc3Request.getCanonicalQueryString();
        String payload = tc3Request.getPayload();
        String timestamp = tc3Request.getTimestamp();
        String service = tc3Request.getService();
        String signedHeaders = tc3Request.getSignedHeaders();
        Map<String, String> headers = tc3Request.getHeaders();
        String date = tc3Request.getDate();
        String credentialScope = tc3Request.getCredentialScope();

        // ************* 步骤 1：拼接规范请求串 *************
        String canonicalRequest = generateCanonicalRequest(httpRequestMethod, signedHeaders,
                headers, canonicalQueryString, payload);

        // ************* 步骤 2：拼接待签名字符串 *************
        String stringToSign = generateStringToSign(credentialScope, timestamp, canonicalRequest);

        // ************* 步骤 3：计算签名 *************
        String signature = generateSignature(secretKey, date, service, stringToSign);

        return signature;
    }

    /**
     * 生成TC3签名信息
     *
     * @param request http请求体
     * @param secretKey 签名用的secretKey
     * @return
     */
    public static String generateSignature(HttpServletRequest request, String secretKey) throws Exception {

        TC3Request tc3Request = TC3Request.parse(request);
        return generateSignature(tc3Request, secretKey);
    }

    /**
     * 验证TC3签名信息
     *
     * @param tc3Request TC3请求对象
     * @param secretKey 签名用的secretKey
     * @param signatureExpire 签名过期时间（秒）
     * @throws Exception
     */
    public static void verifySignature(TC3Request tc3Request, String secretKey, Long signatureExpire)
            throws Exception {

        String tc3Signature = generateSignature(tc3Request, secretKey);

        String clientSignature = tc3Request.getSignature();

        if (!tc3Signature.equals(clientSignature)) {
            throw new TC3Exception(TC3ErrCodeEnum.AUTHFAILURE_SIGNATUREFAILURE);
        }
        Long timestamp = Long.parseLong(tc3Request.getTimestamp());
        Long now = Long.parseLong(String.valueOf(System.currentTimeMillis() / 1000));
        if (now - timestamp > signatureExpire) {
            throw new TC3Exception(TC3ErrCodeEnum.AUTHFAILURE_SIGNATUREEXPIRE);
        }
    }

    /**
     * 验证TC3签名信息
     *
     * @param request http请求体
     * @param secretKey 签名用的secretKey
     * @param signatureExpire 签名过期时间（秒）
     */
    public static void verifySignature(HttpServletRequest request, String secretKey, Long signatureExpire)
            throws Exception {

        TC3Request tc3Request = TC3Request.parse(request);
        verifySignature(tc3Request, secretKey, signatureExpire);
    }

    /**
     * 生成TC3要求的http头部信息
     *
     * @param authorization Authorization
     * @param contentType Content-Type
     * @param host Host
     * @param action X-TC-Action
     * @param timestamp X-TC-Version
     * @param version X-TC-Timestamp
     * @param region X-TC-Region
     * @return
     */
    public static Map<String, String> generateHeader(String authorization, String contentType, String host,
            String action, String timestamp, String version, String region) {

        TreeMap<String, String> headers = new TreeMap<String, String>();
        headers.put(TC3Constants.AUTHORIZATION, authorization);
        headers.put(TC3Constants.CONTENT_TYPE, contentType);
        headers.put(TC3Constants.HOST, host);
        headers.put(TC3Constants.X_TC_ACTION, action);
        headers.put(TC3Constants.X_TC_TIMESTAMP, timestamp);
        headers.put(TC3Constants.X_TC_VERSION, version);
        if (StringUtils.isNotBlank(region)) {
            headers.put(TC3Constants.X_TC_REGION, region);
        }

        return headers;
    }

    /**
     * 生成TC3要求的http头部信息
     *
     * @param tc3Request TC3要求的http请求
     * @param secretKey 签名用的secretKey
     * @return
     * @throws Exception
     */
    public static Map<String, String> generateHeader(TC3Request tc3Request, String secretKey) throws Exception {

        String credentialScope = TC3Utils.generateCredentialScope(tc3Request.getDate(), tc3Request.getService());
        tc3Request.setCredentialScope(credentialScope);

        String signature = TC3Utils.generateSignature(tc3Request, secretKey);

        String secretId = tc3Request.getSecretId();
        String authorization = TC3Utils
                .generateAuthorization(secretId, credentialScope, tc3Request.getSignedHeaders(), signature);

        TreeMap<String, String> headers = new TreeMap<String, String>();
        headers.put(TC3Constants.AUTHORIZATION, authorization);
        headers.put(TC3Constants.CONTENT_TYPE, tc3Request.getContentType());
        headers.put(TC3Constants.HOST, tc3Request.getHost());
        headers.put(TC3Constants.X_TC_ACTION, tc3Request.getHost());
        headers.put(TC3Constants.X_TC_TIMESTAMP, tc3Request.getTimestamp());
        headers.put(TC3Constants.X_TC_VERSION, tc3Request.getVersion());
        if (StringUtils.isNotBlank(tc3Request.getTimestamp())) {
            headers.put(TC3Constants.X_TC_REGION, tc3Request.getRegion());
        }

        return headers;
    }

    /**
     * 生成TC3要求的http头部的Authorization信息
     * 格式为
     * Algorithm + ' ' +
     * 'Credential=' + SecretId + '/' + CredentialScope + ', ' +
     * 'SignedHeaders=' + SignedHeaders + ', ' +
     * 'Signature=' + Signature
     *
     * @param secretId 密钥对中的SecretId
     * @param credentialScope 凭证范围
     * @param signedHeaders 参与签名的头部信息
     * @param signature 签名值
     * @return
     */
    private static String generateAuthorization(String secretId, String credentialScope, String signedHeaders,
            String signature) {

        StringBuilder sb = new StringBuilder();
        // 签名方法，固定为TC3-HMAC-SHA256
        sb.append(TC3Constants.DEFAULT_ALGORITHM);
        sb.append(" ");
        sb.append("Credential=");
        sb.append(secretId);
        sb.append("/");
        sb.append(credentialScope);
        sb.append(", ");
        sb.append("SignedHeaders=");
        sb.append(signedHeaders);
        sb.append(", ");
        sb.append("Signature=");
        sb.append(signature);

        return sb.toString();
    }

    /**
     * 生成TC3签名信息
     *
     * @param secretKey 原始的SecretKey
     * @param date Credential中的date字段信息，格式为yyyy-MM-dd
     * @param service Credential中的Service字段信息
     * @param stringToSign 生成待签名字符串
     */
    private static String generateSignature(String secretKey, String date, String service, String stringToSign)
            throws Exception {

        byte[] secretDate = hmac256(("TC3" + secretKey).getBytes(TC3Constants.UTF8), date);
        byte[] secretService = hmac256(secretDate, service);
        byte[] secretSigning = hmac256(secretService, "tc3_request");
        String signature = Hex.encodeHexString(hmac256(secretSigning, stringToSign));

        return signature;
    }

    /**
     * 生成凭证范围CredentialScope
     * 格式为Date/service/tc3_request，包含日期、所请求的服务和终止字符串（tc3_request）
     *
     * @param date UTC标准时间的日期，取值需要和公共参数X-TC-Timestamp换算的UTC 标准时间日期一致
     * @param service 产品名，必须与调用的产品域名一致
     * @return
     */
    public static String generateCredentialScope(String date, String service) {

        String credentialScope = date + "/" + service + "/" + TC3Constants.DEFAULT_CREDENTIAL_SCOPE_END;
        return credentialScope;
    }

    /**
     * 生成规范请求串CanonicalRequest
     * 格式为
     * HTTPRequestMethod + '\n' +
     * CanonicalURI + '\n' +
     * CanonicalQueryString + '\n' +
     * CanonicalHeaders + '\n' +
     * SignedHeaders + '\n' +
     * HashedRequestPayload
     *
     * @param httpRequestMethod http请求方法（GET、POST）
     * @param signedHeaders 参与签名的头部信息
     * @param headers 所有http头部的字段值，字段key值为全小写字母
     * @param canonicalQueryString 发起 HTTP请求 URL中的查询字符串，对于POST请求，固定为空字符串""，对于GET请求，则为URL中问号（?）后面的字符串内容
     * @param payload 即http body
     */
    private static String generateCanonicalRequest(String httpRequestMethod, String signedHeaders,
            Map<String, String> headers, String canonicalQueryString, String payload) throws Exception {

        StringBuilder headersBf = new StringBuilder();
        if (MapUtils.isNotEmpty(headers)) {
            // SignedHeaders example: content-type;host
            // CanonicalHeaders examle: content-type:application/json; charset=utf-8\nhost:cvm.tencentcloudapi.com\n
            String[] signedHeadersGrp = signedHeaders.split(";");
            for (String h : signedHeadersGrp) {
                String headerValue = headers.get(h);
                if (StringUtils.isBlank(headerValue)) {
                    throw new TC3Exception("cannot find signed header " + h);
                }
                headersBf.append(h);
                headersBf.append(":");
                headersBf.append(headerValue);
                headersBf.append(TC3Constants.LINEFEED);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(httpRequestMethod);
        sb.append(TC3Constants.LINEFEED);
        // URI参数，API 3.0固定为正斜杠（/）
        sb.append(TC3Constants.DEFAULT_CANONICALURI);
        sb.append(TC3Constants.LINEFEED);
        canonicalQueryString = canonicalQueryString == null ? "" : canonicalQueryString;
        if ("POST".equals(httpRequestMethod)) {
            sb.append("");
        } else {
            sb.append(canonicalQueryString);
        }
        String canonicalHeaders = headersBf.toString();
        String hashedRequestPayload = "";
        if (StringUtils.isNotBlank(payload)) {
            hashedRequestPayload = sha256Hex(payload);
        }

        sb.append(TC3Constants.LINEFEED);
        sb.append(canonicalHeaders);
        sb.append(TC3Constants.LINEFEED);
        sb.append(signedHeaders);
        sb.append(TC3Constants.LINEFEED);
        sb.append(hashedRequestPayload);

        return sb.toString();
    }

    /**
     * 生成待签名字符串StringToSign
     * 格式为
     * Algorithm + \n +
     * RequestTimestamp + \n +
     * CredentialScope + \n +
     * HashedCanonicalRequest
     *
     * @param credentialScope 凭证范围，格式为date/service/tc3_request，包含日期、所请求的服务和终止字符串（tc3_request）
     * @param timestamp 请求时间戳，即请求头部的公共参数 X-TC-Timestamp 取值，取当前时间 UNIX 时间戳，精确到秒
     * @param canonicalRequest 规范请求串
     */
    private static String generateStringToSign(String credentialScope, String timestamp, String canonicalRequest)
            throws Exception {

        String hashedCanonicalRequest = sha256Hex(canonicalRequest);

        StringBuilder sb = new StringBuilder();
        sb.append(TC3Constants.DEFAULT_ALGORITHM);
        sb.append(TC3Constants.LINEFEED);
        sb.append(timestamp);
        sb.append(TC3Constants.LINEFEED);
        sb.append(credentialScope);
        sb.append(TC3Constants.LINEFEED);
        sb.append(hashedCanonicalRequest);

        return sb.toString();
    }

    /**
     * timestamp转date字符串
     *
     * @param timestamp timestamp字符串，精确到秒
     * @return
     */
    public static String timestampToDateStr(String timestamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 注意时区，否则容易出错
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date(Long.valueOf(timestamp + "000")));
    }

    /**
     * HmacSHA256算法计算
     *
     * @param key 密钥
     * @param msg 原文
     */
    private static byte[] hmac256(byte[] key, String msg) throws Exception {

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
        mac.init(secretKeySpec);
        return mac.doFinal(msg.getBytes(TC3Constants.UTF8));
    }

    /**
     * SHA256摘要，返回16进制结果
     *
     * @param s 原文
     * @return
     */
    private static String sha256Hex(String s) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] d = md.digest(s.getBytes(TC3Constants.UTF8));
        return Hex.encodeHexString(d);
    }
}
