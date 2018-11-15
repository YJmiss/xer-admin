package com.oservice.admin.common.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;


/**
 * 短信发送
 *
 * @author Yiyx
 * @version 1.0
 */
public class SMSUtils {
    /**
     * 短信发射器
     *
     * @param templateCode 模板代码
     * @param template 消息模板
* @param phone
     * @throws ApiException
     */
    public static boolean sendTelMessage(String templateCode, String template, String phone) throws ApiException {
        //调用短信接口
        TaobaoClient client = new DefaultTaobaoClient(ConfigConstant.SMSURL, ConfigConstant.APPKEY, ConfigConstant.SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("珍品母婴");
        req.setRecNum(phone);
        req.setSmsParamString(template);
        req.setSmsTemplateCode(templateCode);
        AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
        String respStr = rsp.getBody();
        return respStr.indexOf("OK")>=0&&respStr.indexOf("msg")>=0;

    }

}
