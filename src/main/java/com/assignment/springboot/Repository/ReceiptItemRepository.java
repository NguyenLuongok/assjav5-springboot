package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.ReceiptItem;

import java.util.List;

public interface ReceiptItemRepository extends Repository<ReceiptItem> {
    List<ReceiptItem> saveAll(List<ReceiptItem> receiptItemList);
    List<ReceiptItem> fillAllByReceiptId(Long id);
}
