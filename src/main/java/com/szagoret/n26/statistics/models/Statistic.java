package com.szagoret.n26.statistics.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {

    private Double sum;
    private Double avg;
    private Double max;
    private Double min;
    private Long count;
}
