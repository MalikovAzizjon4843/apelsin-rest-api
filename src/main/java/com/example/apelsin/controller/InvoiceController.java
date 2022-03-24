package com.example.apelsin.controller;
import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.InvoiceDto;
import com.example.apelsin.entity.Invoice;
import com.example.apelsin.repository.InvoiceRepository;
import com.example.apelsin.repository.OrderRepository;
import com.example.apelsin.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    final InvoiceRepository invoiceRepository;
    final OrderRepository orderRepository;
    final InvoiceService invoiceService;
    @GetMapping
    public HttpEntity<?> getAll() {
        List<Invoice> all = invoiceRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Invoice> byId = invoiceRepository.findById(id);
        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Invoice()));
    }
    @PostMapping
    public HttpEntity<?> add(@RequestBody InvoiceDto dto) {
        ApiResponse response = invoiceService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody InvoiceDto invoiceDto) {
        ApiResponse response = invoiceService.edit(id, invoiceDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Invoice> byId = invoiceRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Invoice invoice = byId.get();
        invoice.setActive(false);
        invoiceRepository.save(invoice);
        return ResponseEntity.ok().body("DELETED!");
    }

    @GetMapping("/expired_invoices")
    public HttpEntity<?> getExpired_invoices() {
        List<Invoice> all = invoiceRepository.getAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }
    @GetMapping("/wrong_date_invoices")
    public HttpEntity<?> getWrong_date_invoices() {
        List<Invoice> all = invoiceRepository.getWrong_date_invoices();
        return ResponseEntity.ok().body(all);
    }
    @GetMapping("/overpaid_invoices")
    public HttpEntity<?> getOverpaid_invoices() {
        List<Invoice> all = invoiceRepository.getOverpaid_invoices();
        return ResponseEntity.ok().body(all);
    }
}
