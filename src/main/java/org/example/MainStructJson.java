package org.example;

public class MainStructJson {
    private UserSettings userSettings;
    private CoffeeSettings coffeeSettings;

    public MainStructJson(UserSettings userSettings, CoffeeSettings coffeeSettings) {
        this.userSettings = userSettings;
        this.coffeeSettings = coffeeSettings;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public CoffeeSettings getCoffeeSettings() {
        return coffeeSettings;
    }

    public void setCoffeeSettings(CoffeeSettings coffeeSettings) {
        this.coffeeSettings = coffeeSettings;
    }
}
