package com.szagoret.n26.statistics.service;

import com.szagoret.n26.statistics.models.Statistic;
import reactor.core.publisher.Mono;

public interface StatisticsService {

    Mono<Statistic> getStatistics();
}
