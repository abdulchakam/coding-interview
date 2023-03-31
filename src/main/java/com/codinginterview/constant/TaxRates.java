package com.codinginterview.constant;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.stream.Stream;

@AllArgsConstructor
public enum TaxRates {

    RANGE1_IDN(TaxConstant.INDONESIA, "Under 50 Million", "5%", BigDecimal.valueOf(0.05)),
    RANGE2_IDN(TaxConstant.INDONESIA, "Range 50-250 Million", "10%", BigDecimal.valueOf(0.1)),
    RANGE3_IDN(TaxConstant.INDONESIA, "More Than 250 Million", "15%", BigDecimal.valueOf(0.15)),

    RANGE1_VDN(TaxConstant.INDONESIA, "Under 50 Million", "2,5%", BigDecimal.valueOf(0.025)),
    RANGE2_VDN(TaxConstant.INDONESIA, "More Than 50 Million", "7,5%", BigDecimal.valueOf(0.075));

    private String country;
    private String description;
    private String percentage;
    private BigDecimal value;

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getPercentage() {
        return percentage;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static Stream<TaxRates> stream() {
        return Stream.of(TaxRates.values());
    }
}
