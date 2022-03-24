package com.example.apelsin.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {
    private Timestamp time;
    private double ammount;
    private Integer invId;
}
