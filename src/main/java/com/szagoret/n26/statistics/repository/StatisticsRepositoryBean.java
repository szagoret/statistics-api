package com.szagoret.n26.statistics.repository;

import com.szagoret.n26.statistics.datastore.ReactiveTransactionsDataStoreComponent;
import com.szagoret.n26.statistics.models.Statistic;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class StatisticsRepositoryBean implements StatisticsRepository {

    private final ReactiveTransactionsDataStoreComponent storeComponent;

    public StatisticsRepositoryBean(ReactiveTransactionsDataStoreComponent storeComponent) {
        this.storeComponent = storeComponent;
    }

    @Override
    public Mono<Statistic> getStatistics() {
        return Mono.just(new Statistic(1.2, 3.4, 4.6, 3.0, 2.2));
    }
}
