package com.example.currency;

import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionService {

    // Tabela de taxas fixas (EUR como base)
    private final Map<String, BigDecimal> rates = new HashMap<>();

    public CurrencyConversionService() {
        // Exemplo de taxas
        rates.put("EUR", BigDecimal.valueOf(1.0));
        rates.put("USD", BigDecimal.valueOf(1.1)); // 1 EUR = 1.1 USD
        rates.put("GBP", BigDecimal.valueOf(0.88)); // 1 EUR = 0.88 GBP
    }

    public CurrencyConversionResult convert(String from, String to, BigDecimal amount) {
        if (!rates.containsKey(from) || !rates.containsKey(to)) {
            throw new IllegalArgumentException("Moeda inválida: " + from + " ou " + to);
        }

        // Converte para EUR primeiro, depois para destino
        BigDecimal amountInEUR = amount.divide(rates.get(from), 6, BigDecimal.ROUND_HALF_UP);
        BigDecimal convertedAmountValue = amountInEUR.multiply(rates.get(to));

        Money originalAmount = Money.of(amount, from);
        Money convertedAmount = Money.of(convertedAmountValue, to);

        // Calcula taxa efetiva
        BigDecimal rate = convertedAmountValue.divide(amount, 6, BigDecimal.ROUND_HALF_UP);

        return new CurrencyConversionResult(from, to, originalAmount, convertedAmount, rate);
    }
}