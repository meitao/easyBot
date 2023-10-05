package com.mt.bot.easyBot.bot.commod;

import com.mt.bot.easyBot.bot.RssSub;
import com.mt.bot.easyBot.http.JkqHttpInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

/**
 * This commands stops the conversation with the bot.
 * Bot won't respond to user until he sends a start command
 * 极客签的bot 命令
 *
 * @author Timo Schulz (Mit0x2)
 */
@Component
@Slf4j
public class JikeqCommand extends BotCommand {

    /**
     * Construct
     */
    public JikeqCommand() {
        super("jkq", "With this command you can query jkq account ");
    }

    @Autowired
    JkqHttpInvoke jkqHttpInvoke;

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        String userName = user.getFirstName() + " " + user.getLastName();
        //解析命令中的字符串
        String udid = null;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0)
                udid = arguments[0];
        }
        SendMessage answer = new SendMessage();
        try {
            //调用极客签url
            answer.setChatId(chat.getId().toString());
            answer.setText(jkqHttpInvoke.query_amount());
        } catch (Exception e) {
            log.error("",e);
            answer.setChatId(chat.getId().toString());
            answer.setText("失败:" + e.getMessage());
        }
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
