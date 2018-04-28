package com.szagoret.n26.statistics.datastore;

import com.szagoret.n26.statistics.models.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
public class ReactiveTransactionsDataStoreComponent {

    private final ConcurrentNavigableMap<Long, List<Transaction>> concurrentNavigableMap;

    public ReactiveTransactionsDataStoreComponent() {
        this.concurrentNavigableMap = new ConcurrentSkipListMap<>();
    }

    public Mono<ConcurrentNavigableMap<Long, List<Transaction>>> findTransactionsWindow(Long windowLength) {
        return Mono.just(concurrentNavigableMap.headMap(windowLength));
    }

    public Mono<ConcurrentNavigableMap<Long, List<Transaction>>> getAll() {
        return Mono.just(concurrentNavigableMap);
    }

    public Mono<Void> addTransaction(Mono<Transaction> transaction) {
        Mono<Transaction> result = transaction.doOnNext(tx ->
                concurrentNavigableMap.compute(tx.getTimestamp(), (timestamp, transactions) -> {
                    if (transactions == null) {
                        return new ArrayList<>(Arrays.asList(tx));
                    } else {
                        transactions.add(tx);
                        return transactions;
                    }
                })
        );
        return result.thenEmpty(Mono.empty());
    }

    /**
     * TODO Delete at the end
     *
     * @return
     */
    @Bean
    CommandLineRunner setUp() {
        return args ->
                concurrentNavigableMap.put(20L, Arrays.asList(
                        new Transaction(20.4, 1234567890L),
                        new Transaction(40.2, 1234567891L)
                ));
    }
}
