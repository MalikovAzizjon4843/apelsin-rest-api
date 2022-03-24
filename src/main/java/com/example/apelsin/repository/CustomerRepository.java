package com.example.apelsin.repository;

import com.example.apelsin.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByActiveTrue();



    @Query(value = "select * from customer join orderer o on customer.id = o.cust_id_id where   date ='2016-03-23 16:29:50.000000'; ", nativeQuery = true)
    List<Customer> getCustomers_without_orders();



    @Query(value = "select * from customer\n" +
            "    join orderer o on customer.id = o.cust_id_id\n" +
            "WHERE o.date = (\n" +
            "    SELECT MAX(o2.date)\n" +
            "    FROM  orderer o2\n" +
            "    WHERE o2.cust_id_id = o.cust_id_id\n" +
            ");", nativeQuery = true)
    List<Customer> getCustomers_last_orders();





    @Query(value = "select * from customer join orderer o on customer.id = o.cust_id_id where date='2016-06-23 12:32:47.000000'", nativeQuery = true)

    List<Customer> getNumber_of_products_in_year();
}
