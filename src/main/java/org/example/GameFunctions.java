package org.example;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class GameFunctions {
    private final MessageSender messageSender;
    private boolean isPlay;
    private String themeFitness;
    private String themeGames;
    private String themeSelfDevelopment;
    private String themeTalking;
    private String themeCooking;
    private HashMap<Integer, String> themes = new HashMap<>();
    private String selectedTheme = null;


    public GameFunctions(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void startGame(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();

        if (selectedTheme != null && !callbackData.equals("Начать новый квест")) {
            messageSender.sendText(chatId, "Вы уже выбрали тему: " + selectedTheme);
            return;
        }

        themeFitness = changeFitnessTheme(themes);
        themeGames = changeGameTheme(themes);
        themeSelfDevelopment = changeSelfDevelopmentTheme(themes);
        themeTalking = changeTalkingTheme(themes);
        themeCooking = changeCookingTheme(themes);

        switch (callbackData) {
            case "Fitness" -> {
                selectedTheme = "Фитнес";
                messageSender.sendText(chatId, "Вот рандомное задание на тему фитнеса: " + themeFitness);
                sendNewGameButton(chatId);
            }
            case "Games" -> {
                selectedTheme = "Развлечения";
                messageSender.sendText(chatId, "Вот рандомное задание на тему игр: " + themeGames);
                sendNewGameButton(chatId);
            }
            case "SelfDevelopment" -> {
                selectedTheme = "Саморазвитие";
                messageSender.sendText(chatId, "Вот рандомное задание на тему саморазвития: " + themeSelfDevelopment);
                sendNewGameButton(chatId);
            }
            case "Talking" -> {
                selectedTheme = "Общение";
                messageSender.sendText(chatId, "Вот рандомное задание на тему общения: " + themeTalking);
                sendNewGameButton(chatId);
            }
            case "Cooking" -> {
                selectedTheme = "Кулинария";
                messageSender.sendText(chatId, "Вот рандомное задание на тему кулинарии: " + themeCooking);
                sendNewGameButton(chatId);
            }
            case "Начать новый квест" -> {
                resetGameState();
                sendButtonsTypeQuest(chatId);
            }
            case "Выбрать Тему" -> sendButtonsTypeQuest(chatId);
            default -> System.out.println("Error");
        }
    }

    private void resetGameState() {
        selectedTheme = null;
        isPlay = false;
        themes.clear();
    }

    void sendButtonsTypeQuest(long chatID) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder().text("\uD83C\uDFCB\uFE0F Фитнес").callbackData("Fitness").build());
        row.add(InlineKeyboardButton.builder().text("\uD83C\uDFAE Развлечения").callbackData("Games").build());
        row.add(InlineKeyboardButton.builder().text("\uD83D\uDCDA Саморазвитие").callbackData("SelfDevelopment").build());
        row.add(InlineKeyboardButton.builder().text("\uD83D\uDCAC Общение ").callbackData("Talking").build());
        row.add(InlineKeyboardButton.builder().text("\uD83C\uDF73 Кулинария").callbackData("Cooking").build());
        List<List<InlineKeyboardButton>> rows = Collections.singletonList(row);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        messageSender.sendMenu(chatID, "Выбирай на какую тему хочешь получить квест \uD83D\uDC7A", keyboardMarkup);
    }

    private void sendNewGameButton(long chatID) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("Начать новый квест").callbackData("Начать новый квест")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        messageSender.sendMenu(chatID, "🎮 Нажмите кнопку ниже, чтобы начать новый квест!", keyboardMarkup);
    }

    public String changeFitnessTheme(HashMap<Integer, String> themes) {
        themes.put(1, "20 отжиманий на кулаках");
        themes.put(2, "10 отжиманий на кулаках");
        themes.put(3, "15 отжиманий на кулаках");
        themes.put(4, "20 отжиманий");
        themes.put(5, "15 отжиманий");
        themes.put(6, "10 отжиманий");
        themes.put(7, "10 повторений пресса (скручивания)");
        themes.put(8, "20 повторений пресса (скручивания)");
        themes.put(9, "30 повторений пресса (скручивания)");
        themes.put(10, "15 повторений пресса (скручивания)");
        themes.put(11, "15 повторений приседаний");
        themes.put(12, "20 повторений приседаний");
        themes.put(13, "30 повторений приседаний");

        Random random = new Random();
        int randomKey = random.nextInt(themes.size()) + 1;
        return themes.get(randomKey);
    }

    public String changeGameTheme(HashMap<Integer, String> themes) {
        themes.put(1, "Сыграть в прятки");
        themes.put(2, "Попробовать настольную игру, которую раньше не играл");
        themes.put(3, "Устроить мини-турнир по настольным играм");
        themes.put(4, "Придумать и сыграть в свою собственную игру");
        themes.put(5, "Сыграть партию в шахматы или шашки");
        themes.put(6, "Организовать квест на улице или в помещении");
        themes.put(7, "Сыграть в классическую карточную игру");
        themes.put(8, "Попробовать новую видеоигру");
        themes.put(9, "Организовать виртуальный игровой вечер с друзьями");
        themes.put(10, "Придумать и провести викторину");
        themes.put(11, "Попробовать свои силы в новом виде спорта");
        themes.put(12, "Придумать и провести игровое занятие для детей");
        themes.put(13, "Сыграть в настольную ролевую игру");

        Random random = new Random();
        int randomKey = random.nextInt(themes.size()) + 1;
        return themes.get(randomKey);
    }

    public String changeSelfDevelopmentTheme(HashMap<Integer, String> themes) {
        themes.put(1, "Прочитать книгу о личностном росте");
        themes.put(2, "Выучить 10 новых слов на иностранном языке");
        themes.put(3, "Просмотреть мотивационное видео");
        themes.put(4, "Написать список целей на ближайший месяц");
        themes.put(5, "Составить план дня и следовать ему");
        themes.put(6, "Заняться медитацией или йогой");
        themes.put(7, "Освоить новую технику управления временем");
        themes.put(8, "Посетить онлайн-курс или вебинар");
        themes.put(9, "Начать вести дневник благодарности");
        themes.put(10, "Попробовать новый вид творчества или хобби");
        themes.put(11, "Послушать подкаст на тему саморазвития");
        themes.put(12, "Провести цифровой детокс на один день");
        themes.put(13, "Изучить основы финансовой грамотности");

        Random random = new Random();
        int randomKey = random.nextInt(themes.size()) + 1;
        return themes.get(randomKey);
    }

    public String changeTalkingTheme(HashMap<Integer, String> themes) {
        themes.put(1, "Устроить вечер воспоминаний с друзьями");
        themes.put(2, "Обсудить интересную книгу или фильм");
        themes.put(3, "Провести дебаты на актуальную тему");
        themes.put(4, "Поделиться своими мечтами и целями");
        themes.put(5, "Обсудить планы на будущее");
        themes.put(6, "Узнать что-то новое о собеседнике");
        themes.put(7, "Рассказать друг другу интересные истории из жизни");
        themes.put(8, "Поговорить о последних новостях и событиях");
        themes.put(9, "Устроить вечер вопросов и ответов");
        themes.put(10, "Организовать дружеский турнир по настольным играм");
        themes.put(11, "Обсудить любимые музыкальные исполнители и песни");
        themes.put(12, "Поделиться полезными советами и рекомендациями");
        themes.put(13, "Обсудить планы на выходные или отпуск");

        Random random = new Random();
        int randomKey = random.nextInt(themes.size()) + 1;
        return themes.get(randomKey);
    }

    public String changeCookingTheme(HashMap<Integer, String> themes) {
        themes.put(1, "Приготовить новое блюдо из кухни другой страны");
        themes.put(2, "Попробовать приготовить здоровый завтрак");
        themes.put(3, "Испечь пирог или кексы");
        themes.put(4, "Устроить вечер гриля с друзьями");
        themes.put(5, "Приготовить блюдо из сезонных продуктов");
        themes.put(6, "Попробовать свои силы в вегетарианской кухне");
        themes.put(7, "Сделать домашние конфеты или печенье");
        themes.put(8, "Попробовать приготовить блюдо из новой кулинарной книги");
        themes.put(9, "Организовать кулинарный мастер-класс для друзей");
        themes.put(10, "Приготовить несколько блюд для пикника на природе");
        themes.put(11, "Попробовать приготовить блюдо с использованием необычных специй");
        themes.put(12, "Устроить вечер кулинарных экспериментов");
        themes.put(13, "Приготовить изысканное блюдо для романтического ужина");

        Random random = new Random();
        int randomKey = random.nextInt(themes.size()) + 1;
        return themes.get(randomKey);
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

}
