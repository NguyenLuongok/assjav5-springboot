package com.assignment.springboot.Repository;

import com.assignment.springboot.Model.Receipt;

import java.util.List;

public interface ReceiptRepository extends Repository<Receipt> {
    List<Receipt> fillAllReceiptByMemberId(Long id);
}
