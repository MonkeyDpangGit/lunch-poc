package com.tencent.common.context.helper;

import com.tencent.common.constants.ApiConstants;
import com.tencent.common.context.ClientCtx;
import com.tencent.common.utils.RequestUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

/**
 * ClientCtxHelper
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: ClientCtxHelper
 */
@Component
public class ClientCtxHelper {

    /**
     * 使用Http请求构造客户端上下文
     *
     * @param request http请求
     * @return
     */
    public ClientCtx buildByHttpRequest(HttpServletRequest request) {

        ClientCtx clientCtx = new ClientCtx();

        clientCtx.setClientOS(RequestUtils.parseClientOS(request));
        clientCtx.setClientAgent(RequestUtils.parseUserAgent(request));
        clientCtx.setClientIP(RequestUtils.parseClientIP(request));
        clientCtx.setDeviceCode(RequestUtils.parseDeviceCode(request));
        clientCtx.setSessionId(request.getSession().getId());
        clientCtx.setHost(RequestUtils.parseHost(request));
        clientCtx.setLanguage(request.getLocale().getLanguage());

        return clientCtx;
    }

    /**
     * 使用CAM输入参数构造客户端上下文
     *
     * @param camInput CAM输入参数
     * @return
     */
    public ClientCtx buildByCamInput(Map camInput) {

        ClientCtx clientCtx = new ClientCtx();

        String language = MapUtils.getString(camInput, ApiConstants.LANGUAGE, "zh");
        clientCtx.setLanguage(language);

        Map camContext = MapUtils.getMap(camInput, ApiConstants.CAM_CONTEXT);
        clientCtx.setClientIP(MapUtils.getString(camContext, ApiConstants.CLIENT_IP));

        return clientCtx;
    }
}
