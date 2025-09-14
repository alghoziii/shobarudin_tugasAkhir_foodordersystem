package com.sinaukoding.foodordersystem.controller.master;

import com.sinaukoding.foodordersystem.model.enums.OrderStatus;
import com.sinaukoding.foodordersystem.model.enums.Status;
import com.sinaukoding.foodordersystem.model.filter.OrderFilterRecord;
import com.sinaukoding.foodordersystem.model.request.OrderRequestRecord;
import com.sinaukoding.foodordersystem.model.request.OrderUpdateRequestRecord;
import com.sinaukoding.foodordersystem.model.response.BaseResponse;
import com.sinaukoding.foodordersystem.service.transaction.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Tag(name = "Order API")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('PEMBELI')")
    public BaseResponse<?> create(@RequestBody OrderRequestRecord request) {
        var result = orderService.add(request);
        return BaseResponse.ok("Data berhasil disimpan", result);
    }

    @PostMapping("edit")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PENJUAL')")
    public BaseResponse<?> update(@RequestBody OrderUpdateRequestRecord request) {
        var result =  orderService.edit(request);
        return BaseResponse.ok("Order berhasil diubah", result);
    }

    @GetMapping("find-all")
    public BaseResponse<?> findAll(
            @org.springdoc.core.annotations.ParameterObject
            @PageableDefault(sort = "modifiedDate", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String createdDateFrom,
            @RequestParam(required = false) String createdDateTo
    ) {
        var filter = new OrderFilterRecord(userId, status, createdDateFrom, createdDateTo);
        return BaseResponse.ok("Data berhasil diambil", orderService.findAll(filter, pageable));
    }

    @GetMapping("find-by-id/{id}")
    public BaseResponse<?> findById(@PathVariable String id) {
        return BaseResponse.ok("Data berhasil diambil", orderService.findById(id));
    }
}


