package com.mt.bot.easyBot.bot;

import com.mt.bot.easyBot.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

/**
 * @author wucode * @email wucode@163.com * @date 2022/7/26
 */
@Slf4j
@Component
public class RssSubBot extends TelegramLongPollingBot {

    @Autowired
    Constants constants;

    @Autowired
    RssSub rssSub;
    public RssSubBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public String getBotUsername() {
        return constants.bot_username;
    }

    @Override
    public String getBotToken() {
        return constants.bot_token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            log.info("  ----chatId:{}------", chatId);
            List<String> list = rssSub.getRss("weibo_hot", "");
            send(chatId, list.get(0));
        }

    }

    private boolean send(Long chatId, String msg) {
        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(msg)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
        return true;
    }

}