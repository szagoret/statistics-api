package com.szagoret.n26.statistics.datastore;

import com.szagoret.n26.statistics.models.Transaction;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
public class ReactiveTransactionsDataStoreComponent {

    private final ConcurrentNavigableMap<Long, List<Transaction>> concurrentNavigableMap;

    public ReactiveTransactionsDataStoreComponent() {
        this.concurrentNavigableMap = new ConcurrentSkipListMap<>();
    }

    public Flux<Transaction> findTimeFrameTransactions(Long fromKey) {
        return Flux.fromStream(concurrentNavigableMap.tailMap(fromKey, true).values().stream().flatMap(Collection::stream));
    }

    public Mono<ConcurrentNavigableMap<Long, List<Transaction>>> getAllMap() {
        return Mono.just(concurrentNavigableMap);
    }

    public Flux<Transaction> getAll() {
        return Flux.fromStream(concurrentNavigableMap.values().stream().flatMap(Collection::stream));
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

    public Mono<Void> clearDataStore() {
        concurrentNavigableMap.clear();
        return Mono.empty();
    }
}
