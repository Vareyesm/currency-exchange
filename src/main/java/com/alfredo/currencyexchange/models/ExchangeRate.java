package com.alfredo.currencyexchange.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "update_date")
    private Date updateDate;

    private Double value;

    @ManyToOne
    @JoinColumn(name = "sorce_currency_id")
    private Currency sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "destination_currency_id")
    private Currency destinationCurrency;
}
