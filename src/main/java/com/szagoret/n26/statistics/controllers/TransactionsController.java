package com.szagoret.n26.statistics.controllers;

import com.szagoret.n26.statistics.datastore.ReactiveTransactionsDataStoreComponent;
import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.service.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final ReactiveTransactionsDataStoreComponent storeComponent;

    public TransactionsController(TransactionsService transactionsService, ReactiveTransactionsDataStoreComponent storeComponent) {
        this.transactionsService = transactionsService;
        this.storeComponent = storeComponent;
    }

    @GetMapping("/all")
    public Mono<ConcurrentNavigableMap<Long, List<Transaction>>> getAll(){
        return storeComponent.getAll();
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> addTransaction(@RequestBody @Valid Mono<Transaction> transaction) {
        return transactionsService.addTransaction(transaction)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }
}
