package com.oservice.admin.common.utils;

import cn.hutool.json.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Objects;


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
        System.out.print(respStr);
        JSONObject jsObject = new JSONObject(respStr);
        // TODO 判断这里有个返回结果好像是msg==ok就是发送成功,自己看下里面那个是那个值
        return Objects.equals(jsObject.get("msg"), "ok");

    }

}
