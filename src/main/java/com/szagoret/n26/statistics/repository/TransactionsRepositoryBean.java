package com.szagoret.n26.statistics.repository;

import com.szagoret.n26.statistics.models.Transaction;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class TransactionsRepositoryBean implements TransactionsRepository {

    @Override
    public Mono<Void> addTransaction(Mono<Transaction> transaction) {
        return Mono.empty();
    }
}
