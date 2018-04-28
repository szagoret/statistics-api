package com.szagoret.n26.statistics.repository;

import com.szagoret.n26.statistics.datastore.ReactiveTransactionsDataStoreComponent;
import com.szagoret.n26.statistics.models.Transaction;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

@Repository
public class StatisticsRepositoryBean implements StatisticsRepository {

    private final ReactiveTransactionsDataStoreComponent dataStoreComponent;

    public StatisticsRepositoryBean(ReactiveTransactionsDataStoreComponent dataStoreComponent) {
        this.dataStoreComponent = dataStoreComponent;
    }

    @Override
    public Mono<ConcurrentNavigableMap<Long, List<Transaction>>> getStatistics(Long timeFrame) {
        return dataStoreComponent.findTransactionsWindow(timeFrame);
    }
}
