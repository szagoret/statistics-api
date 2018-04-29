package com.szagoret.n26.statistics.datastore;

import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.util.DateUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveTransactionsDataStoreComponentTest {

    private final Long commonTimestamp = 1525020005501L;

    @Autowired
    private ReactiveTransactionsDataStoreComponent dataStore;

    private Transaction testTransaction;

    @Before
    public void setUp() {
        testTransaction = new Transaction(10.5D, commonTimestamp);
        dataStore.addTransaction(Mono.just(testTransaction)).subscribe();
    }

    @After
    public void afterTest() {
        dataStore.clearDataStore().subscribe();
    }


    @Test
    public void testDataStoreOneElement() {
        Flux<Transaction> allFlux = dataStore.getAll();
        StepVerifier.create(allFlux)
                .recordWith(ArrayList::new)
                .expectNextCount(1)
                .consumeRecordedWith(transactions -> {
                    assertThat(transactions).hasSize(1);
                    assertThat(transactions)
                            .extracting(Transaction::getTimestamp)
                            .contains(testTransaction.getTimestamp());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testDataStoreSameTimestampTransaction() {
        Transaction localTransaction = new Transaction(20.3, commonTimestamp);
        dataStore.addTransaction(Mono.just(localTransaction)).subscribe();
        Flux<Transaction> allFlux = dataStore.getAll();
        StepVerifier.create(allFlux)
                .recordWith(ArrayList::new)
                .expectNextCount(2)
                .consumeRecordedWith(transactions -> {
                    assertThat(transactions).hasSize(2);
                    assertThat(transactions)
                            .extracting(Transaction::getTimestamp)
                            .contains(commonTimestamp);
                    assertThat(transactions)
                            .extracting(Transaction::getAmount)
                            .contains(testTransaction.getAmount())
                            .contains(localTransaction.getAmount());
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testDataStoreFindByTimeFrame() {
        Long timestampNow = 1525036985000L; // Apr 29 2018 21:23:05
        Long timeFrameLength = 60L; //sec

        Transaction tx0 = new Transaction(20D, 1525036984900L); // Apr 29 2018 21:23:04 (yes)
        Transaction tx1 = new Transaction(10D, 1525036925082L); // Apr 29 2018 21:22:05 (yes)
        Transaction tx2 = new Transaction(12.5, 1525036925082L);// Apr 29 2018 21:22:05 (yes)
        Transaction tx3 = new Transaction(7.5, 1525036924082L); // Apr 29 2018 21:22:04 (no)
        Transaction tx4 = new Transaction(12D, 1515036924082L); // Jan 04 2018 03:35:24 (no)

        dataStore.addTransaction(Mono.just(tx0)).subscribe();
        dataStore.addTransaction(Mono.just(tx1)).subscribe();
        dataStore.addTransaction(Mono.just(tx2)).subscribe();
        dataStore.addTransaction(Mono.just(tx3)).subscribe();
        dataStore.addTransaction(Mono.just(tx4)).subscribe();

        Long timestampSecondsBefore = DateUtils.secondsBefore(timeFrameLength, timestampNow);
        Flux<Transaction> timeFrameTransactions = dataStore.findTimeFrameTransactions(timestampSecondsBefore);

        StepVerifier.create(timeFrameTransactions)
                .recordWith(ArrayList::new)
                .expectNextCount(3)
                .consumeRecordedWith(transactions -> {
                    assertThat(transactions).hasSize(3);
                    assertThat(transactions)
                            .extracting(Transaction::getTimestamp)
                            .doesNotContain(tx3.getTimestamp(), tx4.getTimestamp())
                            .contains(tx0.getTimestamp(), tx1.getTimestamp(), tx2.getTimestamp());
                })
                .expectComplete()
                .verify();


    }
}
