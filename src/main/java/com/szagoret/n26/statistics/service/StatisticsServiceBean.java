package com.szagoret.n26.statistics.service;

import com.szagoret.n26.statistics.models.Statistic;
import com.szagoret.n26.statistics.repository.StatisticsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StatisticsServiceBean implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsServiceBean(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public Mono<Statistic> getStatistics() {
        return statisticsRepository.getStatistics();
    }
}
