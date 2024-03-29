package com.mt.bot.easyBot.bot;


import com.mt.bot.easyBot.bot.commod.*;
import com.mt.bot.easyBot.common.Constants;
import com.mt.bot.easyBot.common.Emoji;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * This handler mainly works with commands to demonstrate the Commands feature of the API
 *
 * @author Timo Schulz (Mit0x2)
 */
@Slf4j
@Component
public class CommandsHandler extends TelegramLongPollingCommandBot {

    public static final String LOGTAG = "COMMANDSHANDLER";

    @Autowired
    Constants constants;



    /**
     * Constructor.
     */

    public CommandsHandler(DefaultBotOptions botOptions, JikeqCommand jikeqCommand, RssCommand rssCommand
            , JkqRegisterSSCommand jkqRegisterCommand
            , JkqRegisterNOCommand jkqRegisterNOCommand
            , JkqRegisterYYCommand jkqRegisterYYCommand) {
        super(botOptions);

        HelpCommand helpCommand = new HelpCommand(this);

//        register(new HelloCommand());
//        register(new StartCommand());
//        register(new StopCommand());
        register(rssCommand);
        register(jikeqCommand);
        register(jkqRegisterCommand);
        register(jkqRegisterNOCommand);
        register(jkqRegisterYYCommand);
        register(helpCommand);

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId());
            commandUnknownMessage.setText("The command '" + message.getText() + "' is not known by this bot. Here comes some help " + Emoji.AMBULANCE);
            try {
                absSender.execute(commandUnknownMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        });
    }


    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            log.info("  ----chatId:{}------", message.getChatId());
            //@Todo权限控制
            if (message.hasText()) {
                send(message.getChatId(), "\"Hey heres your message:\\n\" " + message.getText());
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            Message message = callbackQuery.getMessage();
            send(message.getChatId(), "\"Hey heres your message:\\n\" " + data);
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

    @Override
    public String getBotToken() {
        return constants.bot_token;
    }

    @Override
    public String getBotUsername() {
        return constants.bot_username;
    }

}