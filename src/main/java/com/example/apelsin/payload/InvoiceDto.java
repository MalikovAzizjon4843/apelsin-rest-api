package com.example.apelsin.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceDto {
    private Integer ordId;
    private double ammount;
    private Date issued;
    private Date due;
}
