package com.codinginterview.service;

import com.codinginterview.constant.PTKPType;
import com.codinginterview.constant.TaxConstant;
import com.codinginterview.constant.TaxRates;
import com.codinginterview.dto.CalculateTaxRequest;
import com.codinginterview.dto.CalculateTaxResponse;
import com.codinginterview.dto.ComponentSalary;
import com.codinginterview.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CalculateTaxService {

    public CalculateTaxResponse calculateTax(CalculateTaxRequest request) {
        log.info("Start calculate tax");
        try {
            // Declaration Variable
            Employee employee = request.getEmployee();
            BigDecimal nettSalary = countNettSalaryPerYear(request);
            BigDecimal netIncome = countNetIncome(employee, nettSalary);
            BigDecimal firstTaxIdn, firstTaxVnd, secondTax;
            BigDecimal finalTax = BigDecimal.ZERO;

            if (TaxConstant.INDONESIA.equalsIgnoreCase(employee.getCountry()) && netIncome.compareTo(BigDecimal.ZERO) > 0)  {
                // When net income more than 250 million
                if (netIncome.compareTo(TaxConstant.TWO_HUNDRED_AND_FIFTY_MILLION) > 0) {
                    firstTaxIdn = TaxConstant.FIFTY_MILLION.multiply(TaxRates.RANGE1_IDN.getValue());
                    BigDecimal tempSecondTax = netIncome.subtract(TaxConstant.FIFTY_MILLION);
                    secondTax = tempSecondTax.multiply(TaxRates.RANGE3_IDN.getValue());

                    finalTax = firstTaxIdn.add(secondTax);

                } else if (netIncome.compareTo(TaxConstant.FIFTY_MILLION) > 0 &&
                            netIncome.compareTo(TaxConstant.TWO_HUNDRED_AND_FIFTY_MILLION) < 1) { // When net income between 50-250 million
                    firstTaxIdn = TaxConstant.FIFTY_MILLION.multiply(TaxRates.RANGE1_IDN.getValue());
                    BigDecimal tempSecondTax = netIncome.subtract(TaxConstant.FIFTY_MILLION);
                    secondTax = tempSecondTax.multiply(TaxRates.RANGE2_IDN.getValue());
                    finalTax = firstTaxIdn.add(secondTax);

                } else if (netIncome.compareTo(TaxConstant.FIFTY_MILLION) <= 0 ) { // When net income less than 50 million
                    firstTaxIdn = netIncome.multiply(TaxRates.RANGE1_IDN.getValue());
                    finalTax = firstTaxIdn;
                }
            } else if (TaxConstant.VIETNAM.equalsIgnoreCase(employee.getCountry()) && netIncome.compareTo(BigDecimal.ZERO) > 0 ) {
                if (netIncome.compareTo(TaxConstant.FIFTY_MILLION) > 0) {
                    firstTaxVnd = TaxConstant.FIFTY_MILLION.multiply(TaxRates.RANGE1_VDN.getValue());
                    BigDecimal tempSecondTax = netIncome.subtract(TaxConstant.FIFTY_MILLION);
                    secondTax = tempSecondTax.multiply(TaxRates.RANGE2_VDN.getValue());

                    finalTax = firstTaxVnd.add(secondTax);

                } else if (netIncome.compareTo(TaxConstant.FIFTY_MILLION) <= 0) {
                    firstTaxVnd = netIncome.multiply(TaxRates.RANGE1_VDN.getValue());
                    finalTax = firstTaxVnd;
                }
            }

            BigDecimal taxPerMonth = finalTax.divide(BigDecimal.valueOf(TaxConstant.MONTH_IN_ONE_YEAR), 0,RoundingMode.HALF_UP);

            return CalculateTaxResponse.builder()
                    .employeeName(employee.getName())
                    .nettSalary(convertRupiah(customRound(nettSalary)))
                    .nettIncome(convertRupiah(customRound(netIncome)))
                    .taxPerYear(convertRupiah(customRound(finalTax)))
                    .taxPerMonth(convertRupiah(customRound(taxPerMonth)))
                    .build();
        }catch (Exception e) {
            log.error("Error when calculate tax : {}", e.getMessage());
            throw e;
        }
    }

    private BigDecimal countNetIncome(Employee employee, BigDecimal nettSalary) {
        log.info("Start get ptkp");

        try {
            BigDecimal ptkp;
            BigDecimal netIncome;
            BigDecimal insurance = employee.getInsurance();

            if (TaxConstant.INDONESIA.equalsIgnoreCase(employee.getCountry())) {
                if (TaxConstant.SINGLE.equalsIgnoreCase(employee.getMaritalStatus())) {
                    ptkp = PTKPType.SINGLE_IDN.getValue();

                } else if (TaxConstant.MARRIED.equalsIgnoreCase(employee.getMaritalStatus()) && employee.getChild() == 0) {
                    ptkp = PTKPType.MARRIED_IDN.getValue();

                } else if (TaxConstant.MARRIED.equalsIgnoreCase(employee.getMaritalStatus()) && employee.getChild() > 0) {
                    ptkp = PTKPType.MARRIED_HAVE_CHILD_IDN.getValue();

                } else {
                    throw new RuntimeException("Possible value marital status Single or Married");
                }

                netIncome = nettSalary.subtract(ptkp);

            } else if (TaxConstant.VIETNAM.equalsIgnoreCase(employee.getCountry())) {
                if (TaxConstant.SINGLE.equalsIgnoreCase(employee.getMaritalStatus())) {
                    ptkp = PTKPType.SINGLE_VDN.getValue();

                } else if (TaxConstant.MARRIED.equalsIgnoreCase(employee.getMaritalStatus())) {
                    ptkp = PTKPType.MARRIED_VDN.getValue();

                } else {
                    throw new RuntimeException("Possible value marital status Single or Married");
                }

                netIncome = nettSalary
                        .subtract(insurance.multiply(BigDecimal.valueOf(TaxConstant.MONTH_IN_ONE_YEAR)))
                        .subtract(ptkp);
            } else {
                throw new RuntimeException("Possible value country Indonesia or Vietnam");
            }

            return netIncome;
        } catch (Exception e) {
            log.error("Error when get ptkp");
            throw e;
        }
    }

    private BigDecimal countNettSalaryPerYear(CalculateTaxRequest request) {
        log.info("Start count nett salary per year");
        try {
            List<ComponentSalary> componentSalary = request.getKomponenGaji();
            BigDecimal monthPerYear = BigDecimal.valueOf(12);

            List<BigDecimal> earnings = new ArrayList<>();
            List<BigDecimal> deductions = new ArrayList<>();

            BigDecimal earning = BigDecimal.ZERO;
            BigDecimal deduction = BigDecimal.ZERO;
            BigDecimal nettSalary;

            if (!componentSalary.isEmpty()) {
                componentSalary.forEach(component -> {
                    if ("earning".equalsIgnoreCase(component.getType())) {
                        earnings.add(component.getAmount());
                    } else {
                        deductions.add(component.getAmount());
                    }
                });

                // Sum all type earning
                for (BigDecimal amount : earnings) {
                    earning = earning.add(amount);
                }

                // Sum all type deduction
                for (BigDecimal amount : deductions) {
                    deduction = deduction.add(amount);
                }

                // Earning subtraction deduction
                nettSalary = earning.subtract(deduction);
                return nettSalary.multiply(monthPerYear);

            } else {
                return BigDecimal.ZERO;
            }

        }catch (Exception e) {
            log.error("Error when count nett salary per year : {}", e.getMessage());
            throw e;
        }
    }

    private BigDecimal customRound(BigDecimal value) {
        BigDecimal bigDecimal = new BigDecimal(1000);
        return value.divide(bigDecimal,RoundingMode.HALF_UP).multiply(bigDecimal);
    }
    private String convertRupiah(BigDecimal intPrice) {
        Locale localId = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
        return formatter.format(intPrice);
    }
}
