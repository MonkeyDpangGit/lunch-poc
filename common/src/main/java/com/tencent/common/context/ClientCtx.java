package com.tencent.common.context;

/**
 * ClientCtx
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: ClientCtx
 */
public class ClientCtx {

    /**
     * 客户端
     */
    public String clientAgent;
    /**
     * 客户端IP
     */
    public String clientIP;
    /**
     * 客户端操作系统
     */
    public String clientOS;
    /**
     * 设备编码
     */
    public String deviceCode;
    /**
     * 访问的Host
     */
    public String host;
    /**
     * 会话ID
     */
    public String sessionId;
    /**
     * 语言标识
     */
    public String language;

    public String getClientAgent() {
        return clientAgent;
    }

    public void setClientAgent(String clientAgent) {
        this.clientAgent = clientAgent;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getClientOS() {
        return clientOS;
    }

    public void setClientOS(String clientOS) {
        this.clientOS = clientOS;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
