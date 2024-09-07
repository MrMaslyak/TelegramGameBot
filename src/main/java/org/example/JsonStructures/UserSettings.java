package org.example.JsonStructures;

public class UserSettings {
    private long idUser;
    private String message;

    public UserSettings(long idUser, String message) {
        this.idUser = idUser;
        this.message = message;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}