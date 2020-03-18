package com.assignment.springboot.Service;

import com.assignment.springboot.Model.Receipt;

import java.util.List;

public interface ReceiptService {
    List<Receipt> findAll();
    Receipt findById(Long id);
    void save(Receipt receipt);
    void remove(Long id);
    List<Receipt> fillAllReceiptByMemberId(Long id);
}
