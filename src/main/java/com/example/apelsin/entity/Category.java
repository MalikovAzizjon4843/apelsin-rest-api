package com.example.apelsin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "category")
public class Category{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;

   private String name;
   private boolean active = true;
}
