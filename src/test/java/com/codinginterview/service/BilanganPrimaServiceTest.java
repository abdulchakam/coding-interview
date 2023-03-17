package com.codinginterview.service;

import com.codinginterview.dto.BilanganPrimaRequest;
import com.codinginterview.dto.BilanganPrimaResponse;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BilanganPrimaService.class)
public class BilanganPrimaServiceTest {

    @Autowired
    private BilanganPrimaService bilanganPrimaService;

    private BilanganPrimaResponse bilanganPrimaResponse() {
        return BilanganPrimaResponse.builder()
                .content(Arrays.asList(2,3,5,7))
                .build();
    }

    @Test
    public void getBilanganPrima_expectSuccess() {
        var response = bilanganPrimaService.getBilanganPrima(
                BilanganPrimaRequest.builder()
                        .number(10)
                        .build());
        TestCase.assertEquals(bilanganPrimaResponse(),response.getBody());
    }

    @Test
    public void getBilanganPrima_expectBadRequest() {
        var response = bilanganPrimaService.getBilanganPrima(
                BilanganPrimaRequest.builder()
                        .number(null)
                        .build());
        TestCase.assertEquals(BAD_REQUEST,response.getStatusCode());
    }

    @Test
    public void sortBilanganPrima_expectSuccess() {
        var response = bilanganPrimaService.sortBilanganPrima(
                BilanganPrimaRequest.builder()
                        .numberList(Arrays.asList(7,2,5,3))
                        .build());
        TestCase.assertEquals(bilanganPrimaResponse(), response.getBody());
    }

    @Test
    public void sortBilanganPrima_expectBadRequest() {
        var response = bilanganPrimaService.sortBilanganPrima(
                BilanganPrimaRequest.builder()
                        .numberList(null)
                        .build());
        TestCase.assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void sortBilanganPrima_expectBadRequestInvalidValue() {
        var response = bilanganPrimaService.sortBilanganPrima(
                BilanganPrimaRequest.builder()
                        .numberList(Arrays.asList(7,2,6,5,3))
                        .build());
        TestCase.assertEquals(BAD_REQUEST, response.getStatusCode());
    }
}
