package com.example.apelsin.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orderer")
public class Order{
    private Timestamp date;

    @ManyToOne
    private  Customer custId;

    private boolean active = true;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

}
