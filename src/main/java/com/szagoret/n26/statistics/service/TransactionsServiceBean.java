package com.szagoret.n26.statistics.service;

import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.repository.TransactionsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TransactionsServiceBean implements TransactionsService {

    private final TransactionsRepository transactionsRepository;

    public TransactionsServiceBean(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Mono<Void> addTransaction(Mono<Transaction> transaction) {
        return transactionsRepository.addTransaction(transaction);
    }
}
