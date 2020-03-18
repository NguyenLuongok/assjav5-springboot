package com.assignment.springboot.Service;

import com.assignment.springboot.Model.ReceiptItem;
import com.assignment.springboot.Repository.ReceiptItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReceiptItemServiceImpl implements ReceiptItemService {

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @Override
    public List<ReceiptItem> findAll() {
        return receiptItemRepository.findAll();
    }

    @Override
    public ReceiptItem findById(Long id) {
        return receiptItemRepository.findById(id);
    }

    @Override
    public void save(ReceiptItem receiptItem) {
        receiptItemRepository.save(receiptItem);
    }

    @Override
    public void remove(Long id) {
        receiptItemRepository.remove(id);
    }

    @Override
    public List<ReceiptItem> saveAll(List<ReceiptItem> receiptItemList) {
        return receiptItemRepository.saveAll(receiptItemList);
    }

    @Override
    public List<ReceiptItem> fillAllByReceiptId(Long id) {
        return receiptItemRepository.fillAllByReceiptId(id);
    }
}
