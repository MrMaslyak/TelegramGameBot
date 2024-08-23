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
    private boolean isStartGame;
    private GameFunctions gameFunctions;
    private final SaveForData saveForData;

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
            return saveForData.getToken("passwordBot.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        long idUser;
        if (update.hasCallbackQuery()) {
            idUser = update.getCallbackQuery().getMessage().getChatId();
        }
    }


    public void sendButtons ( long idUser){
        InlineKeyboardButton next = InlineKeyboardButton.builder()
                .text("Start Game").callbackData("Start Game")
                .build();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboard = new ArrayList<>();
        keyboard.add(next);
        keyboardMarkup.setKeyboard(Collections.singletonList(keyboard));
        sendMenu(idUser, "\uD83C\uDF89 Добро пожаловать в игру \"Угадай Число\"! \uD83C\uDF89\n" +
                "\n" +
                "\uD83D\uDD22 Правила просты: бот загадывает число от 1 до 10, а ваша задача — угадать его!\n" +
                "\n" +
                "\uD83C\uDFAF Как играть:\n" +
                "\n" +
                "Нажмите на одну из кнопок ниже, чтобы выбрать число.\n" +
                "Бот сразу скажет, угадали вы или нет.\n" +
                "Если не угадали — не сдавайтесь, пробуйте снова! \uD83D\uDD04\n" +
                "\uD83C\uDFC6 Цель: угадать число как можно быстрее и получить поздравление от бота! \uD83C\uDF8A\n" +
                "\n" +
                "\uD83D\uDCE3 Готовы начать? Жмите на кнопку и пробуйте свои силы! Удачи! \uD83C\uDF40", keyboardMarkup);
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
            SendMessage sm = SendMessage.builder().chatId(who.toString())
                    .parseMode("HTML").text(txt)
                    .replyMarkup(kb).build();

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

