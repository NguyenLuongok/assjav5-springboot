package com.assignment.springboot.Service;

import com.assignment.springboot.Model.ReceiptItem;

import java.util.List;

public interface ReceiptItemService {
    List<ReceiptItem> findAll();
    ReceiptItem findById(Long id);
    void save(ReceiptItem receiptItem);
    void remove(Long id);
    List<ReceiptItem> saveAll(List<ReceiptItem> receiptItemList);
    List<ReceiptItem> fillAllByReceiptId(Long id);
}
