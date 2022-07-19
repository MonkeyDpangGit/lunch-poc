package com.tencent.common.context;

/**
 * AppCtx
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: AppCtx
 */
public class AppCtx {

    /**
     * 应用程序上下文与 ThreadLocal 绑定
     */
    private static ThreadLocal<AppCtx> threadLocal = new ThreadLocal<>();

    private String requestId;

    private TenantCtx tenantCtx;

    private SubjectCtx subjectCtx;

    private ClientCtx clientCtx;

    private AppCtx() {
    }

    /**
     * 初始化
     */
    public static void init() {
        AppCtx appCtx = new AppCtx();
        threadLocal.set(appCtx);
    }

    /**
     * 获取
     *
     * @return the app ctx
     */
    public static AppCtx get() {
        AppCtx appCtx = threadLocal.get();
        if (appCtx == null) {
            init();
            return get();
        }
        return appCtx;
    }


    /**
     * 设置
     *
     * @param appCtx the app ctx
     */
    public static void setCtx(AppCtx appCtx) {
        threadLocal.set(appCtx);
    }

    /**
     * 销毁
     */
    public static void destroy() {
        if (threadLocal != null) {
            threadLocal.remove();
        }
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public TenantCtx getTenantCtx() {
        return tenantCtx;
    }

    public void setTenantCtx(TenantCtx tenantCtx) {
        this.tenantCtx = tenantCtx;
    }

    public SubjectCtx getSubjectCtx() {
        return subjectCtx;
    }

    public void setSubjectCtx(SubjectCtx subjectCtx) {
        this.subjectCtx = subjectCtx;
    }

    public ClientCtx getClientCtx() {
        return clientCtx;
    }

    public void setClientCtx(ClientCtx clientCtx) {
        this.clientCtx = clientCtx;
    }
}
