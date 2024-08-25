package org.example;

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
    private GameFunctions gameFunctions;

    public Bot(GameFunctions gameFunctions) {
        this.gameFunctions = gameFunctions;
    }

    public void setGameFunctions(GameFunctions gameFunctions) {
        this.gameFunctions = gameFunctions;
    }

    @Override
    public String getBotUsername() {
        return "MaslyakQuestBot";
    }

    @Override
    public String getBotToken() {
        return "6658554435:AAFgsyHdgWEed1_MQ4Gxk0liU02TfOxUgpQ";//сделал уже так, просто хотел прям сделать для этого отдельного бота)
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long idUser = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            if (text.equals("/start")) {
                sendButtons(idUser);
            }
        } else if (update.hasCallbackQuery()) {
            gameFunctions.startGame(update);
        }
    }

    public void sendButtons(long idUser) {
        InlineKeyboardButton next = InlineKeyboardButton.builder()
                .text("Выбрать Тему").callbackData("Выбрать Тему")
                .build();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboard = new ArrayList<>();
        keyboard.add(next);
        keyboardMarkup.setKeyboard(Collections.singletonList(keyboard));
        sendMenu(idUser, "\uD83D\uDC4B Привет!\n" +
                "\n" +
                "\uD83C\uDF89 Добро пожаловать в RandomQuest Bot – твой персональный генератор случайных заданий! \uD83C\uDFAF\n" +
                "\n" +
                "\uD83D\uDE80 Что ты хочешь сегодня сделать? Выбери категорию ниже, и я предложу тебе интересное задание для твоего дня:\n" +
                "\n" +
                "\uD83C\uDFCB\uFE0F Фитнес – время потренироваться!\n" +
                "\uD83C\uDFAE Развлечения – пора весело провести время!\n" +
                "\uD83D\uDCDA Саморазвитие – узнай что-то новое!\n" +
                "\uD83D\uDCAC Общение – найди повод пообщаться!\n" +
                "\uD83C\uDF73 Кулинария – приготовь что-то вкусное!\n" +
                "\uD83D\uDC47 Выбери категорию и начни приключение!", keyboardMarkup);
    }

    @Override
    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb) {
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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


}
