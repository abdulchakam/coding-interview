package com.codinginterview.constant;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.stream.Stream;

@AllArgsConstructor
public enum PTKPType {

    SINGLE_IDN(TaxConstant.INDONESIA, "TK", BigDecimal.valueOf(25000000)),
    MARRIED_IDN(TaxConstant.INDONESIA, "K0", BigDecimal.valueOf(50000000)),
    MARRIED_HAVE_CHILD_IDN(TaxConstant.INDONESIA, "K1", BigDecimal.valueOf(75000000)),

    SINGLE_VDN(TaxConstant.VIETNAM, "TK", BigDecimal.valueOf(15000000)),
    MARRIED_VDN(TaxConstant.VIETNAM, "K0", BigDecimal.valueOf(30000000));

    private String country;
    private String code;
    private BigDecimal value;

    public String getCountry() {
        return country;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static Stream<PTKPType> stream() {
        return Stream.of(PTKPType.values());
    }
}
