package com.example.apelsin.service;
import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.InvoiceDto;
import com.example.apelsin.entity.Invoice;
import com.example.apelsin.entity.Order;
import com.example.apelsin.repository.InvoiceRepository;
import com.example.apelsin.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    final InvoiceRepository invoiceRepository;
    final OrderRepository orderRepository;
    public ApiResponse add(InvoiceDto dto) {
        Order order = new Order();
        order.setId(dto.getOrdId());
        Order save = orderRepository.save(order);
        Invoice invoice = new Invoice();
        invoice.setOrdId(save);
        invoice.setAmmount(dto.getAmmount());
        invoice.setDue(dto.getDue());
        invoice.setIssued(dto.getIssued());
        invoiceRepository.save(invoice);
        return new ApiResponse("Added", true);
    }

    public ApiResponse edit(Integer id, InvoiceDto dto) {

        Optional<Invoice>byId = invoiceRepository.findById(id);
        if (byId.isEmpty()) return new ApiResponse(" Not found!", false);
        Invoice invoice = byId.get();
        invoice.setIssued(dto.getIssued());
        invoice.setAmmount(dto.getAmmount());
        invoice.setDue(dto.getDue());
        Order order = invoice.getOrdId();
        invoice.setOrdId(order);
        invoiceRepository.save(invoice);
        return new ApiResponse("Edited", true);
    }
}
