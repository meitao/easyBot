package com.mt.bot.easyBot.bot.commod;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * This commands stops the conversation with the bot.
 * Bot won't respond to user until he sends a start command
 *
 * @author Timo Schulz (Mit0x2)
 */
public class StopCommand extends BotCommand {

    public static final String LOGTAG = "STOPCOMMAND";

    /**
     * Construct
     */
    public StopCommand() {
        super("stop", "With this command you can stop the Bot");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

            String userName = user.getFirstName() + " " + user.getLastName();

            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Good bye " + userName + "\n" + "Hope to see you soon!");

            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }
}
