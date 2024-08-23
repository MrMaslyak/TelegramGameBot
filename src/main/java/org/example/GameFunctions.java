package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFunctions {
    private final MessageSender messageSender;
    private boolean isHidden = false;
    private String answer = "";
    private boolean isPlay;

    public GameFunctions(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void startGame(List<User> users) {

    }

    public void currentGame(Update update, List<User> users) {

    }

    private void sendHideButtons(long chatID) {

    }

    private void sendSearchButtons(long chatID) {

    }

    public void resetGame(List<User> users) {
        isHidden = false;
        answer = "";
        isPlay = true;
    }

    public void offerNewGame(List<User> users) {
        messageSender.sendText(users.get(0).getChatID(), "🎉 Игра закончена! 🎉\n\nХотите сыграть еще раз? Нажмите кнопку ниже, чтобы начать новую игру.");
        messageSender.sendText(users.get(1).getChatID(), "🎉 Игра закончена! 🎉\n\nХотите сыграть еще раз? Нажмите кнопку ниже, чтобы начать новую игру.");
        sendNewGameButton(users.get(0).getChatID());
        sendNewGameButton(users.get(1).getChatID());
    }

    private void sendNewGameButton(long chatID) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Начать новую игру").callbackData("Start New Game")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        messageSender.sendMenu(chatID, "🎮 Нажмите кнопку ниже, чтобы начать новую игру!", keyboardMarkup);
    }

    public void handleNewGameRequest(Update update, List<User> users) {
        if (update.getCallbackQuery().getData().equals("Start New Game")) {
            resetGame(users);
            startGame(users);
        }
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
