package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFunctions {
    private final MessageSender messageSender;
    private String answer = "";
    private boolean isPlay;
    private int randomBotNum = 0;

    public GameFunctions(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void currentGame(Update update) {
        randomBotNum = (int) (Math.random() * 10) + 1;
        long idUser = update.getCallbackQuery().getMessage().getChatId();
        sendSearchButtons(idUser);
        isPlay = true;
        answer = "";
        System.out.println("randomBotNum = " + randomBotNum);
    }

    void sendSearchButtons(long chatID) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text("Число " + i)
                    .callbackData("Число " + i)
                    .build();
            if (rows.isEmpty() || rows.get(rows.size() - 1).size() == 5) {
                rows.add(new ArrayList<>());
            }
            rows.get(rows.size() - 1).add(button);
        }

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        messageSender.sendMenu(chatID, "❓\uFE0F Выберите, какое число выбрал бот: ", keyboardMarkup);
    }

    private void sendNewGameButton(long chatID) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Начать новую игру")
                .callbackData("Start New Game")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        messageSender.sendMenu(chatID, "🎮 Нажмите кнопку ниже, чтобы начать новую игру!", keyboardMarkup);
    }

    public void handleNewGameRequest(Update update, List<User> users) {
        if (update.getCallbackQuery().getData().equals("Start New Game")) {
            currentGame(update);
        }
    }



    public void setPlay(boolean play) {
        isPlay = play;
    }

    public void checkNum(String data, long idUser) {
        if (data.equals("Число " + randomBotNum)) {
            answer = "🎉 Вы выиграли! 🎉";
        }
        if (!data.equals("Число " + randomBotNum)) {
            answer = "��� Вы проиграли! ���";
        }
        isPlay = false;
        messageSender.sendText(idUser, answer);
        sendNewGameButton(idUser);

    }
}
