package org.example;

import com.google.gson.Gson;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFunctions {
    private final MessageSender messageSender;
    private final ArrayList<String> description = new ArrayList<>();
    private Gson gson = new Gson();

    public GameFunctions(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void currentGame(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (callbackData) {
            case "TypeCoffee_Espresso":
                sendCoffeeSize(chatId);
                description.add("Тип кофе: Эспрессо");
                break;
            case "TypeCoffee_Cappuccino":
                sendCoffeeSize(chatId);
                description.add("Тип кофе: Капучино");
                break;
            case "TypeCoffee_Latte":
                sendCoffeeSize(chatId);
                description.add("Тип кофе: Латте");
                break;
            case "TypeCoffee_Americano":
                sendCoffeeSize(chatId);
                description.add("Тип кофе: Американо");
                break;
            case "TypeCoffee_Raf":
                sendCoffeeSize(chatId);
                description.add("Тип кофе: Раф");
                break;
            case "CoffeeSize_Small":
                sendCoffeeStrength(chatId);
                description.add("Размер кофе: Маленький");
                break;
            case "CoffeeSize_Medium":
                sendCoffeeStrength(chatId);
                description.add("Размер кофе: Средний");
                break;
            case "CoffeeSize_Large":
                sendCoffeeStrength(chatId);
                description.add("Размер кофе: Большой");
                break;
            case "CoffeeStrength_Light":
                sendCoffeeIngredients(chatId);
                description.add("Крепость кофе: Легкий");
                break;
            case "CoffeeStrength_Medium":
                sendCoffeeIngredients(chatId);
                description.add("Крепость кофе: Средний");
                break;
            case "CoffeeStrength_Strong":
                sendCoffeeIngredients(chatId);
                description.add("Крепость кофе: Крепкий");
                break;
            case "CoffeeIngredients_Milk":
                sendCoffeeTemp(chatId);
                description.add("Ингредиенты: Молоко");
                break;
            case "CoffeeIngredients_Tea":
                sendCoffeeTemp(chatId);
                description.add("Ингредиенты: Чай");
                break;
            case "CoffeeIngredients_Syrup":
                sendCoffeeTemp(chatId);
                description.add("Ингредиенты: Сироп");
                break;
            case "CoffeeIngredients_None":
                sendCoffeeTemp(chatId);
                description.add("Ингредиенты: Без добавок");
                break;
            case "CoffeeTemp_Hot":
                sendCoffeeDone(chatId, update);
                description.add("Температура кофе: Горячий");
                break;
            case "CoffeeTemp_Warm":
                sendCoffeeDone(chatId, update);
                description.add("Температура кофе: Теплый");
                break;
            case "CoffeeTemp_Cold":
                sendCoffeeDone(chatId, update);
                description.add("Температура кофе: Холодный");
                break;
        }
    }

    void sendTypeCoffee(long chatID) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("☕\uFE0F Эспрессо").callbackData("TypeCoffee_Espresso").build());
        row.add(InlineKeyboardButton.builder().text("☕\uFE0F Капучино").callbackData("TypeCoffee_Cappuccino").build());
        row.add(InlineKeyboardButton.builder().text("☕\uFE0F Латте").callbackData("TypeCoffee_Latte").build());
        row.add(InlineKeyboardButton.builder().text("☕\uFE0F Американо").callbackData("TypeCoffee_Americano").build());
        row.add(InlineKeyboardButton.builder().text("☕\uFE0F Раф").callbackData("TypeCoffee_Raf").build());
        List<List<InlineKeyboardButton>> rows = Collections.singletonList(row);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        messageSender.sendMenu(chatID, "Добро пожаловать в 'Кофейный мастер'! Какой тип кофе вы хотите приготовить", keyboardMarkup);
    }

    void sendCoffeeSize(long chatID) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDD64 Маленький").callbackData("CoffeeSize_Small").build());
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDD64 Средний").callbackData("CoffeeSize_Medium").build());
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDD64 Большой").callbackData("CoffeeSize_Large").build());
        List<List<InlineKeyboardButton>> rows = Collections.singletonList(row);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        messageSender.sendMenu(chatID, "Отличный выбор! Теперь выберите объем напитка.", keyboardMarkup);
    }

    void sendCoffeeStrength(long chatID) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDD43 Легкий").callbackData("CoffeeStrength_Light").build());
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDD43 Средний").callbackData("CoffeeStrength_Medium").build());
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDD43 Крепкий").callbackData("CoffeeStrength_Strong").build());
        List<List<InlineKeyboardButton>> rows = Collections.singletonList(row);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        messageSender.sendMenu(chatID, "Какая крепость кофе вам нравится?", keyboardMarkup);
    }

    void sendCoffeeIngredients(long chatID) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDD5B Молоко").callbackData("CoffeeIngredients_Milk").build());
        row.add(InlineKeyboardButton.builder().text("\uD83E\uDDC9 Чай").callbackData("CoffeeIngredients_Tea").build());
        row.add(InlineKeyboardButton.builder().text("\uD83C\uDF6A Сироп").callbackData("CoffeeIngredients_Syrup").build());
        row.add(InlineKeyboardButton.builder().text(" Без Добавок ").callbackData("CoffeeIngredients_None").build());
        List<List<InlineKeyboardButton>> rows = Collections.singletonList(row);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        messageSender.sendMenu(chatID, "Хотите добавить что-нибудь к вашему кофе?", keyboardMarkup);
    }

    void sendCoffeeTemp(long chatID) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("☕ Горячий").callbackData("CoffeeTemp_Hot").build());
        row.add(InlineKeyboardButton.builder().text("☕ Теплый").callbackData("CoffeeTemp_Warm").build());
        row.add(InlineKeyboardButton.builder().text("☕ Холодный").callbackData("CoffeeTemp_Cold").build());
        List<List<InlineKeyboardButton>> rows = Collections.singletonList(row);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        messageSender.sendMenu(chatID, "Последний шаг - выберите температуру вашего кофе?", keyboardMarkup);
    }

    void sendCoffeeDone(long chatID, Update update) {
        messageSender.sendText(chatID, "\uD83E\uDD42 Ваш кофе готовится! Я сообщу, когда он будет готов.");
        new TimerCoffee(update).start();
    }

    void sendCoffeeDescription(long chatID) {
        String type = null, size = null, strength = null, ingredients = null, temp = null;
        StringBuilder coffeeDescription = new StringBuilder("");
        for (String desc : description) {
            if (desc.contains("Тип кофе")) {
                type = desc.split(": ")[1];
            } else if (desc.contains("Размер кофе")) {
                size = desc.split(": ")[1];
            } else if (desc.contains("Крепость кофе")) {
                strength = desc.split(": ")[1];
            } else if (desc.contains("Ингредиенты")) {
                ingredients = desc.split(": ")[1];
            } else if (desc.contains("Температура кофе")) {
                temp = desc.split(": ")[1];
            }
        }
        for (String desc : description) {
            coffeeDescription.append(desc).append("\n");
        }

        CoffeeSettings coffeeSettings = new CoffeeSettings(type, size, strength, ingredients, temp);

        String json = gson.toJson(coffeeSettings);

        IDB dataBase = DataBase.getInstance();
        dataBase.saveData(json);

        messageSender.sendText(chatID, "Ваш кофе готов!\n" + coffeeDescription.toString());
    }

    class TimerCoffee extends Thread {
        private final Update update;

        public TimerCoffee(Update update) {
            this.update = update;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                sendCoffeeDescription(chatId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}