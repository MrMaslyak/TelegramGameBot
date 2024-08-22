package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        SaveForData saveForData = new SaveForData();
        Bot bot = new Bot(null, saveForData);

        GameFunctions gameFunctions = new GameFunctions(bot);
        bot.setGameFunctions(gameFunctions);

        botsApi.registerBot(bot);
    }
}
