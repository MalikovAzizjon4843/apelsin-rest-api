package com.example.apelsin.service;
import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.DetailDto;
import com.example.apelsin.entity.Detail;
import com.example.apelsin.entity.Order;
import com.example.apelsin.entity.Product;
import com.example.apelsin.repository.DetailRepository;
import com.example.apelsin.repository.OrderRepository;
import com.example.apelsin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetailService {

    final OrderRepository orderRepository;
    final DetailRepository detailRepository;
    final ProductRepository productRepository;
    public ApiResponse add(DetailDto dto) {
        Order order= new Order();
        order.setId(dto.getOrdId());
        Order save = orderRepository.save(order);

        Detail detail = new Detail();

        Optional<Product> optionalProduct = productRepository.findById(dto.getPrId());
        if (optionalProduct.isEmpty()) return new ApiResponse("Not found!", false);

        detail.setProductId(optionalProduct.get());
        detail.setQuantity(dto.getQuantity());
        detail.setOrdId(save);
        detailRepository.save(detail);
        return new ApiResponse("Added", true);
    }

    public ApiResponse edit(Integer id, DetailDto dto) {

        Optional<Detail> byId = detailRepository.findById(id);
        if (byId.isEmpty()) return new ApiResponse("AutoShop Not found!", false);

        Detail detail = byId.get();

        Order order = detail.getOrdId();
        detail.setOrdId(order);
        detail.setQuantity(dto.getQuantity());

        Optional<Product> optionalProduct = productRepository.findById(dto.getPrId());
        detail.setProductId(optionalProduct.get());

        detailRepository.save(detail);
        return new ApiResponse("Edited", true);
    }
}
