package com.example.apelsin.controller;
import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.DetailDto;
import com.example.apelsin.entity.Detail;
import com.example.apelsin.repository.DetailRepository;
import com.example.apelsin.repository.OrderRepository;
import com.example.apelsin.repository.ProductRepository;
import com.example.apelsin.service.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detail")
@RequiredArgsConstructor
public class DetailController {
    final DetailRepository detailRepository;
    final ProductRepository productRepository;
    final DetailService detailService;
    final OrderRepository orderRepository;
    @GetMapping
    public HttpEntity<?> getAll() {
        List<Detail> all = detailRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Detail> byId = detailRepository.findById(id);
        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Detail()));
    }
    @PostMapping
    public HttpEntity<?> add(@RequestBody DetailDto dto) {
        ApiResponse response = detailService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody DetailDto dto) {
        ApiResponse response = detailService.edit(id, dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Detail> byId = detailRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Detail detail = byId.get();
        detail.setActive(false);
        detailRepository.save(detail);
        return ResponseEntity.ok().body("DELETED!");
    }
    @GetMapping("/byOrdId/{id}")
    public HttpEntity<?> getAllByOrd(@PathVariable Integer id) {
        return ResponseEntity.ok().body(detailRepository.findAllByActiveTrueAndOrdId(id));
    }
    @GetMapping("/byProductId/{id}")
    public HttpEntity<?> getAllByProduct(@PathVariable Integer id) {
        return ResponseEntity.ok().body(detailRepository.findAllByActiveTrueAndProductId(id));
    }

}
