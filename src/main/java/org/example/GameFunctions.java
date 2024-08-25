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



    public void startGame(Update update) {

    }

    private void sendButtons(long chatID) {

    }





    public void offerNewGame() {

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

    public void handleNewGameRequest(Update update) {
        if (update.getCallbackQuery().getData().equals("Start New Game")) {
            startGame(update);
        }
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
