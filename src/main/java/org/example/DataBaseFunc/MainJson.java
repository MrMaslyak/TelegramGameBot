package org.example.DataBaseFunc;

public class MainJson {
    private StartGameDB  startGameDB;
    private UserSetting userSetting;

    public MainJson(StartGameDB startGameDB, UserSetting userSetting) {
        this.startGameDB = startGameDB;
        this.userSetting = userSetting;
    }

    public StartGameDB getStartGameDB() {
        return startGameDB;
    }

    public void setStartGameDB(StartGameDB startGameDB) {
        this.startGameDB = startGameDB;
    }

    public UserSetting getUser() {
        return userSetting;
    }

    public void setUser(UserSetting userSetting) {
        this.userSetting = userSetting;
    }
}
