package com.assignment.springboot.Model;

public class CartMember {
    private Product product;
    private int quantity;

    @Override
    public String toString() {
        return "Cart{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    public CartMember(){}

    public CartMember(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
