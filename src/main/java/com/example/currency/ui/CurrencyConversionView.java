package com.example.currency.ui;

import com.example.currency.CurrencyConversionResult;
import com.example.currency.CurrencyConversionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Route("currency")
public class CurrencyConversionView extends VerticalLayout {

    private final CurrencyConversionService conversionService;

    @Autowired
    public CurrencyConversionView(CurrencyConversionService conversionService) {
        this.conversionService = conversionService;

        Span resultLabel = new Span();

        // ComboBoxes com moedas
        ComboBox<String> fromCurrency = new ComboBox<>("From", "EUR", "USD", "GBP");
        fromCurrency.setValue("EUR");
        ComboBox<String> toCurrency = new ComboBox<>("To", "EUR", "USD", "GBP");
        toCurrency.setValue("USD");

        NumberField amountField = new NumberField("Amount");
        amountField.setValue(1.0);

        Button convertButton = new Button("Convert");

        convertButton.addClickListener(e -> {
            try {
                BigDecimal amount = BigDecimal.valueOf(amountField.getValue());
                CurrencyConversionResult result = conversionService.convert(
                        fromCurrency.getValue(),
                        toCurrency.getValue(),
                        amount
                );

                resultLabel.setText(
                        String.format("%s = %s (Rate: %s)",
                                result.getOriginalAmount(),
                                result.getConvertedAmount(),
                                result.getRate())
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                Notification.show("Erro na conversão: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        add(fromCurrency, toCurrency, amountField, convertButton, resultLabel);
        setPadding(true);
        setSpacing(true);
    }
}