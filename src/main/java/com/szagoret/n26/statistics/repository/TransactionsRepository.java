package com.szagoret.n26.statistics.repository;

import com.szagoret.n26.statistics.models.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionsRepository {

    Mono<Void> addTransaction(Mono<Transaction> transaction);

    Flux<Transaction> findTransactions(Long startingFromTimestamp);
}
