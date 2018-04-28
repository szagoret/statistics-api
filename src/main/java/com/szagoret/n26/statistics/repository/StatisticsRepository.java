package com.szagoret.n26.statistics.repository;

import com.szagoret.n26.statistics.models.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

public interface StatisticsRepository {

    Mono<ConcurrentNavigableMap<Long, List<Transaction>>> getStatistics(Long timeFrame);
}
