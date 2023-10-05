package com.mt.bot.easyBot.http;

import com.mt.bot.easyBot.common.HttpClientApiUtils;
import com.mt.bot.easyBot.common.JkqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JkqHttpInvoke {

    @Autowired
    JkqConstants jkqConstants;

    public String query_amount() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("token", jkqConstants.token);
        String result = null;
        try {
            result = HttpClientApiUtils.httpGetRequest(jkqConstants.query_amount, headers, new HashMap<>());
            log.info("返回结果:{}", result);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String register_device(String udid, String productCode) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("token", jkqConstants.token);

        Map<String, Object> param = new HashMap<>();
        param.put("udid", udid);
        param.put("productCode", productCode);
//        param.put("appleAccountId", 88888);
        param.put("usePrivateAccount", false);
        String result = null;
        try {
            result = HttpClientApiUtils.httpPostRequest(jkqConstants.register_device, headers, param);
            log.info("返回结果:{}", result);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    public String register_deviceSS(String udid) {
        return this.register_device(udid, "SS");
    }

    public String register_deviceYY(String udid) {
        return this.register_device(udid, "YY");
    }

    public String register_deviceNO(String udid) {
        return this.register_device(udid, "NO");
    }

}
