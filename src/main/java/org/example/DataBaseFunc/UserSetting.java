package org.example.DataBaseFunc;

public class UserSetting {

    private long chatID;
    private String name;

    public UserSetting(long chatID, String name) {
        this.chatID = chatID;
        this.name = name;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
