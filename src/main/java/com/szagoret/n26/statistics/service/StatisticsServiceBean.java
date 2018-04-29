package com.szagoret.n26.statistics.service;

import com.szagoret.n26.statistics.config.StatisticsProperties;
import com.szagoret.n26.statistics.models.Statistic;
import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.repository.TransactionsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static com.szagoret.n26.statistics.util.DateUtils.secondsBefore;


@Service
public class StatisticsServiceBean implements StatisticsService {

    private final TransactionsRepository transactionsRepository;
    private final StatisticsProperties statisticsProperties;

    public StatisticsServiceBean(TransactionsRepository transactionsRepository,
                                 StatisticsProperties statisticsProperties) {
        this.transactionsRepository = transactionsRepository;
        this.statisticsProperties = statisticsProperties;
    }


    @Override
    public Mono<Statistic> getStatistics() {
        return transactionsRepository.findTransactions(secondsBefore(statisticsProperties.getStatistics().getTimeFrameLength()))
                .collect(Collectors.summarizingDouble(Transaction::getAmount))
                .map(summaryStatistics ->
                        new Statistic(
                                summaryStatistics.getSum(),
                                summaryStatistics.getAverage(),
                                summaryStatistics.getMax(),
                                summaryStatistics.getMin(),
                                summaryStatistics.getCount()

                        ));
    }
}
