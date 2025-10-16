package com.example.currency;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;

public class CurrencyConversionResult {

    private final String from;
    private final String to;
    private final Money originalAmount;
    private final Money convertedAmount;
    private final BigDecimal rate;

    public CurrencyConversionResult(String from, String to, Money originalAmount, Money convertedAmount, BigDecimal rate) {
        this.from = from;
        this.to = to;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.rate = rate;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public Money getOriginalAmount() { return originalAmount; }
    public Money getConvertedAmount() { return convertedAmount; }
    public BigDecimal getRate() { return rate; }
}