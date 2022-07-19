package com.tencent.common.context;

/**
 * SubjectCtx
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: SubjectCtx
 */
public class SubjectCtx {

    private String userId;

    private String userName;

    private String sessionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
