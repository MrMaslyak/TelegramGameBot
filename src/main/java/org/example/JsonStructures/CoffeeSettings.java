package org.example.JsonStructures;

public class CoffeeSettings {
    private String type;
    private String size;
    private String strength;
    private String ingredients;
    private String temp;


    public CoffeeSettings(String type, String size, String strength, String ingredients, String temp) {
        this.type = type;
        this.size = size;
        this.strength = strength;
        this.ingredients = ingredients;
        this.temp = temp;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}