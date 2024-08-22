package org.example;

public class User {

    private long chatID;
    private String name;

    public User(long chatID, String name) {
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
