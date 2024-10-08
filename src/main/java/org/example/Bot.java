package org.example;

import com.google.gson.Gson;
import org.example.DataBaseFunc.DataBase;
import org.example.DataBaseFunc.MainJson;
import org.example.DataBaseFunc.UserSetting;
import org.example.Inteface.IDB;
import org.example.Inteface.MessageSender;
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
    private ArrayList<UserSetting> userSettings = new ArrayList<>();
    private boolean isStartGame;
    private GameFunctions gameFunctions;
    private final SaveForData saveForData;
    private Gson gson = new Gson();

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
        IDB dataBase = DataBase.getInstance();
        gameFunctions.setUpdate(update);
        if (update.hasCallbackQuery()) {
            idUser = update.getCallbackQuery().getMessage().getChatId();
            if (update.getCallbackQuery().getData().equals("Start New Game")) {
                if (userSettings.size() > 1) {
                    gameFunctions.handleNewGameRequest(update, userSettings);
                } else {
                    sendText(idUser, "\uD83C\uDD9A Нет пока месть аппонента \uD83C\uDD9A");
                }
                return;
            }
            if ((update.getCallbackQuery().getData().equals("load"))) {
                ArrayList<String> data = dataBase.loadData();
                ArrayList<MainJson> mainStructJsons = new ArrayList<>();
                for (String line : data) {
                    MainJson settings = gson.fromJson(line, MainJson.class);
                    if (settings != null) {
                        mainStructJsons.add(settings);
                    } else {
                        sendText(idUser, "Ошибка при преобразовании данных из JSON: " + line);
                    }
                }
                sendText(idUser, "Данные из базы: " +"\n" +gson.toJson(mainStructJsons));
            }
            if (userSettings.size() > 1) {
                if (isStartGame) {
                    if (gameFunctions.isPlay()){
                        gameFunctions.startGame(userSettings);
                        return;
                    }
                    gameFunctions.currentGame(update, userSettings);
                } else {
                    isStartGame = true;
                    gameFunctions.startGame(userSettings);
                }
            } else {
                sendText(idUser, "\uD83C\uDD9A Нет пока месть аппонента \uD83C\uDD9A");
            }
        } else if (update.hasMessage()) {
            idUser = update.getMessage().getFrom().getId();
            String name = update.getMessage().getFrom().getFirstName();
            String textUser = update.getMessage().getText();
            if (textUser.equals("/start")) {
                userSettings.add(new UserSetting(idUser, name));
                sendButtons(idUser);
            }
        }
    }

    public void sendButtons(long idUser) {
        InlineKeyboardButton next = InlineKeyboardButton.builder()
                .text("Start Game").callbackData("Start Game")
                .build();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboard = new ArrayList<>();
        keyboard.add(next);
        keyboardMarkup.setKeyboard(Collections.singletonList(keyboard));
        sendMenu(idUser, "\uD83D\uDC8E Добро пожаловать в игру \"Прятки с Алмазом\"! \uD83D\uDC8E\n" +
                "\n" +
                "✨ Правила просты: Один игрок прячет алмаз за одной из кнопок, а второй игрок должен угадать, где он спрятан! Если угадаешь – победа твоя! \uD83C\uDF89 Если нет – алмаз останется в руках прячущего! \uD83D\uDE1C\n" +
                "\n" +
                "\uD83D\uDC65 Подключай друга и начинай игру!\n" +
                "\uD83D\uDC65 Просто пусть зайдет в бота и нажмет кнопку Start Game!\n" +
                "\n" +
                "➡\uFE0F Жми /start и попробуй свои силы в этой увлекательной игре! Удачи! \uD83C\uDF40", keyboardMarkup);
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
