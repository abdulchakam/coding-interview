package com.codinginterview.controller;

import com.codinginterview.dto.BilanganPrimaRequest;
import com.codinginterview.dto.CalculateTaxRequest;
import com.codinginterview.dto.CalculateTaxResponse;
import com.codinginterview.service.CalculateTaxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api
public class CalculateTaxController {

    @Autowired
    private CalculateTaxService calculateTaxService;

    @ApiOperation(value = "Calculate Tax")
    @PostMapping("/hitunggaji")
    public CalculateTaxResponse calculateTaxResponse(@RequestBody CalculateTaxRequest request) {
        return calculateTaxService.calculateTax(request);
    }
}
