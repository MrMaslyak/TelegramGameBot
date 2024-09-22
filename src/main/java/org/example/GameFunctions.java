package org.example;

import com.google.gson.Gson;
import org.example.DataBaseFunc.DataBase;
import org.example.DataBaseFunc.MainJson;
import org.example.DataBaseFunc.StartGameDB;
import org.example.DataBaseFunc.UserSetting;
import org.example.Inteface.IDB;
import org.example.Inteface.MessageSender;
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
    private Gson gson = new Gson();
    private Update update;
    private IDB dataBase = DataBase.getInstance();

    public GameFunctions(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void startGame(List<UserSetting> users) {
        if (users.size() >= 2) {
            messageSender.sendText(users.get(0).getChatID(), "Игра на Удачу началась! \n" + users.get(0).getName() + " VS " + users.get(1).getName());
            messageSender.sendText(users.get(1).getChatID(), "Игра на Удачу началась! \n" + users.get(0).getName() + " VS " + users.get(1).getName());
            messageSender.sendText(users.get(0).getChatID(), "\uD83D\uDC9AПрячь свой алмаз удачи\uD83D\uDC9A");
            messageSender.sendText(users.get(1).getChatID(), "\uD83D\uDE42\u200D↕\uFE0F Жди \uD83D\uDE42\u200D↕\uFE0F");
            sendHideButtons(users.get(0).getChatID());
            isPlay = false;


            dataBase.save_user(users.get(0).getChatID(),users.get(0).getName(), true);
            dataBase.save_user(users.get(1).getChatID(),users.get(1).getName(), false);

        }
    }

    public void currentGame(Update update, List<UserSetting> userSettings) {
        String receivedData = update.getCallbackQuery().getData();

        if (!isHidden) {
            answer = receivedData;
            messageSender.sendText(userSettings.get(0).getChatID(), "\uD83E\uDE75 Жди, пока аппонент выберет в какой ящик ты спрятал \uD83E\uDE75");
            messageSender.sendText(userSettings.get(1).getChatID(), "\uD83E\uDE75 Ищи Алмаз! \uD83E\uDE75");
            sendSearchButtons(userSettings.get(1).getChatID());
            isHidden = true;
        } else {
            if (answer.equals(receivedData)) {
                messageSender.sendText(userSettings.get(1).getChatID(), "Угадал! \nТы нашел алмаз! ❤\uFE0F\u200D\uD83D\uDD25");
                messageSender.sendText(userSettings.get(0).getChatID(), "<----> <----> <--->");
                messageSender.sendText(userSettings.get(0).getChatID(), "Ты проиграл! ☹\uFE0F");
                dataBase.save_game_user_stats(userSettings.get(1).getName(), userSettings.get(0).getName());

            } else {
                messageSender.sendText(userSettings.get(1).getChatID(), "Неправильно, алмаз был спрятан в " + answer + "!");
                messageSender.sendText(userSettings.get(1).getChatID(), "Ты проиграл! ☹\uFE0F");
                messageSender.sendText(userSettings.get(0).getChatID(), "<----> <----> <--->");
                messageSender.sendText(userSettings.get(0).getChatID(), "\uD83D\uDCA5 Ты победил! \uD83D\uDCA5");
                dataBase.save_game_user_stats(userSettings.get(0).getName(), userSettings.get(1).getName());
            }
            offerNewGame(userSettings);
        }
    }

    private void sendHideButtons(long chatID) {
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("Ящик 1").callbackData("Ящик 1")
                .build();

        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text("Ящик 2").callbackData("Ящик 2")
                .build();

        InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                .text("Ящик 3").callbackData("Ящик 3")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button1);
        row.add(button2);
        row.add(button3);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        messageSender.sendMenu(chatID, "❓\uFE0F Выберите, куда спрятать алмаз: ", keyboardMarkup);
    }

    private void sendSearchButtons(long chatID) {
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("Ящик 1").callbackData("Ящик 1")
                .build();

        InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                .text("Ящик 2").callbackData("Ящик 2")
                .build();

        InlineKeyboardButton button3 = InlineKeyboardButton.builder()
                .text("Ящик 3").callbackData("Ящик 3")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button1);
        row.add(button2);
        row.add(button3);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        messageSender.sendMenu(chatID, "Попробуй найти алмаз:", keyboardMarkup);
    }

    public void resetGame(List<UserSetting> userSettings) {
        isHidden = false;
        answer = "";
        isPlay = true;
    }

    public void offerNewGame(List<UserSetting> userSettings) {
        messageSender.sendText(userSettings.get(0).getChatID(), "🎉 Игра закончена! 🎉\n\nХотите сыграть еще раз? Нажмите кнопку ниже, чтобы начать новую игру.");
        messageSender.sendText(userSettings.get(1).getChatID(), "🎉 Игра закончена! 🎉\n\nХотите сыграть еще раз? Нажмите кнопку ниже, чтобы начать новую игру.");
        sendNewGameButton(userSettings.get(0).getChatID());
        sendNewGameButton(userSettings.get(1).getChatID());
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

    public void handleNewGameRequest(Update update, List<UserSetting> userSettings) {
        if (update.getCallbackQuery().getData().equals("Start New Game")) {
            resetGame(userSettings);
            startGame(userSettings);
        }
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
