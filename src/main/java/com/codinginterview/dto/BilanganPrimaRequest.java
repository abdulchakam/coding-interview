package com.codinginterview.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BilanganPrimaRequest {

    @ApiModelProperty(notes = "Used when hit api /get", example = "10", dataType = "Integer")
    private Integer number;

    @ApiModelProperty(notes = "Used when hit api /sort", dataType = "List")
    private List<Integer> numberList;
}
