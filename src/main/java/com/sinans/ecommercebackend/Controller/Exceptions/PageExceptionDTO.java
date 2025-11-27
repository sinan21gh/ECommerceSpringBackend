package com.sinans.ecommercebackend.Controller.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageExceptionDTO {
    private String message;
    private int status;
    private Instant timestamp;
    private String path;

}
