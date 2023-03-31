package com.codinginterview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateTaxResponse {

    private String employeeName;
    private String nettSalary;
    private String nettIncome;
    private String taxPerYear;
    private String taxPerMonth;
}
