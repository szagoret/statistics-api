package com.szagoret.n26.statistics.service;

import com.szagoret.n26.statistics.models.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionsService {

    Mono<Void> addTransaction(Mono<Transaction> transaction);
}
