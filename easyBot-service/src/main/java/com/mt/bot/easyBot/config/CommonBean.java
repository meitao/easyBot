package com.mt.bot.easyBot.config;

import com.mt.bot.easyBot.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class CommonBean {

    @Autowired
    Constants constants;
    @Bean
    public DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyHost(constants.porxy_host);
        botOptions.setProxyPort(constants.porxy_port);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return botOptions;
    }
}
