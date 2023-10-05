package com.mt.bot.easyBot.bot.commod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.bot.easyBot.bean.JkqRegister;
import com.mt.bot.easyBot.http.JkqHttpInvoke;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Objects;

/**
 * This commands stops the conversation with the bot.
 * Bot won't respond to user until he sends a start command
 * 极客签的bot 命令
 *
 * @author Timo Schulz (Mit0x2)
 */
@Component
@Slf4j
public abstract class AbstractJkqRegisterCommand extends BotCommand {

    /**
     * Construct
     */
    public AbstractJkqRegisterCommand(String commandIdentifier, String dsc) {
        super(commandIdentifier, dsc);
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
        Assert.notNull(udid, "用户:" + userName + " 传参错误！");
        SendMessage answer = new SendMessage();
        //调用极客签url

        answer.setChatId(chat.getId().toString());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String msg = calljkq(udid);
            JkqRegister jkqRegister = objectMapper.readValue(msg, JkqRegister.class);
            SendDocument sendDocument =  SendDocument.builder()
                    .chatId(chat.getId())
                    .document(new InputFile(new ByteArrayInputStream(Base64.getDecoder().decode( jkqRegister.getP12())),userName+".p12")).build();
            absSender.execute(sendDocument);

            sendDocument.setDocument(new InputFile(new ByteArrayInputStream(Base64.getDecoder().decode( jkqRegister.getMp())),userName+".mobileprovision"));
            absSender.execute(sendDocument);
        } catch (JsonProcessingException e) {
            log.error("", e);
            answer.setChatId(chat.getId().toString());
            answer.setText("失败:" + e.getMessage());
        } catch (TelegramApiException e) {
            log.error("", e);
            answer.setChatId(chat.getId().toString());
            answer.setText("失败:" + e.getMessage());
        }
    }

    protected abstract String calljkq(String udid);
}
