package com.mt.bot.easyBot.bot.commod;

import com.mt.bot.easyBot.bot.RssSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

/**
 * This commands stops the conversation with the bot.
 * Bot won't respond to user until he sends a start command
 *
 * @author Timo Schulz (Mit0x2)
 */
@Component
public class RssCommand extends BotCommand {

    /**
     * Construct
     */
    public RssCommand() {
        super("rss", "With this command you can sub the Rss");
    }
    @Autowired
    RssSub rssSub;
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        String userName = user.getFirstName() + " " + user.getLastName();
        String type = null;
        String param = null;
        for (int i = 0; i < arguments.length; i++) {
            if (i == 0)
                type = arguments[0];
            if (i == 1)
                param = arguments[1];
        }


        List<String> list = rssSub.getRss(type, param);

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(list.get(0));
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
