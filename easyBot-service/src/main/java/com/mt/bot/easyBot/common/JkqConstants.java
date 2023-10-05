package com.mt.bot.easyBot.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "jikeq")
@Data
public class JkqConstants {

    public String token;

    public String query_amount;

    public String register_device;

}
