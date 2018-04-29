package com.szagoret.n26.statistics.controllers;

import com.szagoret.n26.statistics.config.StatisticsProperties;
import com.szagoret.n26.statistics.models.Transaction;
import com.szagoret.n26.statistics.service.TransactionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;
    private final StatisticsProperties statisticsProperties;

    public TransactionsController(TransactionsService transactionsService,
                                  StatisticsProperties statisticsProperties) {
        this.transactionsService = transactionsService;
        this.statisticsProperties = statisticsProperties;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> addTransaction(@RequestBody @Valid Transaction transaction) {
        HttpStatus httpStatus = computeHttpStatus(transaction.getTimestamp());

        return transactionsService.addTransaction(Mono.just(transaction))
                .then(Mono.just(ResponseEntity.status(httpStatus).build()));
    }


    private HttpStatus computeHttpStatus(Long timestamp) {
        Long timeFrameLength = statisticsProperties.getStatistics().getTimeFrameLength();
        if (Duration.between(Instant.ofEpochMilli(timestamp), Instant.now()).getSeconds() > timeFrameLength) {
            return HttpStatus.NO_CONTENT;
        } else {
            return HttpStatus.CREATED;
        }
    }
}
