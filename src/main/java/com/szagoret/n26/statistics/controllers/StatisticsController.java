package com.szagoret.n26.statistics.controllers;

import com.szagoret.n26.statistics.models.Statistic;
import com.szagoret.n26.statistics.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public Mono<ResponseEntity<Statistic>> getStatistics() {
        return statisticsService.getStatistics()
                .map(statistic -> ResponseEntity.ok(statistic));
    }
}
