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
public class Employee {

    @ApiModelProperty(example = "Johnson", dataType = "string")
    private String name;

    @ApiModelProperty(value = "male or female", example = "male", dataType = "string")
    private String sex;

    @ApiModelProperty(value = "single or married", example = "married", dataType = "string")
    private String maritalStatus;

    @ApiModelProperty(example = "0", dataType = "numeric")
    private int child;

    @ApiModelProperty(value = "indonesia or vietnam", example = "indonesia", dataType = "string")
    private String country;

    @ApiModelProperty(notes = "Filled when employee country vietnam", example = "0", dataType = "numeric")
    private BigDecimal insurance;
}
