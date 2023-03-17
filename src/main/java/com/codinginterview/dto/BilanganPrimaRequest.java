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

    @ApiModelProperty(notes = "Used when hit api /get",
            example = "10", value = "Integer")
    private Integer number;

    @ApiModelProperty(notes = "Used when hit api /store",
            example = "[3,7,59,23,2,17]", value = "Array")
    private List<Integer> numberList;
}
