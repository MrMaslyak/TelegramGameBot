package org.example;

import com.google.gson.Gson;
import org.example.Interface.IDB;
import org.example.Interface.MessageSender;
import org.example.JsonStructures.CoffeeSettings;
import org.example.JsonStructures.MainStructJson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bot extends TelegramLongPollingBot implements MessageSender {
    private boolean isStartGame;
    private GameFunctions gameFunctions;
    private final SaveForData saveForData;
    private Gson gson = new Gson();
    private long idUserGlobal;
    private String textUserGlobal;
    public Bot(GameFunctions gameFunctions, SaveForData saveForData) {
        this.gameFunctions = gameFunctions;
        this.saveForData = saveForData;
    }



    public void setGameFunctions(GameFunctions gameFunctions) {
        this.gameFunctions = gameFunctions;
    }

    @Override
    public String getBotUsername() {
        return "MaslyakGameBot";
    }

    @Override
    public String getBotToken() {
        try {
            return SaveForData.getToken("passwordBot.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long idUser = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            IDB dataBase = DataBase.getInstance();
            idUserGlobal = idUser;
            textUserGlobal = text;

            if (text.equals("/start")) {
                sendButtons(idUser);
            }
            if (text.equals("/checkDB")) {
                ArrayList<String> data = dataBase.loadData();
                ArrayList<MainStructJson> mainStructJsons = new ArrayList<>();
                for (String line : data) {
                    MainStructJson settings = gson.fromJson(line, MainStructJson.class);


                    if (settings != null) {
                        mainStructJsons.add(settings);
                    } else {
                        sendText(idUser, "Ошибка при преобразовании данных из JSON: " + line);
                    }
                }
                sendText(idUser, "Данные из базы: " +"\n" +gson.toJson(mainStructJsons));
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            System.out.println("CallbackData: " + callbackData);
            gameFunctions.setIdUserGlobal(idUserGlobal);
            gameFunctions.setUserTextGlobal(textUserGlobal);
            if (callbackData.equals("Начать приготовление")) {
                gameFunctions.sendTypeCoffee(chatId);
                gameFunctions.currentGame(update);
            } else {
                gameFunctions.currentGame(update);
            }
        }
    }

    public void sendButtons(long idUser) {
        InlineKeyboardButton startButton = InlineKeyboardButton.builder()
                .text("Начать приготовление")
                .callbackData("Начать приготовление")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(startButton);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        sendMenu(idUser, "☕\uFE0F Приветствуем в 'Кофейный мастер'! ☕\uFE0F\n" +
                "\n" +
                "Вдохновитесь ароматом свежесваренного кофе и создайте свой идеальный напиток с нами! \uD83C\uDF89\n" +
                "\n" +
                "Выберите тип кофе, настройте крепость, добавьте любимые ингредиенты, и мы поможем вам приготовить идеальный кофе за несколько шагов. \uD83C\uDF1F\n" +
                "\n" +
                "Готовы начать? Давайте варить! \uD83D\uDC47", keyboardMarkup);
    }

    @Override
    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .parseMode("Markdown")
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}