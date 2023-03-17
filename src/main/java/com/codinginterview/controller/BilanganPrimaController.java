package com.codinginterview.controller;

import com.codinginterview.dto.BilanganPrimaRequest;
import com.codinginterview.service.BilanganPrimaService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Api
public class BilanganPrimaController {

    @Autowired
    private BilanganPrimaService bilanganPrimaService;

    @ApiOperation(value = "Get Bilangan Prima",
            notes = "Request number only, example { \"number\" : 10 }")
    @PostMapping("/bilanganprima/get")
    public ResponseEntity<Object> getBillanganPrima(@RequestBody BilanganPrimaRequest request) {
        var response = bilanganPrimaService.getBilanganPrima(request);
        return new ResponseEntity<>(response,response.getStatusCode());
    }

    @ApiOperation(value = "Sort Bilangan Prima",
            notes = "Request numberList only, Request Only, example { \"numberList\" :[3,7,59,23,2,17] }")
    @PostMapping("/bilanganprima/sort")
    public ResponseEntity<Object> sortBilanganPrima(@RequestBody BilanganPrimaRequest request) {
        var response = bilanganPrimaService.sortBilanganPrima(request);
        return new ResponseEntity<>(response,response.getStatusCode());
    }
}
