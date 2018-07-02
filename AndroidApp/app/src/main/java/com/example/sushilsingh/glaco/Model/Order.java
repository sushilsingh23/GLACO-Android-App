package com.example.sushilsingh.glaco.Model;

/**
 * Created by Sushil Singh on 11/26/2017.
 */

public class Order {

    private String ProductId;
    private String ProductName;
    private String Price;
    private String Quantity;

    public Order() {
    }

    public Order(String productId, String productName, String quantity,String price ) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }


}
