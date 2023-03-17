package com.codinginterview.service;

import com.codinginterview.dto.BilanganPrimaRequest;
import com.codinginterview.dto.BilanganPrimaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BilanganPrimaService {

    ResponseEntity<Object> responseEntityRequestNotFound = ResponseEntity.badRequest().body("Request Not Found!");

    public ResponseEntity<Object> getBilanganPrima(BilanganPrimaRequest request){
        log.info("Start Get Bilangan Prima");
        try {
            List<Integer> bilangan = new ArrayList<>();

            if (!ObjectUtils.isEmpty(request.getNumber())) {
                for (int i=1; i<=request.getNumber(); i++) {
                    if (isBilanganPrima(i)) bilangan.add(i);
                }

                return ResponseEntity.ok()
                        .body(BilanganPrimaResponse.builder()
                                .content(bilangan)
                                .build());
            } else {
                log.error("Request Not Found");
                return responseEntityRequestNotFound;
            }
        } catch (Exception e) {
            log.error("Error when get bilangan prima : {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> sortBilanganPrima(BilanganPrimaRequest request) {
        log.info("Start Sort Bilangan Prima Ascending");
        try {
            List<Integer> bilangan = new ArrayList<>();
            if (!ObjectUtils.isEmpty(request.getNumberList())) {
                for (Integer data : request.getNumberList()) {
                    // Validation Bilangan
                    if (isBilanganPrima(data)) {
                        bilangan.add(data);
                    } else {
                        log.error(data+" Is Not Bilangan Prima!");
                        return ResponseEntity
                                .badRequest()
                                .body(data+" Is Not Bilangan Prima!");
                    }
                }
            } else {
                log.error("Request Not Found");
                return responseEntityRequestNotFound;
            }

            return ResponseEntity.ok()
                    .body(BilanganPrimaResponse.builder()
                            .content(bilangan.stream().sorted().toList())
                            .build());
        } catch (Exception e) {
            log.error("Error When Sort Bilangan Prima Ascending : {}", e.getMessage());
            throw e;
        }
    }

    private static boolean isBilanganPrima(Integer number) {
        if (number <= 1) {
            return false;
        } else {
            int bilangan = 0;
            for (int i = 1; i <= number; i++) {
                if (number%i == 0) {
                    bilangan = bilangan+1;
                }
            }
            return bilangan == 2;
        }
    }
}
