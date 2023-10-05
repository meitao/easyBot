package com.mt.bot.easyBot.bot.commod;

import com.mt.bot.easyBot.http.JkqHttpInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This commands stops the conversation with the bot.
 * Bot won't respond to user until he sends a start command
 * 极客签的bot 命令
 *
 * @author Timo Schulz (Mit0x2)
 */
@Component
@Slf4j
public class JkqRegisterSSCommand extends AbstractJkqRegisterCommand {

    /**
     * Construct
     */
    public JkqRegisterSSCommand() {
        super("jkqSS","With this command you can register SS jkq cert");
    }
    @Autowired
    JkqHttpInvoke jkqHttpInvoke;

    @Override
    protected String calljkq(String udid) {
        //调用极客签url
        return  jkqHttpInvoke.register_deviceSS(udid);
    }
}
