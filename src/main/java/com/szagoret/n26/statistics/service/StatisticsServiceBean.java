package com.szagoret.n26.statistics.service;

import com.szagoret.n26.statistics.config.StatisticsProperties;
import com.szagoret.n26.statistics.models.Statistic;
import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.repository.StatisticsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;


@Service
public class StatisticsServiceBean implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final StatisticsProperties statisticsProperties;

    public StatisticsServiceBean(StatisticsRepository statisticsRepository, StatisticsProperties statisticsProperties) {
        this.statisticsRepository = statisticsRepository;
        this.statisticsProperties = statisticsProperties;
    }

    @Override
    public Mono<Statistic> getStatistics() {
        return statisticsRepository.getStatistics(statisticsProperties.getStatistics().getTimeFrameLength())
                .map(txsMap ->
                        txsMap
                                .values()
                                .stream()
                                .flatMap(t -> t.stream())
                                .collect(Collectors.summarizingDouble(Transaction::getAmount))
                ).map(resultStatistics ->
                        new Statistic(
                                resultStatistics.getSum(),
                                resultStatistics.getAverage(),
                                resultStatistics.getMax(),
                                resultStatistics.getMin(),
                                resultStatistics.getCount()
                        ));

    }
}
