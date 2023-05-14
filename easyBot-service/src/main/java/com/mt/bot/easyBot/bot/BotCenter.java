package com.mt.bot.easyBot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Main class to create all bots
 * @date 20 of June of 2015
 */
@Slf4j
@Component
public class BotCenter implements CommandLineRunner {
    private static final String LOGTAG = "MAIN";

    @Autowired
    CommandsHandler commandsHandler;
//    @Autowired
//    RssSubBot rssSubBot;


    public void init() {

        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = createTelegramBotsApi();
//            telegramBotsApi.registerBot(rssSubBot);
            telegramBotsApi.registerBot(commandsHandler);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private static TelegramBotsApi createTelegramBotsApi() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi;
        telegramBotsApi = createLongPollingTelegramBotsApi();

//        if (!BuildVars.useWebHook) {
//            // Default (long polling only)
//
//        } else if (!BuildVars.pathToCertificatePublicKey.isEmpty()) {
//            // Filled a path to a pem file ? looks like you're going for the self signed option then, invoke with store and pem file to supply.
//            telegramBotsApi = createSelfSignedTelegramBotsApi();
//            telegramBotsApi.registerBot(new WebHookExampleHandlers());
//        } else {
//            // Non self signed, make sure you've added private/public and if needed intermediate to your cert-store.
//            telegramBotsApi = createNoSelfSignedTelegramBotsApi();
//            telegramBotsApi.registerBot(new WebHookExampleHandlers());
//        }
        return telegramBotsApi;
    }

    /**
     * @return TelegramBotsApi to register the bots.
     * @brief Creates a Telegram Bots Api to use Long Polling (getUpdates) bots.
     */
    private static TelegramBotsApi createLongPollingTelegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    /**
     * @return TelegramBotsApi to register the bots.
     * @brief Creates a Telegram Bots Api to use Long Polling bots and webhooks bots with self-signed certificates.
     * @note https://core.telegram.org/bots/self-signed#java-keystore for generating a keypair in store and exporting the pem.
     * @note Don't forget to split the pem bundle (begin/end), use only the public key as input!
    private static TelegramBotsApi createSelfSignedTelegramBotsApi() throws TelegramApiException {
    return new TelegramBotsApi(BuildVars.pathToCertificateStore, BuildVars.certificateStorePassword, BuildVars.EXTERNALWEBHOOKURL, BuildVars.INTERNALWEBHOOKURL, BuildVars.pathToCertificatePublicKey);
    }

    /**
     * @return TelegramBotsApi to register the bots.
     * @brief Creates a Telegram Bots Api to use Long Polling bots and webhooks bots with no-self-signed certificates.
     * @note Coming from a set of pem files here's one way to do it:
     * @code{.sh} openssl pkcs12 -export -in public.pem -inkey private.pem > keypair.p12
     * keytool -importkeystore -srckeystore keypair.p12 -destkeystore server.jks -srcstoretype pkcs12
     * #have (an) intermediate(s) to supply? first:
     * cat public.pem intermediate.pem > set.pem (use set.pem as -in)
     * @endcode private static TelegramBotsApi createNoSelfSignedTelegramBotsApi() throws TelegramApiException {
    return new TelegramBotsApi(BuildVars.pathToCertificateStore, BuildVars.certificateStorePassword, BuildVars.EXTERNALWEBHOOKURL, BuildVars.INTERNALWEBHOOKURL);
    }  */
}
