package com.szagoret.n26.statistics.repository;

import com.szagoret.n26.statistics.datastore.ReactiveTransactionsDataStoreComponent;
import com.szagoret.n26.statistics.models.Transaction;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class TransactionsRepositoryBean implements TransactionsRepository {

    private final ReactiveTransactionsDataStoreComponent dataStoreComponent;

    public TransactionsRepositoryBean(ReactiveTransactionsDataStoreComponent dataStoreComponent) {
        this.dataStoreComponent = dataStoreComponent;
    }

    @Override
    public Mono<Void> addTransaction(Mono<Transaction> transaction) {
        return dataStoreComponent.addTransaction(transaction);
    }

    @Override
    public Flux<Transaction> findTransactions(Long startingFromTimestamp) {
        return dataStoreComponent.findTimeFrameTransactions(startingFromTimestamp);
    }
}
