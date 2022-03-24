package com.example.apelsin.service;

import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.ProductDto;
import com.example.apelsin.entity.Category;
import com.example.apelsin.entity.Product;
import com.example.apelsin.repository.CategoryRepository;
import com.example.apelsin.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService {


    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;
    public ApiResponse add(ProductDto dto) {
        Category category = new Category();
        category.setId(dto.getCategoryId());
        Category save = categoryRepository.save(category);
        Product product = new Product();
        product.setCategoryId(save);
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setPhoto(dto.getPhoto());
        product.setName(dto.getName());
        productRepository.save(product);
        return new ApiResponse("Added", true);
    }

    public ApiResponse edit(Integer id, ProductDto dto) {

        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) return new ApiResponse(" Not found!", false);
        Product product = byId.get();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
product.setPhoto(dto.getPhoto());
product.setDescription(dto.getDescription());
        Category category = product.getCategoryId();
        product.setCategoryId(category);
        productRepository.save(product);
        return new ApiResponse("Edited", true);
    }

}
