package com.mt.bot.easyBot.common;

import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class HttpClientApiUtilsTest {

    @Test
    void httpPostRequest() {
        URIBuilder ub = new URIBuilder();
        ub.setPath("https://www.jikeq.com/public-api/query-amount");
        System.out.println(ub.getPath());
        ub.setPath("http://www.jikeq.com/public-api/query-amount");
        System.out.println(ub.getPath());
        ub.setPath("public-api/query-amount");
        ub.setHost("www.jikeq.com");
        ub.setScheme("https");
        System.out.println(ub.getPath());
        try {
            ub = new URIBuilder("https://www.jikeq.com/public-api/query-amount");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ub.getPath());
    }
}