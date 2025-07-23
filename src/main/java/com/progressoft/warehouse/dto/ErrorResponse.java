package com.progressoft.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

        private Instant timestamp;
        private String message;
        private String path;
        private int status;
        private Object details;

}
