package com.szagoret.n26.statistics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "n26")
public class StatisticsProperties {

    private final Statistics statistics = new Statistics();

    @Data
    public static class Statistics {
        private Long timeFrameLength;
    }
}
