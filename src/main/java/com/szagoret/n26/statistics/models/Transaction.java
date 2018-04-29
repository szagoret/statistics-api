package com.szagoret.n26.statistics.models;

import com.szagoret.n26.statistics.validation.PastDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @NotNull
    private Double amount;

    @PastDate
    private Long timestamp;


}
