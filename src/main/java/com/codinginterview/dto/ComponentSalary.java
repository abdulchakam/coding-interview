package com.codinginterview.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentSalary {

    @ApiModelProperty(example = "Gaji pokok", dataType = "string")
    private String name;

    @ApiModelProperty(value = "earning or deduction", example = "earning", dataType = "string")
    private String type;

    @ApiModelProperty(example = "30000000", dataType = "numeric")
    private BigDecimal amount;
}
