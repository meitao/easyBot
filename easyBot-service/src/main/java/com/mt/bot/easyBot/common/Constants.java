package com.mt.bot.easyBot.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "bot")
@Data
public class Constants {
    public String getPorxy_host() {
        return porxy_host;
    }

    public void setPorxy_host(String porxy_host) {
        this.porxy_host = porxy_host;
    }

    public String porxy_host;

    public int porxy_port;

    public String bot_token;

    public String bot_username;

    public String twitter_user;

    public String weibo_hot;

    public boolean isProxy;

}
