package com.szagoret.n26.statistics.repository;

import com.szagoret.n26.statistics.models.Statistic;
import reactor.core.publisher.Mono;

public interface StatisticsRepository {

    Mono<Statistic> getStatistics();
}
