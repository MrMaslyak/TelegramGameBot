package org.example.Interface;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface MessageSender {
    void sendText(Long who, String what);
    void sendMenu(Long who, String txt, InlineKeyboardMarkup kb);
}
