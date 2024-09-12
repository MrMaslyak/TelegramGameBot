package org.example.DataBaseFunc;

public class StartGameDB {
    private String nameFirstUser;
    private String nameSecondUser;
    private long chatIDFirstUser;
    private long chatIDSecondUser;
    private boolean startGame;

    public StartGameDB(String nameFirstUser, String nameSecondUser, long chatIDFirstUser, long chatIDSecondUser, boolean startGame) {
        this.nameFirstUser = nameFirstUser;
        this.nameSecondUser = nameSecondUser;
        this.chatIDFirstUser = chatIDFirstUser;
        this.chatIDSecondUser = chatIDSecondUser;
        this.startGame = startGame;
    }

    public String getNameFirstUser() {
        return nameFirstUser;
    }

    public void setNameFirstUser(String nameFirstUser) {
        this.nameFirstUser = nameFirstUser;
    }

    public String getNameSecondUser() {
        return nameSecondUser;
    }

    public void setNameSecondUser(String nameSecondUser) {
        this.nameSecondUser = nameSecondUser;
    }

    public long getChatIDFirstUser() {
        return chatIDFirstUser;
    }

    public void setChatIDFirstUser(long chatIDFirstUser) {
        this.chatIDFirstUser = chatIDFirstUser;
    }

    public long getChatIDSecondUser() {
        return chatIDSecondUser;
    }

    public void setChatIDSecondUser(long chatIDSecondUser) {
        this.chatIDSecondUser = chatIDSecondUser;
    }

    public boolean isStartGame() {
        return startGame;
    }

    public void setStartGame(boolean startGame) {
        this.startGame = startGame;
    }
}
