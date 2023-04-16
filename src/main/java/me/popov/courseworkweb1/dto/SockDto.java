package me.popov.courseworkweb1.dto;

import me.popov.courseworkweb1.model.Color;
import me.popov.courseworkweb1.model.Size;

public class SockDto {
    private Color color;
    private Size size;
    private int cottonPercentage;
    private int quantity;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getCottonPercentage() {
        return cottonPercentage;
    }

    public void setCottonPercentage(int cottonPercentage) {
        this.cottonPercentage = cottonPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
