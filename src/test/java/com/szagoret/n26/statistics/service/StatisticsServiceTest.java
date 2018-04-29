package com.szagoret.n26.statistics.service;

import com.szagoret.n26.statistics.datastore.ReactiveTransactionsDataStoreComponent;
import com.szagoret.n26.statistics.models.Statistic;
import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.repository.TransactionsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTest {

    @MockBean
    TransactionsRepository transactionsRepository;
    @Autowired
    private ReactiveTransactionsDataStoreComponent dataStore;
    @Autowired
    private StatisticsService statisticsService;

    @Test
    public void testStatisticCalculations() {

        Transaction tx0 = new Transaction(20D, 1525036984900L);  // Apr 29 2018 21:23:04
        Transaction tx1 = new Transaction(30D, 1525036984900L); // Apr 29 2018 21:23:04
        Transaction tx2 = new Transaction(10D, 1525036925082L);  // Apr 29 2018 21:22:05

        given(transactionsRepository.findTransactions(any()))
                .willReturn(Flux.just(tx0, tx1, tx2));

        Mono<Statistic> statisticsResult = statisticsService.getStatistics();

        verify(transactionsRepository).findTransactions(any());
        verifyNoMoreInteractions(transactionsRepository);

        StepVerifier.create(statisticsResult)
                .consumeNextWith(statistic -> {
                    assertThat(statistic.getSum()).isEqualTo(60D);
                    assertThat(statistic.getAvg()).isEqualTo(20D);
                    assertThat(statistic.getMax()).isEqualTo(tx1.getAmount());
                    assertThat(statistic.getMin()).isEqualTo(tx2.getAmount());
                    assertThat(statistic.getCount()).isEqualTo(3);
                }).expectComplete()
                .verify();


    }
}
