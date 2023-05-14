package com.mt.bot.easyBot.bot.commod;

import com.mt.bot.easyBot.bot.RssSub;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This commands starts the conversation with the bot
 *
 * @author Timo Schulz (Mit0x2)
 */
public class StartCommand extends BotCommand {

    public static final String LOGTAG = "STARTCOMMAND";

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("start the bot!");
        String userName = user.getFirstName() + " " + user.getLastName();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        keyboard.add(row);

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("/help");
        row.add(keyboardButton);


        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText("/stop");
        row.add(keyboardButton1);


        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);


        List<List<InlineKeyboardButton>> inlineKeyboards = new ArrayList<>();

        List<InlineKeyboardButton> keyboardButtonList = new ArrayList<>();
        inlineKeyboards.add(keyboardButtonList);
        InlineKeyboardButton inlineKeyboardButton = InlineKeyboardButton.builder()
                .callbackData("help")
                .text("/help")
                .build();

        InlineKeyboardButton stopinlineKeyboardButton = InlineKeyboardButton.builder()
                .callbackData("stop")
                .switchInlineQuery("/stop")
                .text("/stop")
                .build();


        keyboardButtonList.add(inlineKeyboardButton);
        keyboardButtonList.add(stopinlineKeyboardButton);


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(inlineKeyboards);


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId().toString());
        sendMessage.setText(messageBuilder.toString());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}