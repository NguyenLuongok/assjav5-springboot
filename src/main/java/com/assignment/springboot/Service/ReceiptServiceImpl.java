package com.assignment.springboot.Service;

import com.assignment.springboot.Model.Receipt;
import com.assignment.springboot.Repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Override
    public List<Receipt> findAll() {
        return receiptRepository.findAll();
    }

    @Override
    public Receipt findById(Long id) {
        return receiptRepository.findById(id);
    }

    @Override
    public void save(Receipt receipt) {
        receiptRepository.save(receipt);
    }

    @Override
    public void remove(Long id) {
        receiptRepository.remove(id);
    }

    @Override
    public List<Receipt> fillAllReceiptByMemberId(Long id) {
        return receiptRepository.fillAllReceiptByMemberId(id);
    }
}
