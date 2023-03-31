package com.codinginterview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateTaxRequest {

    private Employee employee;
    private List<ComponentSalary> komponenGaji;
}
