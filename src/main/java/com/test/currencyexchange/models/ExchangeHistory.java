package com.test.currencyexchange.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "exchange_history")
@Getter
@Setter
public class ExchangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    @Column(name = "exchange_rate_value")
    private Double exchangeRateValue;

    @NotNull
    @Column(name = "input_value")
    private Double inputValue;

    @NotNull
    @Column(name = "output_value")
    private Double outputValue;

    @NotNull
    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "sorce_currency_id")
    private Currency sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "destination_currency_id")
    private Currency destinationCurrency;
}
