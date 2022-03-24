package com.example.apelsin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "invoice")
public class Invoice{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private boolean active = true;
    private double ammount;
    private Date  issued;
    private Date due;


    @ManyToOne
    @JoinColumn(name = "ordId")
   private Order ordId;

}
