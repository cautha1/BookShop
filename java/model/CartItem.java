package model;

import java.text.DecimalFormat;
import java.io.*;

public class CartItem implements Serializable {

    private Books book;
    private int quantity = 0;
    private double costOfItem = 0.00;

    public CartItem(Books book) {
        this.book = book;
    }

    public void setQuantity(int aQuantity) {
        quantity += aQuantity;
        calculateOrderCost();
    }

    public void updateQuantity(int newQuantity) {
        quantity = newQuantity;
        calculateOrderCost();
    }

    private void calculateOrderCost() {
        costOfItem = (quantity * book.getPrice());
    }

    public String getDollarOrderCost() {
        DecimalFormat cost = new DecimalFormat("0.00");
        return ("$" + cost.format(this.costOfItem));
    }

    public String toString() {
        return (book + "  " + "quantity: " + quantity);
    }

    public Books getBook() {
        return book;
    }

    public double getOrderCost() {
        return costOfItem;
    }

    public int getQuantity() {
        return quantity;
    }

}
