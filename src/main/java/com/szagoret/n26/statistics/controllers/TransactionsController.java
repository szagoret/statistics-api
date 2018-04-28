package com.szagoret.n26.statistics.controllers;

import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.service.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> addTransactions(Mono<Transaction> transaction) {
        return transactionsService.addTransaction(transaction)
                .then(Mono.just(ResponseEntity.status(HttpStatus.OK).build()));
    }
}
