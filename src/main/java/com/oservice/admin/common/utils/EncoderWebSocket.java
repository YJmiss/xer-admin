package com.oservice.admin.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;
import java.util.Map;

public class EncoderWebSocket implements Encoder.Text<Object> {

    @Override
    public void init(EndpointConfig config) {
        // TODO Auto-generated method stub
        System.out.println("--------------EncoderWebSocket初始化-------------");
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        System.out.println("--------------EncoderWebSocket销毁-------------");
    }

    /**
     * 传递Map类型
     * @param map
     * @return
     */
    public String encode(Map<String, List> map) throws EncodeException {
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 如果你传递的是一个类，则使用如下写法
     *
     * @param message
     * @return
     * @throws Exception
     */
    @Override
    public String encode(Object message) {
        JSONObject object = new JSONObject(message);
        return object.toString();
    }
}
