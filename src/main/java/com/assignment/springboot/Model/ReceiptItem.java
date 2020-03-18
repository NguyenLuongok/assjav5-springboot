package com.assignment.springboot.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "receipt_item")
public class ReceiptItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long receiptItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiptId")
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    private int receiptItemTotal;
    private double receiptItemPrice;
    private int receiptItemSale;
    private boolean receiptItemStatus;

    public ReceiptItem() {
    }

    public ReceiptItem(Receipt receipt) {
        this.receipt = receipt;
    }

    public ReceiptItem(Product product,Receipt receipt,double receiptItemPrice,int receiptItemSale, int receiptItemTotal, boolean receiptItemStatus) {
        this.receipt = receipt;
        this.product = product;
        this.receiptItemPrice = receiptItemPrice;
        this.receiptItemSale  = receiptItemSale;
        this.receiptItemTotal = receiptItemTotal;
        this.receiptItemStatus = receiptItemStatus;
    }

    public ReceiptItem(Receipt receipt,Product product,double receiptItemPrice,int receiptItemSale, int receiptItemTotal, boolean receiptItemStatus) {
        this.product = product;
        this.receipt = receipt;
        this.receiptItemPrice = receiptItemPrice;
        this.receiptItemSale  = receiptItemSale;
        this.receiptItemTotal = receiptItemTotal;
        this.receiptItemStatus = receiptItemStatus;
    }

    @Override
    public String toString() {
        return "ReceiptItem{" +
                ", receipt=" + receipt +
                ", product=" + product +
                ", receiptItemTotal=" + receiptItemTotal +
                ", receiptItemPrice=" + receiptItemPrice +
                ", receiptItemSale=" + receiptItemSale +
                ", receiptItemStatus='" + receiptItemStatus + '\'' +
                '}';
    }

    public Long getReceiptItemId() {
        return receiptItemId;
    }

    public void setReceiptItemId(Long receiptItemId) {
        this.receiptItemId = receiptItemId;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getReceiptItemTotal() {
        return receiptItemTotal;
    }

    public void setReceiptItemTotal(int receiptItemTotal) {
        this.receiptItemTotal = receiptItemTotal;
    }

    public double getReceiptItemPrice() {
        return receiptItemPrice;
    }

    public void setReceiptItemPrice(double receiptItemPrice) {
        this.receiptItemPrice = receiptItemPrice;
    }

    public int getReceiptItemSale() {
        return receiptItemSale;
    }

    public void setReceiptItemSale(int receiptItemSale) {
        this.receiptItemSale = receiptItemSale;
    }

    public boolean isReceiptItemStatus() {
        return receiptItemStatus;
    }

    public void setReceiptItemStatus(boolean receiptItemStatus) {
        this.receiptItemStatus = receiptItemStatus;
    }
}
